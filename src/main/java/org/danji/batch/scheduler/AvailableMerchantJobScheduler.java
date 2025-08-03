package org.danji.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AvailableMerchantJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job merchantChunkJob;

    @Scheduled(cron = "0 * * * * *")  // 매 1분마다 실행
    public void runJob() {
        try {
            jobLauncher.run(
                    merchantChunkJob,
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis())
                            .toJobParameters()
            );
            log.info("✅ 가맹점 동기화 배치 완료");
        } catch (Exception e) {
            log.error("❌ 배치 실행 중 예외 발생", e);
        }
    }
}
