package com.maksnurgazy.consumerservice.entities;


import com.maksnurgazy.consumerservice.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FileInfo {
    @Id
    private String id;
    private String fileName;
    private Integer lastProcessedRecord;
    private Integer lastFileNumber;
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    public String getNextFileName() {
        if (lastProcessedRecord - lastFileNumber * 100 >= 100) {
            lastFileNumber++;
        }
        String suffix = String.format("%04d", lastFileNumber);
        String prefix = fileName.split("\\.")[0];

        return prefix + "-" + suffix + ".log";
    }
}