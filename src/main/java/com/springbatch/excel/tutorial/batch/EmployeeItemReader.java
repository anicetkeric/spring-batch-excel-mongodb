package com.springbatch.excel.tutorial.batch;

import com.springbatch.excel.tutorial.batch.mappers.EmployeeItemRowMapper;
import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.support.poi.AbstractExcelPoi;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeItemReader extends AbstractExcelPoi<Employee> implements ItemReader<Employee> , StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeItemReader.class);

    private int employeeIndex = 0;

    private String excelFilePath;

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee read() {

        List<Employee> employeeList;
        Employee employee = null;

        try {

            String path = new ClassPathResource(excelFilePath).getFile().getPath();

            // read data in file
            employeeList = read(path, new EmployeeItemRowMapper());

            if(!employeeList.isEmpty()) {

                if (employeeIndex < employeeList.size()) {
                    employee = employeeList.get(employeeIndex);
                    employeeIndex++;
                } else {
                    employeeIndex = 0;
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Cannot read the excel file: {}", e.getMessage());
        }

        return employee;
    }

    @Override
    public void write(String filePath , List<Employee> aList) {
        throw new NotImplementedException("No need to implement this method in the context");
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        excelFilePath = stepExecution.getJobParameters().getString("excelPath");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
