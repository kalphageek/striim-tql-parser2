package com.skhynix.datahub.striimtqlparser2.batch;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.skhynix.datahub.striimtqlparser2.feign.MockCacheApi;
import com.skhynix.datahub.striimtqlparser2.feign.MockCacheApiConfig;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@SpringBatchTest
@ContextConfiguration(classes = { MockCacheApiConfig.class })
class BatchConfigTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    JobExplorer jobExplorer;
    @Autowired
    WireMockServer mockCacheApi;

    @Test
    public void createJobTest() throws Exception {
        String jobName = "MyJob";
        Job job = context.getBean(jobName, Job.class);
        Long currentTimeMillis = System.currentTimeMillis();

        // given
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .addLong("currentTimeMillis", currentTimeMillis)
                .getNextJobParameters(job)
                .toJobParameters();

        MockCacheApi.setupStriimApiClient_getStatus(mockCacheApi);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(BatchStatus.COMPLETED,jobExecution.getStatus());
        assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus());

    }

    @Test
    public void createStepTest() throws Exception {
        String jobName = "MyJob";
        Job job = context.getBean(jobName, Job.class);
        Long currentTimeMillis = System.currentTimeMillis();

        // given
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .addLong("currentTimeMillis", currentTimeMillis)
                .getNextJobParameters(job)
                .toJobParameters();

        MockCacheApi.setupStriimApiClient_getStatus(mockCacheApi);

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("MyStep");

        // then
        assertEquals(BatchStatus.COMPLETED,jobExecution.getStatus());
    }
}