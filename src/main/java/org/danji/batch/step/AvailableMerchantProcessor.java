package org.danji.batch.step;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.availableMerchant.service.KakaoApiClient;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableMerchantProcessor implements ItemProcessor<LocalCurrencyDTO, List<AvailableMerchantVO>> {


    private final KakaoApiClient kakaoApiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AvailableMerchantMapper merchantMapper;

    @Value("${public.api.service-key}")
    private String serviceKey;

    @Override
    public List<AvailableMerchantVO> process(LocalCurrencyDTO currency) throws Exception {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .build();

        String encodedCity = URLEncoder.encode(currency.getProvince(), StandardCharsets.UTF_8);
        String encodedBill = URLEncoder.encode(currency.getName(), StandardCharsets.UTF_8);

        String url = "http://api.data.go.kr/openapi/tn_pubr_public_local_bill_api" +
                "?ServiceKey=" + serviceKey +
                "&pageNo=1" +
                "&numOfRows=100" +
                "&type=json" +
                "&CTPV_NM=" + encodedCity +
                "&LOCAL_BILL=" + encodedBill;

        log.info("📡 API 호출 URL: {}", url);

        String response = webClient.get().uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode items = objectMapper.readTree(response)
                .path("response").path("body").path("items");

        List<AvailableMerchantVO> allMerchants = new ArrayList<>();

        for (JsonNode item : items) {
            String name = item.path("affiliateNm").asText();
            String address = item.path("lctnRoadNmAddr").asText();
            String category = item.path("sectorNm").asText();

            if (name.isEmpty() || address.isEmpty()) continue;

            AvailableMerchantVO vo = AvailableMerchantVO.builder()
                    .availableMerchantId(UUID.randomUUID())
                    .name(name)
                    .address(address)
                    .category(category)
                    .localCurrencyId(currency.getLocalCurrencyId())
                    .build();

            allMerchants.add(vo);
        }

        if (allMerchants.isEmpty()) return Collections.emptyList();

        List<Map<String, String>> merchantKeys = allMerchants.stream()
                .map(vo -> {
                    Map<String, String> key = new HashMap<>();
                    key.put("name", vo.getName());
                    key.put("address", vo.getAddress());
                    return key;
                })
                .toList();

        List<AvailableMerchantVO> existing = merchantMapper.findExistingByNameAndAddressList(merchantKeys);
        Set<String> existingKeySet = existing.stream()
                .map(vo -> vo.getName() + "|" + vo.getAddress())
                .collect(Collectors.toSet());

        List<AvailableMerchantVO> filtered = allMerchants.stream()
                .filter(vo -> !existingKeySet.contains(vo.getName() + "|" + vo.getAddress()))
                .toList();

        if (filtered.isEmpty()) return Collections.emptyList();

        List<AvailableMerchantVO> results = new ArrayList<>();
        for (AvailableMerchantVO vo : filtered) {
            String cleanedAddress = vo.getAddress().replaceAll("\\s*(지하)?\\d+층", "").trim();
            BigDecimal[] coords = kakaoApiClient.getCoordinates(cleanedAddress);
            if (coords == null) continue;

            vo.setLatitude(coords[0]);
            vo.setLongitude(coords[1]);
            results.add(vo);
        }
        return results;
    }
}
