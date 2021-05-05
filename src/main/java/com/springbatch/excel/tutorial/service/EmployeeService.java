package com.springbatch.excel.tutorial.service;



import com.springbatch.excel.tutorial.domain.Employee;

import java.util.Optional;

public interface EmployeeService {

    /**
     * @param number employee nuumber
     * @return Optional employee
     */
    Optional<Employee> getByNumber(String number);
}