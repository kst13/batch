package kr.co.kst.batch.job.job1.step;

import kr.co.kst.batch.domain.entity.SampleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomItemReader implements ItemReader<SampleEntity> {
    private final JdbcTemplate jdbcTemplate;
    private int currentIndex = 0;
    private List<SampleEntity> dataBuffer;

    @Override
    public SampleEntity read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        if (dataBuffer == null || currentIndex >= dataBuffer.size()) {
            fetchDataFromDatabase();
        }
        if (currentIndex < dataBuffer.size()) {
            return dataBuffer.get(currentIndex++);
        }

        return null;
    }

    private void fetchDataFromDatabase() {
        int pageSize = 10; // 페이지 크기
        int startRow = (currentIndex / pageSize) * pageSize + 1;
        int endRow = startRow + pageSize - 1;

        String sql = "SELECT * FROM (" +
                " SELECT ROW_NUMBER() OVER (ORDER BY id) AS row_num, id, name, email" +
                " FROM users" +
                ") AS temp WHERE row_num BETWEEN ? AND ?";

        dataBuffer = jdbcTemplate.query(sql, new Object[]{startRow, endRow},
                (rs, rowNum) -> new SampleEntity(rs.getString("id"), rs.getString("name"))
        );
        currentIndex = 0; // 인덱스 초기화
    }
}
