package com.skhynix.datahub.striimtqlparser2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StriimTqlParser2Controller {
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final ApplicationContext context;

    @PostMapping("/batch/{jobName}")
    public ResponseEntity runMyJob(@PathVariable("jobName") String jobName) {
        ExitStatus exitStatus;
        long currentTimeMillis = System.currentTimeMillis();
        Job job = context.getBean(jobName, Job.class);

        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .addLong("currentTimeMillis", currentTimeMillis)
                .getNextJobParameters(job)
                .toJobParameters();
        try {
            exitStatus = jobLauncher.run(job, jobParameters).getExitStatus();
        } catch (JobExecutionAlreadyRunningException e) {
            throw new RuntimeException(e);
        } catch (JobRestartException e) {
            throw new RuntimeException(e);
        } catch (JobInstanceAlreadyCompleteException e) {
            throw new RuntimeException(e);
        } catch (JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(exitStatus);
    }
}
