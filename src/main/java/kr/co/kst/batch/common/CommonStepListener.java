package kr.co.kst.batch.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonStepListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step Stared: {}", stepExecution.getStepName());
        stepExecution.getExecutionContext().put("startTime", System.currentTimeMillis());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        Long startTime = stepExecution.getExecutionContext().getLong("startTime");
        Long endTime = System.currentTimeMillis();

        log.info("Step {} Finished: {} ({}ms, Read: {}, Write: {}"
                , stepExecution.getStepName()
                , stepExecution.getStatus()
                , endTime - startTime
                , stepExecution.getReadCount()
                , stepExecution.getWriteCount());

        return stepExecution.getExitStatus();
    }
}
