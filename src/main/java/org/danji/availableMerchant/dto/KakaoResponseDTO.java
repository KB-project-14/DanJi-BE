package org.danji.availableMerchant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.swing.text.Document;
import java.util.List;

@Data
//카카오 응답에 우리가 정의하지 않은 필드가 있어도 무시
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoResponseDTO {
    private List<Document> documents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        //Json 필드 y를 latitude에 매핑
        @JsonProperty("y")
        private String latitude;

        //Json 필드 x를 longitude에 매핑
        @JsonProperty("x")
        private String longitude;
    }
}
