package com.skhynix.datahub.striimtqlparser2.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class StriimPargerConfig {
    /**
     * 1. job
     * 2. step
     * 3. reader
     * 3.1 tql parser (open feign)
     * 4. writer
     */

    /**
     * tql parser
     * 1. export tql
     * 2.1. export tql parsing (return parsing dir)
     * 2.1. compare old / new files -> 달라진 files parsing (return parsing result)
     * 2.2. parsing files retrieve source/target tables -> striim cli (return retrieve data)
     * 2.3. replace new files to old files
     * 2.4. return parsing result (return parsing result)
     */
    private final JobBuilderFactory jobBuilderFactory;
    private final ToTrailToJsonStep toTrailToJsonStep;

    @Bean
    public Job striimParserJob() {
        log.info("striimParserJob start");
        return jobBuilderFactory.get("striimParserJob")
                .incrementer(new RunIdIncrementer())
                .start(toTrailToJsonStep.runStep())
                .build();
    }
}
