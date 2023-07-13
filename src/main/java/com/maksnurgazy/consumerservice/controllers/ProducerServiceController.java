package com.maksnurgazy.consumerservice.controllers;

import com.maksnurgazy.consumerservice.services.RecordProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/services")
public class ProducerServiceController {
    private final RecordProcessingService recordProcessingService;

    @GetMapping()
    public void processRecord(){
        recordProcessingService.processRecords();
    }

}
