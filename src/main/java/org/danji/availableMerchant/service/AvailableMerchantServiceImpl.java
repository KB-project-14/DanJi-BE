package org.danji.availableMerchant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.dto.MerchantFilterDTO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class AvailableMerchantServiceImpl implements AvailableMerchantService {
    private final AvailableMerchantMapper merchantMapper;
    private final LocalCurrencyMapper localCurrencyMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final KakaoApiClient kakaoApiClient;

    @Value("${public.api.service-key}")
    private String serviceKey;

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

//        //DB에서 모든 지역화폐 정보 가져오기
//        List<LocalCurrencyVO> allCurrencies = localCurrencyMapper.findAll();
//        if (allCurrencies.isEmpty()) {
//            log.warn("DB에 등록된 지역화폐가 없습니다.");
//            return;
//        }
//
//        //각 지역화폐에 대한 데이터 수집 작업
//        allCurrencies.forEach(currency -> {
//            try {
//                importDataForLocation(currency);
//                log.info("데이터 수집 완료");
//            } catch (Exception e) {
//                log.error("데이터 수집 중 에러 발생", currency.getName(), e.getMessage(), e);
//            }
//        });
//
//        log.info("가맹점 데이터 수집 완료");
    }

    private void importDataForLocation(LocalCurrencyVO currency) throws Exception {
//        int page = 1;
//        int numOfRows = 100;
//        String baseUrl = "http://api.data.go.kr/openapi/tn_pubr_public_local_bill_api";
//
//        //공공데이터 API URL 구성
//        try {
//
//            DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
//            factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
//
//            WebClient webClient = WebClient.builder()
//                    .uriBuilderFactory(factory)
//                    .build();
//            System.out.println("[Region name] : " + currency.getRegionName());
//            String encodedCity = URLEncoder.encode(currency.getRegionName(), StandardCharsets.UTF_8);
//            String encodedBill = URLEncoder.encode(currency.getName(), StandardCharsets.UTF_8);
//
//            String url = baseUrl +
//                    "?ServiceKey=" + serviceKey +
//                    "&pageNo=" + page +
//                    "&numOfRows=" + numOfRows +
//                    "&type=json" +
//                    "&CTPV_NM=" + encodedCity +
//                    "&LOCAL_BILL=" + encodedBill;
//
//
//            log.info("▶ 호출 URL = {}", url);
//
//            String response = webClient.get()
//                    .uri(url)
//                    .retrieve()
//                    .bodyToMono(String.class)
//                    .block();
//
//            // JSON 파싱
//            JsonNode root = objectMapper.readTree(response);
//
//            // API 응답 상태 확인
//            JsonNode header = root.path("response").path("header");
//            String resultCode = header.path("resultCode").asText();
//            String resultMsg = header.path("resultMsg").asText();
//
//            log.info("▶ API 응답코드={} / 메시지={}", resultCode, resultMsg);
//
//            // 에러 코드 체크
//            if (!"00".equals(resultCode)) {
//                log.error("API 호출 실패: 코드={}, 메시지={}", resultCode, resultMsg);
//                return;
//            }
//
//            JsonNode body = root.path("response").path("body");
//            JsonNode itemsNode = body.path("items");
//
//            log.info("itemsNode size = {}", itemsNode.size());
//
//            if (!itemsNode.isArray() || itemsNode.size() == 0) {
//                log.info("더 이상 가져올 데이터가 없습니다.");
//                return;
//            }
//
//            //각 가맹점 데이터 반복 처리
//            for (JsonNode item : itemsNode) {
//                String name = item.path("affiliateNm").asText(); //가맹점명
//                String address = item.path("lctnRoadNmAddr").asText(); //도로명 주소
//                String category = item.path("sectorNm").asText(); //업종명
//                String localCurrencyName = item.path("localBill").asText(); //지역화폐
//
//                // 빈 값 체크
//                if (name.isEmpty() || address.isEmpty()) {
//                    log.warn("필수 정보가 없는 가맹점 데이터: name={}, address={}", name, address);
//                    continue;
//                }
//
//                //지역화폐 이름으로 local_currency_id 조회
//                List<LocalCurrencyVO> currencies = localCurrencyMapper.findAllByName(localCurrencyName);
//                if (currencies.isEmpty()) {
//                    log.warn("등록되지 않은 지역화폐: {}", localCurrencyName);
//                    continue;
//                }
//
//                //예를 들어 동백전 데이터가 여러 개일 때 하나만 가져옴
//                LocalCurrencyVO currencyOpt = currencies.get(0);
//
//                //중복 가맹점 방지(이름 + 주소)
//                if (merchantMapper.existsByNameAndAddress(name, address)) {
//                    log.info("중복된 가맹점: {} - {}", name, address);
//                    continue;
//                }
//
//                //주소에서 층수 정보 제거
//                String cleanedAddressForApi = address.replaceAll("\\s*(지하)?\\d+층", "").trim();
//
//                //초기화
//                BigDecimal latitude = BigDecimal.ZERO;
//                BigDecimal longitude = BigDecimal.ZERO;
//
//                //KakaoApiClient를 사용해 위경도 좌표 가져오기
//                BigDecimal[] coords = kakaoApiClient.getCoordinates(cleanedAddressForApi);
//                if (coords != null) {
//                    latitude = coords[0];
//                    longitude = coords[1];
//                    log.info("주소 변환 성공: '{}' -> [{}, {}]", address, latitude, longitude);
//
//                    //좌표 변환에 성공한 경우에만 VO 생성 후 DB 저장
//                    AvailableMerchantVO vo = AvailableMerchantVO.builder()
//                            .availableMerchantId(UUID.randomUUID())
//                            .name(name)
//                            .address(address)
//                            .latitude(latitude)
//                            .longitude(longitude)
//                            .category(category)
//                            .localCurrencyId(currencyOpt.getLocalCurrencyId())
//                            .build();
//
//                    merchantMapper.create(vo);
//                    log.info("새 가맹점 저장: {}", name);
//
//                } else {
//                    log.warn("주소 변환 실패: {}", address, cleanedAddressForApi);
//                }
//            }
//        } catch (Exception e) {
//            log.error("공공 API 수집 중 예외 발생 ▶ 원인: {}", e.getMessage(), e);
//            throw new RuntimeException("API 데이터 수집 실패", e);
//        }
    }

    @Override
    public List<AvailableMerchantDTO> findByFilter(MerchantFilterDTO filterDTO) {
        return merchantMapper.findByFilter(filterDTO);
    }
}
