package com.springbatch.excel.tutorial.batch.processors;

import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.repository.EmployeeRepository;
import com.springbatch.excel.tutorial.support.poi.AbstractExcelPoi;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author Eric KOUAME
 */
public class EmployeeItemProcessor extends AbstractExcelPoi<Employee>  implements ItemProcessor<Employee, Employee>, StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeItemProcessor.class);

    private static final List<Employee> existingEmployees = new ArrayList<>();

    @Value("${employee.excel.resultsfolder}")
    private String resultsfolder;

    @Autowired
    EmployeeRepository repository;

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee process(Employee item) {

        if(repository.findByNumber(item.getNumber()).isPresent()){
            existingEmployees.add(item);

            return null;
        }

        return item;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        /* Nothing to do before */
    }

    @SneakyThrows
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        String jobId = stepExecution.getJobParameters().getString("jobId");

        if(stepExecution.getStatus() == BatchStatus.COMPLETED && !CollectionUtils.isEmpty(existingEmployees)) {
            // create log file. if completed
            String path = new ClassPathResource(resultsfolder).getFile().getPath();
            write(String.format("%s/employe-result-%s.xlsx", path, jobId),existingEmployees);
        }
        return null;
    }
    /**
     * Create and write results file. employee duplicate entries
     * @param filePath file path
     * @param aList    list of employee
     */
    @Override
    public void write(String filePath , List<Employee> aList) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            // Create a Sheet
            Sheet sheet = workbook.createSheet("RESULTS");

            // creation header
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            Font font = sheet.getWorkbook().createFont();
            font.setBold(true);
            font.setFontHeightInPoints((short) 12);
            cellStyle.setFont(font);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            List<String> headers = Arrays.asList("FirstName",
                    "LastName",
                    "Email",
                    "Number"
            );
            createHeaderRow(sheet, headers,cellStyle);

            // Create Other rows and cells with BeneficiaryExtraction data
            int rowNum = 1;
            for(Employee employee: aList) {
                Row row = sheet.createRow(rowNum++);

                createCell(row, 0, employee.getFirstName(), null);
                createCell(row, 1, employee.getLastName(), null);
                createCell(row, 2, employee.getEmail(), null);
                createCell(row, 3, employee.getNumber(), null);
            }

            //lets write the excel data to file now
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
                LOGGER.info("Results File written successfully");
            }
        }
        catch (IOException e) {
            LOGGER.warn("Cannot write results file: {}", e.getMessage());
        }finally {
            existingEmployees.clear();
        }
    }
}
