package kr.co.kst.batch.common;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class BatchUtil {

    private BatchUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static JobParameters createJobParameters(String requestDate) {
        return new JobParametersBuilder()
                .addString("requestDate", requestDate)
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
    }

    public static void validateJobParameters(JobParameters jobParameters) {

    }

    public static String generateJobName(String baseJobName, JobParameters params) {
        return String.format("%s_%s", baseJobName, params.getString("requestDate"));
    }

    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
