package org.danji.availableMerchant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.global.error.ErrorCode;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.exception.LocalCurrencyException;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.Proxy;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AvailableMerchantServiceImpl implements AvailableMerchantService {
    private final AvailableMerchantMapper merchantMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AvailableMerchantDTO create(AvailableMerchantDTO dto) {
        log.info("create available merchant: " + dto);

        //UUID 생성 및 VO 변환
        dto.setAvailableMerchantId(UUID.randomUUID());

        //DTO -> VO 변환
        AvailableMerchantVO vo = dto.toVo();

        //DB 저장
        merchantMapper.create(vo);
        //다시 조회해 DTO 반환
        return AvailableMerchantDTO.of(vo);
    }

    @Override
    public void importFromPublicAPI() {
        String serviceKey = "ed7g7lbmLTlBXvYw9LSGPrD6KW4ppvXNZrCWDHNKEzQDkHTRKE9XWeY6Q5uoNxhhwwtvoLP%2BHvL1VYFxicTm1g%3D%3D";
        int page = 1;
        int numOfRows = 100;

        //공공데이터 API URL 구성
            try {
                String baseUrl = "http://api.data.go.kr/openapi/tn_pubr_public_local_bill_api";

                DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
                factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

                WebClient webClient = WebClient.builder()
                        .uriBuilderFactory(factory)
                        .build();

                String encodedCity = URLEncoder.encode("부산광역시", StandardCharsets.UTF_8);
                String encodedBill = URLEncoder.encode("동백전", StandardCharsets.UTF_8);

                String url = baseUrl +
                        "?ServiceKey=" + serviceKey +
                        "&pageNo=" + page +
                        "&numOfRows=" + numOfRows +
                        "&type=json" +
                        "&CTPV_NM=" + encodedCity +
                        "&LOCAL_BILL=" + encodedBill;


                log.info("▶ 호출 URL = {}", url);

                String response = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                log.info("▶ API 응답 = {}", response);

                // JSON 파싱
                JsonNode root = objectMapper.readTree(response);

                log.info("루트", root.toString());

                // API 응답 상태 확인
                JsonNode header = root.path("response").path("header");
                String resultCode = header.path("resultCode").asText();
                String resultMsg = header.path("resultMsg").asText();

                log.info("▶ API 응답코드={} / 메시지={}", resultCode, resultMsg);

                // 에러 코드 체크
                if (!"00".equals(resultCode)) {
                    log.error("API 호출 실패: 코드={}, 메시지={}", resultCode, resultMsg);
                    return;
                }

                JsonNode body = root.path("response").path("body");
                JsonNode itemsNode = body.path("items");

                log.info("itemsNode size = {}", itemsNode.size());

                if (!itemsNode.isArray() || itemsNode.size() == 0) {
                    log.info("첫 번째 페이지에 데이터가 없습니다.");
                    return;
                }

                //각 가맹점 데이터 반복 처리
                for (JsonNode item : itemsNode) {
                    String name = item.path("affiliateNm").asText(); //가맹점명
                    String address = item.path("lctnRoadNmAddr").asText(); //도로명 주소
                    String category = item.path("sectorNm").asText(); //업종명
                    String localCurrencyName = item.path("localBill").asText(); //지역화폐

                    // 빈 값 체크
                    if (name.isEmpty() || address.isEmpty()) {
                        log.warn("필수 정보가 없는 가맹점 데이터: name={}, address={}", name, address);
                        continue;
                    }

                    //지역화폐 이름으로 local_currency_id 조회
                    List<LocalCurrencyVO> currencies = localCurrencyMapper.findAllByName(localCurrencyName);
                    if (currencies.isEmpty()) {
                        log.warn("등록되지 않은 지역화폐: {}", localCurrencyName);
                        continue;
                    }

                    LocalCurrencyVO currencyOpt = currencies.get(0);

                    //중복 가맹점 방지(이름 + 주소)
                    if (merchantMapper.existsByNameAndAddress(name, address)) {
                        log.info("중복된 가맹점: {} - {}", name, address);
                        continue;
                    }

                    //VO 생성 후 DB 저장
                    AvailableMerchantVO vo = AvailableMerchantVO.builder()
                            .availableMerchantId(UUID.randomUUID())
                            .name(name)
                            .address(address)
                            .latitude(BigDecimal.ZERO)
                            .longitude(BigDecimal.ZERO)
                            .category(category)
                            .localCurrencyId(currencyOpt.getLocalCurrencyId())
                            .build();

                    merchantMapper.create(vo);
                    log.info("새 가맹점 저장: {}", name);
                }
            } catch (Exception e) {
                log.error("공공 API 수집 중 예외 발생 ▶ 원인: {}", e.getMessage(), e);
                throw new RuntimeException("API 데이터 수집 실패", e);
            }
    }

    @Override
    public List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO) {
        List<AvailableMerchantVO> voList = merchantMapper.findByFilter(filterDTO);

        //DTO로 변환해서 반환
        return voList.stream()
                .map(AvailableMerchantDTO::of)
                .collect(Collectors.toList());
    }
}
