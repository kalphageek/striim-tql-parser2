package com.skhynix.datahub.striimtqlparser2.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ToTrailToJsonStep implements StepExecutionListener {
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<String> itemReader;
    private final ItemWriter<String> itemWriter;


    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Bean
    public Step runStep() {
        return stepBuilderFactory.get("toTrailToJsonStep")
                .<String, String>chunk(10)
                .reader(itemReader)
                .writer(itemWriter)
                .listener(this)
                .build();
    }
}
