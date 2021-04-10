package com.springbatch.excel.tutorial.service;



import com.springbatch.excel.tutorial.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAll();

    Employee save(Employee employee);

    Optional<Employee> getById(String id);

    void deleteById(String id);
}