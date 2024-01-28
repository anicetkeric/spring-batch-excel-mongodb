package com.springbatch.excel.tutorial;
import com.springbatch.excel.tutorial.batch.BatchConfiguration;
import com.springbatch.excel.tutorial.batch.listeners.JobCompletionListener;
import com.springbatch.excel.tutorial.domain.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@ExtendWith(SpringExtension.class)
@EnableBatchProcessing
@ComponentScan(basePackages = "com.springbatch.excel.tutorial")
@SpringBootTest(classes = {BatchConfiguration.class, JobCompletionListener.class})
class BatchIntegrationTest {

    @Autowired
    private  JobLauncher jobLauncher;

    @Autowired
    private  Job jsonFileProcessingJob;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testBatchJob() throws Exception {
        String absolutePath = Objects.requireNonNull(getClass().getResource("/data/processing/employee.xlsx")).getPath();

        // Créez les paramètres du job
        Map<String, JobParameter> jobParameters = new HashMap<>();
        jobParameters.put("filePath", new JobParameter(absolutePath));
        jobParameters.put("currentTime", new JobParameter(new Date()));

        // Lancez le job avec les paramètres
        JobExecution jobExecution = jobLauncher.run(jsonFileProcessingJob,new JobParameters(jobParameters));

        // Vérifiez que le job s'est terminé avec succès
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

        List<Employee> couponOffers = mongoTemplate.findAll(Employee.class);
        assertThat(couponOffers.size()).isEqualTo(100);

    }
}


