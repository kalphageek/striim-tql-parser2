package com.skhynix.datahub.striimtqlparser2.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringBatchTest
class BatchConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    public void createJobTest() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "MyJob")
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // then
        assertEquals(BatchStatus.COMPLETED,jobExecution.getStatus());
        assertEquals(ExitStatus.COMPLETED,jobExecution.getExitStatus());
    }

    @Test
    public void createStepTest() throws Exception {
        // given
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("name", "MyJob")
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("MyStep");

        // then
        assertEquals(BatchStatus.COMPLETED,jobExecution.getStatus());
    }
}