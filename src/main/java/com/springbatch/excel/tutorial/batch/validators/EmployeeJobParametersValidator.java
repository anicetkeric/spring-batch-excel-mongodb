package com.springbatch.excel.tutorial.batch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.util.StringUtils;

public class EmployeeJobParametersValidator implements org.springframework.batch.core.JobParametersValidator {

	@Override
	public void validate(final JobParameters jobParameters) throws JobParametersInvalidException {
		String fileName = jobParameters != null ? jobParameters.getString("excelPath") : null;

		if (fileName !=null && !StringUtils.endsWithIgnoreCase(fileName, "xlsx")) {
			throw new JobParametersInvalidException("The file type must be in xlsx format");
		}
	}

}
