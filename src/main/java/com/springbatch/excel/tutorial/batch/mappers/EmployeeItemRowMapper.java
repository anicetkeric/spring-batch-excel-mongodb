package com.springbatch.excel.tutorial.batch.mappers;

import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.support.poi.CellFactory;
import com.springbatch.excel.tutorial.support.poi.RowMapper;
import org.apache.poi.ss.usermodel.Row;


public class EmployeeItemRowMapper extends CellFactory implements RowMapper<Employee> {

    @Override
    public Employee transformerRow(Row row) {
        return new Employee(null,
                (String) getCellValue(row.getCell(0)),
                (String) getCellValue(row.getCell(1)),
                (String) getCellValue(row.getCell(2)),
                (String) getCellValue(row.getCell(3)),
                (String) getCellValue(row.getCell(4)),
                (Double) getCellValue(row.getCell(5)));
    }
}
