package com.springbatch.excel.tutorial.batch;

import com.springbatch.excel.tutorial.batch.listeners.JobCompletionListener;
import com.springbatch.excel.tutorial.batch.processors.EmployeeItemProcessor;
import com.springbatch.excel.tutorial.batch.validators.EmployeeJobParametersValidator;
import com.springbatch.excel.tutorial.batch.writers.EmployeeItemWriter;
import com.springbatch.excel.tutorial.domain.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Configuration for batch
 */
@EnableBatchProcessing
@Configuration
public class BatchConfiguration {

    public final JobBuilderFactory jobBuilderFactory;

    public final StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

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
    @StepScope
    public ItemReader<Employee> itemReader(@Value("#{jobParameters[excelPath]}") String pathToFile) {
        return new EmployeeItemReader(pathToFile);
    }

    @Bean
    public ItemWriter<Employee> itemWriter() {
        return new EmployeeItemWriter();
    }

    /**
     * Declaration step
     * @return {@link Step}
     */
    @Bean
    public Step employeeStep() {
        return stepBuilderFactory.get("employeeStep")
                .<Employee, Employee>chunk(1)
                .reader(itemReader(null))
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    /**
     * Declaration job
     * @param listener {@link JobCompletionListener}
     * @return {@link Job}
     */
    @Bean
    public Job employeeJob(JobCompletionListener listener) {
        return jobBuilderFactory.get("employeeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(employeeStep())
                .end()
                .validator(compositeJobParametersValidator())
                .build();
    }

}
