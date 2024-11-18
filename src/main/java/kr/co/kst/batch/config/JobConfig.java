package kr.co.kst.batch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig extends DefaultBatchConfiguration {

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Bean
    public JobOperator jobOperator(
            JobRegistry jobRegistry
            , JobRepository jobRepository
            , JobLauncher jobLauncher
            , JobExplorer jobExplorer) {
        SimpleJobOperator operator = new SimpleJobOperator();
        operator.setJobRegistry(jobRegistry);
        operator.setJobRepository(jobRepository);
        operator.setJobLauncher(jobLauncher);
        operator.setJobExplorer(jobExplorer);
        return operator;
    }
}
