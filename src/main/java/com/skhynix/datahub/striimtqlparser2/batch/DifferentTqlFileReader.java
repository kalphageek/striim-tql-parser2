package com.skhynix.datahub.striimtqlparser2.batch;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DifferentTqlFileReader  implements ItemReader<Employee> {
    private final String targetDirectory;
    private final String tempDirectory;
    private final List<File> differentCsvFiles;
    private int currentIndex;

    @Autowired
    public DifferentTqlFileReader(String targetDirectory, String tempDirectory) {
        this.targetDirectory = targetDirectory;
        this.tempDirectory = tempDirectory;
        this.differentCsvFiles = findDifferentCsvFiles();
        this.currentIndex = 0;
    }

    private List<File> findDifferentCsvFiles() {
        List<File> differentFiles = new ArrayList<>();
        File targetDir = new File(targetDirectory);
        File tempDir = new File(tempDirectory);

        if (!targetDir.isDirectory() || !tempDir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory path");
        }

        File[] targetFiles = targetDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));
        File[] tempFiles = tempDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (tempFiles == null) {
            throw new IllegalArgumentException("No CSV files found in the temp directory");
        }

        for (File tempFile : tempFiles) {
            boolean isDifferent = true;
            if (targetFiles != null) {
                for (File targetFile : targetFiles) {
                    if (tempFile.getName().equals(targetFile.getName())) {
                        try {
                            if (FileUtils.contentEquals(tempFile, targetFile)) {
                                isDifferent = false;
                                break;
                            }
                        } catch (IOException e) {
                            // Handle exception
                        }
                    }
                }
            }
            if (isDifferent) {
                differentFiles.add(tempFile);
            }
        }

        return differentFiles;
    }

    @Override
    public Employee read() {
        if (currentIndex < differentCsvFiles.size()) {
//            return differentCsvFiles.get(currentIndex++);
            return new Employee();
        }
        return null;
    }
}