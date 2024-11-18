package kr.co.kst.batch.common;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class JobParameterValidator implements JobParametersValidator {

    @Override
    public void validate(JobParameters parameters) throws JobParametersInvalidException {
        String requestDate = parameters.getString("requestDate");

        if (requestDate == null) {
            throw new JobParametersInvalidException("requestDate is required");
        }

        try {
            LocalDate.parse(requestDate, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new JobParametersInvalidException("requestDate is invalid");
        }

    }
}
