package com.skhynix.datahub.striimtqlparser2.batch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class ExtractTqlFileTasklet implements Tasklet {
    @Value("${striim.source.directory}")
    private String sourceDirectory;

    @Value("${striim.target.directory}")
    private String targetDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws IOException {
        File sourceDir = new File(sourceDirectory);
        File targetDir = new File(targetDirectory);

        if (!sourceDir.isDirectory()) {
            throw new IllegalArgumentException("Source directory does not exist or is not a directory");
        }

        if (!targetDir.isDirectory()) {
            throw new IllegalArgumentException("Target directory does not exist or is not a directory");
        }

        File[] csvFiles = sourceDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            throw new IllegalArgumentException("No CSV files found in the source directory");
        }

        for (File csvFile : csvFiles) {
            Path sourcePath = csvFile.toPath();
            Path targetPath = targetDir.toPath().resolve(csvFile.getName());
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return RepeatStatus.FINISHED;
    }
}
