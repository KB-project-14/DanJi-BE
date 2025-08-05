package org.danji.batch.config;

import lombok.RequiredArgsConstructor;
import org.danji.availableMerchant.domain.AvailableMerchantVO;
import org.danji.batch.step.AvailableMerchantProcessor;
import org.danji.batch.step.AvailableMerchantWriter;
import org.danji.batch.step.LocalCurrencyPartitionedReader;
import org.danji.batch.step.LocalCurrencyPartitioner;
import org.danji.localCurrency.domain.LocalCurrencyVO;
import org.danji.localCurrency.dto.LocalCurrencyDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AvailableMerchantChunkJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final LocalCurrencyPartitionedReader reader;
    private final AvailableMerchantProcessor processor;
    private final AvailableMerchantWriter writer;
    private final StepBuilderFactory stepBuilderFactory;
    private final LocalCurrencyPartitioner partitioner;

    @Bean
    public Step slaveStep() {
        return stepBuilderFactory.get("slaveStep")
                .<LocalCurrencyDTO, List<AvailableMerchantVO>>chunk(2)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step masterStep(TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("masterStep")
                .partitioner("slaveStep", partitioner)
                .step(slaveStep())
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    public Job merchantChunkJob(@Qualifier("masterStep") Step merchantChunkStep) {
        return new JobBuilder("merchantChunkJob")           // 이름만 넣기
                .repository(jobRepository)
                .start(merchantChunkStep)
                .build();
    }
}
