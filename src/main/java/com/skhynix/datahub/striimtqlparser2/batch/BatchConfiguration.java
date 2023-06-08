package com.skhynix.datahub.striimtqlparser2.batch;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.MyData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Qualifier("cataloagEntityManager")
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private EntityManagerFactory writerEntityManagerFactory;

    @Qualifier("oracleDataSource")
    @Autowired
    private DataSource dataSource;

    @Qualifier("oracleTransactionManager")
    @Autowired
    private JpaTransactionManager transactionManager;

    @Bean
    public ItemReader<MyData> itemReader() {

//        JpaPagingItemReader<MyData> reader = new JpaPagingItemReader<>();
//        reader.setEntityManagerFactory(writerEntityManagerFactory);
//        reader.setQueryString("SELECT p FROM MyData p");
//        reader.setPageSize(10);
//        return reader;

        FlatFileItemReader<MyData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("input.csv"));
        reader.setLineMapper(new DefaultLineMapper<MyData>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("name", "age"); // CSV 파일의 각 열에 해당하는 필드명
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<MyData>() {{
                setTargetType(MyData.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ItemProcessor<MyData, MyData> itemProcessor() {
        // 데이터 변환 또는 유효성 검사 등을 수행할 수 있는 ItemProcessor 구현체를 작성합니다.
        return item -> {
            // 예시: 나이가 18 이하인 데이터만 필터링
            if (item.getAge() > 18) {
                return item;
            } else {
                return null; // 필터링된 데이터는 건너뜁니다.
            }
        };
    }

    @Bean
    public ItemWriter<MyData> itemWriter() {
        return items -> {
            // JPA를 사용하여 Oracle DB에 데이터를 저장합니다.
            for (MyData item : items) {
                // 저장 로직 구현
            }
        };
    }

    @Bean
    public Job myJob() {
        return jobBuilderFactory.get("myJob")
                .start(myStep())
                .build();
    }

    @Bean
    public Step myStep() {
        return stepBuilderFactory.get("myStep")
                .<MyData, MyData>chunk(10)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .transactionManager(transactionManager)
                .build();
    }
}