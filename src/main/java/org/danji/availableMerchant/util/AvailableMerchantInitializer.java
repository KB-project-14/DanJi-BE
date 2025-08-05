package org.danji.availableMerchant.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.service.AvailableMerchantService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableMerchantInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private final AvailableMerchantService availableMerchantService;
    private static boolean alreadyExecuted = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        // Root Application Context에서만 실행 (중복 실행 방지)
//        if (event.getApplicationContext().getParent() != null) {
//            return;
//        }
//
//        if (alreadyExecuted) {
//            log.info("가맹점 동기화가 이미 실행되었습니다.");
//            return;
//        }
//
//        alreadyExecuted = true;
//
//        try {
//            log.info("=== 애플리케이션 컨텍스트 로드 완료 - 가맹점 데이터 동기화 시작 ===");
//
//            // 약간의 지연을 두어 모든 Bean이 완전히 초기화되도록 함
//            Thread.sleep(1000);
//
//            // 기존 데이터 확인
//            int beforeCount = availableMerchantService.getAll().size();
//            log.info("동기화 전 가맹점 데이터 수: {}", beforeCount);
//
//            // API 데이터 동기화 실행
//            availableMerchantService.importFromPublicAPI();
//
//            // 동기화 후 데이터 확인
//            int afterCount = availableMerchantService.getAll().size();
//            log.info("동기화 후 가맹점 데이터 수: {}", afterCount);
//            log.info("새로 추가된 가맹점 수: {}", afterCount - beforeCount);
//
//            log.info("=== 가맹점 데이터 동기화 완료 ===");
//
//        } catch (InterruptedException ie) {
//            Thread.currentThread().interrupt();
//            log.error("동기화 대기 중 인터럽트 발생", ie);
//        } catch (Exception e) {
//            log.error("=== 가맹점 동기화 중 에러 발생 ===");
//            log.error("에러 클래스: {}", e.getClass().getName());
//            log.error("에러 메시지: {}", e.getMessage());
//
//            // 원인 추적
//            Throwable cause = e.getCause();
//            if (cause != null) {
//                log.error("근본 원인: {} - {}", cause.getClass().getName(), cause.getMessage());
//            }
//
//            log.error("전체 스택트레이스:", e);
//
//            // 개발 환경에서 필요시 주석 해제
//            // throw new RuntimeException("가맹점 동기화 실패", e);
//        }
     }
}