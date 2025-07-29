package org.danji.availableMerchant.service;

import org.danji.availableMerchant.dto.KakaoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component //해당 클래스 빈으로 등록
@Log4j2
public class KakaoApiClient {
    private final WebClient webClient;
    private final String kakaoApiKey;

    //생성자를 통해 WebClient와 API 키 주입
    public KakaoApiClient(@Value("${kakao.api.key}") String kakaoApiKey) {
        this.kakaoApiKey = "KakaoAK " + kakaoApiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .build();
    }

    //주소를 받아 위경도 좌표 반환
    public BigDecimal[] getCoordinates(String address) {
        try {
            //API를 호출하고 응답을 DTO로 받음
            KakaoResponseDTO response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                    //세부 경로와 포맷 지정
                                    .path("/v2/local/search/address.json")
                            //쿼리 파라미터 추가
                                    .queryParam("query", address)
                                    .build())
                    //인증 헤더 추가
                    .header("Authorization", this.kakaoApiKey)
                    .retrieve() //응답 받기
                    .bodyToMono(KakaoResponseDTO.class) //응답 본문을 DTO로 자동 변환
                    .block(); //비동기 스트림의 결과를 동기적으로 기다림

            //응답이 유효하고 결과 문서가 존재하는지 확인
            if (response != null && !response.getDocuments().isEmpty()) {
                KakaoResponseDTO.Document firstDoc = response.getDocuments().get(0);
                BigDecimal latitude = new BigDecimal(firstDoc.getLatitude());
                BigDecimal longitude = new BigDecimal(firstDoc.getLongitude());
                return new BigDecimal[]{latitude, longitude};
            }
        } catch (Exception e) {
            log.error("카카오 주소 변환 API 호출 중 오류 발생: address={}, error={}", address, e.getMessage());
        }

        return null;
    }
}
