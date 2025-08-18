package org.danji.availableMerchant.service;

import org.danji.availableMerchant.dto.KakaoResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Component
@Log4j2
public class KakaoApiClient {
    private final WebClient webClient;
    private final String kakaoApiKey;

    public KakaoApiClient(@Value("${kakao.api.key}") String kakaoApiKey) {
        this.kakaoApiKey = "KakaoAK " + kakaoApiKey;
        this.webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .build();
    }

    public BigDecimal[] getCoordinates(String address) {
        try {
            KakaoResponseDTO response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v2/local/search/address.json")
                            .queryParam("query", address)
                            .build())
                    .header("Authorization", this.kakaoApiKey)
                    .retrieve()
                    .bodyToMono(KakaoResponseDTO.class)
                    .block();

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
