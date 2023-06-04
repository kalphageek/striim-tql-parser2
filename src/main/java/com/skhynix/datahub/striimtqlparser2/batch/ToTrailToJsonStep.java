package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.CdcJobEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Component
public class ToTrailToJsonStep implements StepExecutionListener {
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final CdcJobReader itemReader;
    private final CdcJobWriter itemWriter;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("toTrailToJsonStep start");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("toTrailToJsonStep finish");
        return ExitStatus.COMPLETED;
    }

    @Bean
    public Step runStep() {
        return stepBuilderFactory.get("toTrailToJsonStep")
                .<CdcJobEntity, CdcJobEntity>chunk(10)
                .reader(itemReader)
                .writer(itemWriter)
                .transactionManager(transactionManager())
                .listener(this)
                .build();
    }

    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
