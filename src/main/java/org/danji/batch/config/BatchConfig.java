//package org.danji.batch.config;
//
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.launch.support.SimpleJobLauncher;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.core.task.TaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    private final DataSource dataSource;
//
//    @Autowired
//    private final PlatformTransactionManager transactionManager;
//
//    public BatchConfig(DataSource dataSource, PlatformTransactionManager transactionManager) {
//        this.dataSource = dataSource;
//        this.transactionManager = transactionManager;
//    }
//
//
//    @Bean
//    public JobRepository jobRepository() throws Exception {
//        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setDatabaseType("mysql");
//        factory.setTransactionManager(transactionManager);
//        return factory.getObject();
//    }
//
//    @Bean
//    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
//        SimpleJobLauncher launcher = new SimpleJobLauncher();
//        launcher.setJobRepository(jobRepository);
//
//        // 병렬 실행을 위한 TaskExecutor 설정
//        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
//
//        launcher.afterPropertiesSet(); // 필수
//        return launcher;
//    }
//
//    @Bean
//    public StepBuilderFactory stepBuilderFactory(JobRepository jobRepository,
//                                                 PlatformTransactionManager transactionManager) {
//        return new StepBuilderFactory(jobRepository, transactionManager);
//    }
//
//
//    @Bean
//    public JobBuilderFactory jobBuilderFactory(JobRepository jobRepository) {
//        return new JobBuilderFactory(jobRepository);
//    }
//
//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(4);      // 동시에 실행할 최대 slaveStep 개수
//        executor.setMaxPoolSize(8);       // 부하 상황에서 확장 가능
//        executor.setQueueCapacity(100);   // 대기 큐 크기
//        executor.setThreadNamePrefix("batch-exec-");
//        executor.initialize();
//        return executor;
//    }
//
//}
