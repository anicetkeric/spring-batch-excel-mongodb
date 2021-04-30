package com.springbatch.excel.tutorial.batch.processors;

import com.springbatch.excel.tutorial.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * @author Eric KOUAME
 */
public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeItemProcessor.class);


    public EmployeeItemProcessor() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Employee process(Employee item) {

        return item;
    }


}
