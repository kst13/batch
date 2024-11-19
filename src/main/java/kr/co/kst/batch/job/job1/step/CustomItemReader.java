package kr.co.kst.batch.job.job1.step;

import kr.co.kst.batch.domain.entity.SampleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomItemReader implements ItemReader<SampleEntity> {
    private final JdbcTemplate jdbcTemplate;
    private static final int PAGE_SIZE = 10; // 페이지 크기
    private int currentPage = 0; // 현재 페이지
    private List<SampleEntity> dataBuffer = new ArrayList<>();

    @Override
    public SampleEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (dataBuffer.isEmpty()) {
            fetchDataFromDatabase();
        }
        if (!dataBuffer.isEmpty()) {
            return dataBuffer.remove(0);
        }

        return null;
    }

    private void fetchDataFromDatabase() {
        String sql = "SELECT id, name FROM users ORDER BY id LIMIT ? OFFSET ?";
        int offset = currentPage * PAGE_SIZE;

        dataBuffer = jdbcTemplate.query(sql, new Object[]{PAGE_SIZE, offset},
                (rs, rowNum) -> new SampleEntity(rs.getString("id"), rs.getString("name"))
        );

        // 데이터가 있으면 다음 페이지로 이동
        if (!dataBuffer.isEmpty()) {
            currentPage++;
        }
    }
}
