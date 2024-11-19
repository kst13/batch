package kr.co.kst.batch.job.job1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Job1Listener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Job1 started: {}", jobExecution.getJobInstance().getJobName());
        jobExecution.getExecutionContext().put("startTime", System.currentTimeMillis());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        Long startTime = (Long) jobExecution.getExecutionContext().get("startTime");
        Long endTime = System.currentTimeMillis();
        log.info("job1 Finished: {} ({}ms)", jobExecution.getStatus(), endTime - startTime);
    }
}
