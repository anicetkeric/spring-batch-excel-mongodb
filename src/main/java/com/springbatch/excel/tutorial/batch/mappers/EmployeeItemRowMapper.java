package com.springbatch.excel.tutorial.batch.mappers;

import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.support.poi.CellFactory;
import com.springbatch.excel.tutorial.support.poi.RowMapper;
import org.apache.poi.ss.usermodel.Row;


public class EmployeeItemRowMapper extends CellFactory implements RowMapper<Employee> {

    @Override
    public Employee transformerRow(Row row) {
        Employee employee = new Employee();

        employee.setFirstName((String) getCellValue(row.getCell(0)));
        employee.setLastName((String) getCellValue(row.getCell(1)));
        employee.setNumber((String) getCellValue(row.getCell(2)));
        employee.setEmail((String) getCellValue(row.getCell(3)));
        employee.setDepartment((String) getCellValue(row.getCell(4)));
        employee.setSalary((Double) getCellValue(row.getCell(5)));

        return employee;
    }
}
