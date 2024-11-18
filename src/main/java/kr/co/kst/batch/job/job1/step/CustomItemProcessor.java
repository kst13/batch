package kr.co.kst.batch.job.job1.step;

import kr.co.kst.batch.domain.entity.ProcessedSample;
import kr.co.kst.batch.domain.entity.SampleEntity;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomItemProcessor implements ItemProcessor<SampleEntity, ProcessedSample> {
    @Override
    public ProcessedSample process(SampleEntity item) throws Exception {
        return new ProcessedSample(item.getId(), item.getName());
    }
}
