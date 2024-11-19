package kr.co.kst.batch.job.job1;

import kr.co.kst.batch.common.CommonJobListener;
import kr.co.kst.batch.common.JobParameterValidator;
import kr.co.kst.batch.domain.entity.ProcessedSample;
import kr.co.kst.batch.domain.entity.SampleEntity;
import kr.co.kst.batch.job.job1.step.CustomItemProcessor;
import kr.co.kst.batch.job.job1.step.CustomItemReader;
import kr.co.kst.batch.job.job1.step.CustomItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class Job1Config {
    private final Job1Listener job1Listener;
    private final CommonJobListener commonJobListener;
    private final JobParameterValidator validator;
    private final CustomItemReader customItemReader;
    private final CustomItemProcessor customItemProcessor;
    private final CustomItemWriter customItemWriter;

    @Bean
    public Job job1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job1", jobRepository)
                .incrementer(new RunIdIncrementer())
                .validator(validator)
                .listener(commonJobListener)
                .listener(job1Listener)
                .start(job1Step1(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step job1Step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("job1Step1", jobRepository)
                .<SampleEntity, ProcessedSample>chunk(100, transactionManager)
                .reader(customItemReader)
                .processor(customItemProcessor)
                .writer(customItemWriter)
                .build();
    }

}
