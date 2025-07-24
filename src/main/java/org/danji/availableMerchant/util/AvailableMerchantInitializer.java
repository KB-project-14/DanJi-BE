package org.danji.availableMerchant.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.danji.availableMerchant.service.AvailableMerchantService;
import org.springframework.context.ApplicationListener;
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
        if (alreadyExecuted) return;
        alreadyExecuted = true;

        try {
            log.info("톰캣 시작 시 가맹점 데이터 동기화 시작");
            availableMerchantService.importFromPublicAPI();
        } catch (Exception e) {
            log.error("가맹점 동기화 중 에러 발생", e);
        }
    }
}
