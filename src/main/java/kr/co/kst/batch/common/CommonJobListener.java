package kr.co.kst.batch.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        BatchUtil.validateJobParameters(jobExecution.getJobParameters());
        log.info("Job started: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().equals(BatchStatus.FAILED)) {
        }
    }

}
