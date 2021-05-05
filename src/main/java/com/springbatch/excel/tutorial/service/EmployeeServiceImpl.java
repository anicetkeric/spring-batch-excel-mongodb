package com.springbatch.excel.tutorial.service;

import com.springbatch.excel.tutorial.domain.Employee;
import com.springbatch.excel.tutorial.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Employee> getByNumber(String number) {
        return employeeRepository.findByNumber(number);
    }
}