package com.maksnurgazy.consumerservice.services.impl;

import com.maksnurgazy.consumerservice.entities.FileInfo;
import com.maksnurgazy.consumerservice.enums.ProcessStatus;
import com.maksnurgazy.consumerservice.repositories.ProcessedFileInfoRepository;
import com.maksnurgazy.consumerservice.services.RecordProcessingService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class RecordProcessingServiceImpl implements RecordProcessingService {
    private static final String RECORDS_DIRECTORY = "./../ProcessingService/logs/records/";
    private static final String PROCESSED_DIRECTORY = "./../ProcessingService/logs/processed/";
    private static final int BATCH_SIZE = 100;

    private final ProcessedFileInfoRepository processedFileInfoRepository;
    private FileInfo fileInfo; // When application shuts down suddenly this object will be saved before application shuts down

    @PreDestroy
    public void onShutdown() {
        System.out.println("On shutdown ");
        System.out.println(fileInfo);
        if (fileInfo.getStatus() != ProcessStatus.PROCESSED) {
            fileInfo.setStatus(ProcessStatus.STOPPED);
        }

        processedFileInfoRepository.save(fileInfo);
    }

    @PostConstruct
    public void onStartup() {
        this.fileInfo = processedFileInfoRepository.findByStatus(ProcessStatus.STOPPED);
    }

    @Override
    public void processRecords() {
        if (Objects.nonNull(fileInfo) && fileInfo.getStatus() == ProcessStatus.STOPPED) {
            processFile(fileInfo);
        }

        List<FileInfo> files = processedFileInfoRepository.findAllByStatusNot(ProcessStatus.PROCESSED);
        System.out.println("not process size: " + files.size());
        files.forEach(this::processFile);
    }

    private void processFile(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        File file = new File(RECORDS_DIRECTORY + fileInfo.getFileName());
        int lastProcessedRecord = fileInfo.getLastProcessedRecord();

        List<String> records = new ArrayList<>();
        int recordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the line containing the record count
                }

                recordCount++;

                if (recordCount <= lastProcessedRecord) {
                    continue; // Skip already processed records
                }

                records.add(line);

                if (records.size() >= BATCH_SIZE) {
                    writeRecordsToFile(records);
                    records.clear();
                }
            }

            if (!records.isEmpty()) {
                writeRecordsToFile(records);
            }

            fileInfo.setLastProcessedRecord(recordCount);
            updateFileStatus(fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFileStatus(FileInfo fileInfo) {
        fileInfo.setStatus(ProcessStatus.PROCESSED);
        processedFileInfoRepository.save(fileInfo);
    }

    private void writeRecordsToFile(List<String> records) {
        System.out.println("writing to file");
        System.out.println(records.size());
        String fileName = fileInfo.getNextFileName();
        String filePath = Path.of(PROCESSED_DIRECTORY, fileName).toString();

        try (FileWriter writer = new FileWriter(filePath, true)) {
            for (String record : records) {
                writer.write(record);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
