package com.springbatch.excel.tutorial.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author aek
 */
@Component
public class EmployeeJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeJobLauncher.class);

    private final Job job;

    private final JobLauncher jobLauncher;

    @Value("${employee.excel.path}")
    private String excelPath;

    EmployeeJobLauncher(Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "*/2 * * * *")
    void launchFileToJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        LOGGER.info("Starting job");

        jobLauncher.run(job, jobParameters());

        LOGGER.info("Stopping job");
    }

    private JobParameters jobParameters() {
        Map<String, JobParameter> parameters = new HashMap<>();

        parameters.put("currentTime", new JobParameter(new Date()));
        parameters.put("excelPath", new JobParameter(excelPath));

        return new JobParameters(parameters);
    }

}
