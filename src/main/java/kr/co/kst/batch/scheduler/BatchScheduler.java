package kr.co.kst.batch.scheduler;

import kr.co.kst.batch.common.BatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
//@EnableScheduling
public class BatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job job1;

    public BatchScheduler(JobLauncher jobLauncher, Job job1) {
        this.jobLauncher = jobLauncher;
        this.job1 = job1;
    }

    //@Scheduled(cron = "${batch.job1.schedule}")
    public void runJob1() {
        try {
            JobParameters params = BatchUtil.createJobParameters(LocalDate.now().toString());

            JobExecution execution = jobLauncher.run(job1, params);
            log.info("Job1 scheduled: {}", execution.getStatus());

        } catch (Exception e) {
            log.error("job1 schedule failed", e);
        }
    }

    //@Scheduled(cron = "${batch.job2.schedule}")
    //@SchedulerLock(name = "job2Schedule", lockAtLeastFor = "PT5M")
    public void runJob2() {
        if (BatchUtil.isWeekend(LocalDate.now())) {
            log.info("Skipping Job2 on weekend");
            return;
        }

        try {
            JobParameters params = BatchUtil.createJobParameters(
                    LocalDate.now().toString());

            JobExecution execution = jobLauncher.run(job1, params);

            log.info("Job2 scheduled: {}", execution.getStatus());
        } catch (Exception e) {
            log.error("Job2 schedule failed", e);
        }
    }


}
