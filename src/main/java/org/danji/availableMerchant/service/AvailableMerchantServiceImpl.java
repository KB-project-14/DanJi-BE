package org.danji.availableMerchant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.availableMerchant.dto.AvailableMerchantDTO;
import org.danji.availableMerchant.mapper.AvailableMerchantMapper;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.mapper.LocalCurrencyMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class AvailableMerchantServiceImpl implements AvailableMerchantService {
    private final AvailableMerchantMapper merchantMappermapper;
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
        merchantMappermapper.create(vo);
        //다시 조회해 DTO 반환
        return AvailableMerchantDTO.of(vo);
    }

    @Override
    public void importFromPublicAPI() {
        String serviceKey = "ed7g7lbmLTlBXvYw9LSGPrD6KW4ppvXNZrCWDHNKEzQDkHTRKE9XWeY6Q5uoNxhhwwtvoLP%2BHvL1VYFxicTm1g%3D%3D";
        int page = 1;
        int numOfRows = 1000;
        int totalPages = Integer.MAX_VALUE;

        while (page <= totalPages) {
            //공공데이터 API URL 구성
            try {
                String url = UriComponentsBuilder
                        .fromHttpUrl("https://api.data.go.kr/openapi/tn_pubr_public_local_bill_api")
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("page", page) //페이지 번호
                        .queryParam("numOfRows", numOfRows) //한 페이지 당 결과 수
                        .queryParam("type", "json") //응답 형식
                        .queryParam("CTPV_NM", "부산광역시") //시도명
                        .queryParam("LOCAL_BILL", "동백전") //지역화폐 이름
                        .build(true)
                        .toUriString();

                //API 호출
                RestTemplate restTemplate = new RestTemplate();
                String response = restTemplate.getForObject(url, String.class);

                //JSON 파싱
                JsonNode root = objectMapper.readTree(response);
                JsonNode body = root.path("response").path("body");

                //첫 페이지에서만 총 데이터 개수 확인 후 전체 페이지 계산
                if (page == 1) {
                    int totalCount = body.path("totalCount").asInt();
                    totalPages = (int) Math.ceil((double) totalCount / numOfRows);
                    log.info("총 가맹점 수: {}, 총 페이지 수: {}",totalCount, totalPages);
                }

                //실제 가맹점 데이터 배열 추출
                JsonNode items = body.path("items");
                if (!items.isArray() || items.size() == 0) break;

                //각 가맹점 데이터 반복 처리
                for (JsonNode item : items) {
                    String name = item.path("affiliateNm").asText(); //가맹점명
                    String address = item.path("IctnRoadNmAddr").asText(); //도로명 주소
                    String category = item.path("sectorNm").asText(); //업종명
                    String localCurrencyName = item.path("localBill").asText(); //지역화폐

                    //지역화폐 이름으로 local_currency_id 조회
                    Optional<LocalCurrencyVO> currencyOpt = localCurrencyMapper.findByName(localCurrencyName);
                    if (currencyOpt.isEmpty()) {
                        log.warn("등록되지 않은 지역화폐: {}", localCurrencyName);
                        continue;
                    }

                    //중복 가맹점 방지(이름 + 주소)
                    if (merchantMappermapper.existsByNameAndAddress(name, address)) {
                        log.info("중복된 가맹점: {} - {}", name, address);
                        continue;
                    }

                    //VO 생성 후 DB 저장
                    AvailableMerchantVO vo = AvailableMerchantVO.builder()
                            .name(name)
                            .address(address)
                            .latitude(BigDecimal.ZERO)
                            .longitude(BigDecimal.ZERO)
                            .category(category)
                            .localCurrencyId(currencyOpt.get().getLocalCurrencyId())
                            .build();

                    merchantMappermapper.create(vo);
                }

                //다음 페이지로 이동
                page++;
            } catch (Exception e) {
                log.error("공공 API 수집 중 예외 발생", e);
            }
        }
    }
}
