package kr.co.kst.batch.job.job1.step;

import kr.co.kst.batch.domain.entity.ProcessedSample;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomItemWriter implements ItemWriter<ProcessedSample> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void write(Chunk<? extends ProcessedSample> chunk) throws Exception {
        String sql = "INSERT INTO processed_sample(id, name) values (? , ?)";

        for (ProcessedSample sample : chunk) {
            jdbcTemplate.update(sql, sample.getId(), sample.getName());
        }

    }
}
