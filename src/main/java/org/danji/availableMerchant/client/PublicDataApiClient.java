//package org.danji.availableMerchant.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "publicDataApiClient", url = "http://api.data.go.kr")
//public interface PublicDataApiClient {
//    //지역화폐 가맹점 목록 조회
//    @GetMapping("/openapi/tn_pubr_public_local_bill_api")
//    String getMerchants(
//            @RequestParam("ServiceKey") String serviceKey, //공공데이터 API 인증키(인코딩 키)
//            @RequestParam("pageNo") int pageNo, //페이지 번호
//            @RequestParam("numOfRows") int numOfRows, //한 페이지당 결과 수
//            @RequestParam("type") String type, //응답 형식 (json)
//            @RequestParam("CTPV_NM") String sidoName, //시도명
//            @RequestParam("LOCAL_BILL") String localBillName //지역화폐 명
//    );
//}
