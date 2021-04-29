package com.springbatch.excel.tutorial.batch;

import com.springbatch.excel.tutorial.batch.mappers.EmployeeItemRowMapper;
import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.support.poi.AbstractExcelPoi;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class EmployeeItemReader extends AbstractExcelPoi<Employee> implements ItemReader<Employee> {

    private int nextBeneficiaryIndex = 0;

    private final String filePath;

    public EmployeeItemReader(String filePath) {
        super();
        this.filePath = filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee read() {

        List<Employee> beneficiaryDossierDatas;
        Employee nextBeneficiary = null;

        // read data in file
        beneficiaryDossierDatas = read(filePath, new EmployeeItemRowMapper());

        if(!beneficiaryDossierDatas.isEmpty()) {

            if (nextBeneficiaryIndex < beneficiaryDossierDatas.size()) {
                nextBeneficiary = beneficiaryDossierDatas.get(nextBeneficiaryIndex);
                nextBeneficiaryIndex++;
            } else {
                nextBeneficiaryIndex = 0;
            }
        }

        return nextBeneficiary;
    }

    @Override
    public void write(String filePath , List<Employee> aList) {

    }
}
