package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.batch.backup.CustomEntityManagerReader;
import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import com.skhynix.datahub.striimtqlparser2.common.Constants;
import com.skhynix.datahub.striimtqlparser2.secondary.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
//    private final MyCustomReader myCustomReader;
    private final CustomEntityManagerReader myCustomReader;
    private final MyCustomWriter myCustomWriter;
    private final MyCustomProcessor myCustomProcessor;
    private final ExtractTqlFileTasklet extractTqlFileTasklet;
    private final DifferentTqlFileReader differentTqlFileReader;

    @Qualifier(Constants.SecondaryTransactionManager)
    private final PlatformTransactionManager transactionManager;

    @Bean(name = "MyJob")
    public Job createJob() {
        return jobBuilderFactory.get("MyJob")
                .incrementer(new RunIdIncrementer())
                .flow(copyCsvStep())
                    .on("FAILED").fail()
                    .on("*").to(createStep()).end()
                .build();
    }
    @Bean
    public TaskletStep copyCsvStep() {
        return stepBuilderFactory.get("copyCsvStep")
                .tasklet(extractTqlFileTasklet)
                .build();
    }
    @Bean
    public Step createStep() {
        return stepBuilderFactory.get("MyStep")
                .<Employee, Manager> chunk(1)
                .reader(differentTqlFileReader)
                .processor(myCustomProcessor)
                .writer(myCustomWriter)
                .transactionManager(transactionManager)
                .build();
    }
}