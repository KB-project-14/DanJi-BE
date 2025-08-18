package org.danji.availableMerchant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoResponseDTO {
    private List<Document> documents;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {

        @JsonProperty("y")
        private String latitude;

        @JsonProperty("x")
        private String longitude;
    }
}
