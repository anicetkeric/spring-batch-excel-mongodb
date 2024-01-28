package com.springbatch.excel.tutorial.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author aek
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeJobLauncher {

    private final Job job;

    private final JobLauncher jobLauncher;

    @Value("${employee.excel.processingfolder}")
    private String processingDir;

    // run every 2 min
    @Scheduled(fixedRate = 120000)
    void launchFileToJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobInstanceAlreadyCompleteException, JobRestartException {
        log.info("Starting job");
        String excelFilePath = String.format("%s/employee.xlsx", processingDir);

        JobParameters params = new JobParametersBuilder()
                .addLong("jobId", System.currentTimeMillis())
                .addDate("currentTime", new Date())
                .addString("excelPath", excelFilePath)
                .toJobParameters();

        jobLauncher.run(job, params);

        log.info("Stopping job");
    }

}
