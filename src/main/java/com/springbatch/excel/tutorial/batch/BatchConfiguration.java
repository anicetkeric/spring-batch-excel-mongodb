package com.springbatch.excel.tutorial.batch;

import com.springbatch.excel.tutorial.batch.listeners.JobCompletionListener;
import com.springbatch.excel.tutorial.batch.processors.EmployeeItemProcessor;
import com.springbatch.excel.tutorial.batch.validators.EmployeeJobParametersValidator;
import com.springbatch.excel.tutorial.domain.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

/**
 * Configuration for batch
 */
@Configuration
@RequiredArgsConstructor
public class BatchConfiguration {

    public final JobRepository jobRepository;
    public final MongoTemplate mongoTemplate;
    public final PlatformTransactionManager transactionManager;


    @Bean
    public JobParametersValidator jobParametersValidator() {
        return new EmployeeJobParametersValidator();
    }

    @Bean
    public JobParametersValidator compositeJobParametersValidator() {
        CompositeJobParametersValidator bean = new CompositeJobParametersValidator();
        bean.setValidators(Collections.singletonList(jobParametersValidator()));
        return bean;
    }

    @Bean
    public ItemProcessor<Employee, Employee> itemProcessor() {
        return new EmployeeItemProcessor();
    }

    @Bean
    public ItemReader<Employee> itemReader() {
        return new EmployeeItemReader();
    }

    @Bean
    public MongoItemWriter<Employee> writerMongo() {
        return new MongoItemWriterBuilder<Employee>()
                .template(mongoTemplate)
                .collection("employee")
                .build();
    }


    @Bean
    public Step employeeStep() {
        return new StepBuilder("employeeStep", jobRepository)
                .<Employee, Employee>chunk(50, transactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(writerMongo())
                .build();
    }

    @Bean
    public JobCompletionListener listener() {
        return new JobCompletionListener();
    }


    /**
     * job declaration
     *
     * @return {@link Job}
     */
    @Bean
    public Job employeeJob() {
        return new JobBuilder("employeeJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(employeeStep())
                .end()
                .validator(compositeJobParametersValidator())
                .build();
    }

}
