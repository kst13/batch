package kr.co.kst.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
public class BatchConfig {
    private static final int CHUNK_SIZE = 1000;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job sampleJob() {
        return new JobBuilder("sampleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep())
                .next(taskletStep())
                .build();
    }

    @Bean
    public Step chunkStep() {
        return new StepBuilder("chunkStep", jobRepository)
                .<String, String>chunk(CHUNK_SIZE, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Step taskletStep() {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("Executing tasklet step");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    public ItemReader<String> itemReader() {
        List<String> items = Arrays.asList("Item1", "Item2", "Item3");
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemProcessor<String, String> itemProcessor() {
        return item -> {
            log.info("Processing item: {}", item);
            return item.toUpperCase();
        };
    }

    @Bean
    public ItemWriter<String> itemWriter() {
        return items -> {
            log.info("Writing items: {}", items);
            items.forEach(item -> log.info("Writing item: {}", item));
        };
    }


}
