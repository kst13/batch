package kr.co.kst.batch.web;


import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("batch")
public class BatchController {
    private final JobLauncher jobLauncher;
    private final Job job1;

    public BatchController(JobLauncher jobLauncher, Job job1) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
    }

    @PostMapping("start")
    public ResponseEntity<String> startBatch() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters parameters = new JobParametersBuilder()
                .addString("requestDate", LocalDate.now().toString())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job1, parameters);
        return ResponseEntity.ok(
                String.format("Job started with status: %s", jobExecution.getStatus())
        );

    }
}
