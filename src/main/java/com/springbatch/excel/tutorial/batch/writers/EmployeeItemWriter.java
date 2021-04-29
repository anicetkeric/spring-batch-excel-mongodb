package com.springbatch.excel.tutorial.batch.writers;

import com.springbatch.excel.tutorial.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class EmployeeItemWriter implements ItemWriter<Employee>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeItemWriter.class);

    public EmployeeItemWriter() {
        super();
    }

    @Override
    public void write(List<? extends Employee> items) throws Exception {

    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        /* Nothing to do before */
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        if(stepExecution.getStatus() == BatchStatus.COMPLETED) {

        }
        return null;
    }

}
