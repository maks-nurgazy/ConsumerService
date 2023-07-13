package com.maksnurgazy.consumerservice.repositories;


import com.maksnurgazy.consumerservice.entities.FileInfo;
import com.maksnurgazy.consumerservice.enums.ProcessStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcessedFileInfoRepository extends JpaRepository<FileInfo, String> {
    List<FileInfo> findAllByStatusNot(ProcessStatus status);
    FileInfo findByStatus(ProcessStatus status);
    List<FileInfo> findAllByStatus(ProcessStatus status);
}
