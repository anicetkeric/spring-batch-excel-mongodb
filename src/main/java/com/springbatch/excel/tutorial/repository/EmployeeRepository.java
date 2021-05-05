package com.springbatch.excel.tutorial.repository;

import com.springbatch.excel.tutorial.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByNumber(String number);
}
