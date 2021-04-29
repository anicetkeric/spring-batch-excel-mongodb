package com.springbatch.excel.tutorial.batch.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionListener.class);

    public JobCompletionListener() {
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String pathToExtractionFile = jobExecution.getJobParameters().getString("pathToFile");
        String jobId = jobExecution.getJobParameters().getString("jobId");

         // get job's start time
        Date start = jobExecution.getCreateTime();
        //  get job's end time
        Date end = jobExecution.getEndTime();

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {

            LOGGER.trace("===========================JOB FINISHED================================================");
            LOGGER.trace("JobId      : {}",jobId);
            LOGGER.trace("Path file     : {}", pathToExtractionFile);
            LOGGER.trace("Date: {}", end);
            LOGGER.trace("=======================================================================================");
        }

    }

}