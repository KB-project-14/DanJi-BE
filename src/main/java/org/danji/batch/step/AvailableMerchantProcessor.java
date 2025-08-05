package org.danji.batch.step;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.availableMerchant.service.KakaoApiClient;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.danji.localCurrency.dto.LocalCurrencyDetailDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<AvailableMerchantVO> results = new ArrayList<>();

        String encodedCity = URLEncoder.encode(currency.getRegionName(), StandardCharsets.UTF_8);
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

        for (JsonNode item : items) {
            String name = item.path("affiliateNm").asText();
            String address = item.path("lctnRoadNmAddr").asText();
            String category = item.path("sectorNm").asText();

            if (name.isEmpty() || address.isEmpty()) continue;
            if (merchantMapper.existsByNameAndAddress(name, address)) continue;

            String cleanedAddress = address.replaceAll("\\s*(지하)?\\d+층", "").trim();
            BigDecimal[] coords = kakaoApiClient.getCoordinates(cleanedAddress);
            if (coords == null) continue;

            AvailableMerchantVO vo = AvailableMerchantVO.builder()
                    .availableMerchantId(UUID.randomUUID())
                    .name(name)
                    .address(address)
                    .latitude(coords[0])
                    .longitude(coords[1])
                    .category(category)
                    .localCurrencyId(currency.getLocalCurrencyId())
                    .build();

            results.add(vo);
        }

        return results;
    }
}
