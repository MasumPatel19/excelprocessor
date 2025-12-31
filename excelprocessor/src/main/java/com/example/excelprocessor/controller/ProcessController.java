package com.example.excelprocessor.controller;

import com.example.excelprocessor.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProcessController {

    @Autowired
    private ProcessService processService;

    @PostMapping("/process")
    public ResponseEntity<String> process() {
        processService.processRequest();
        return ResponseEntity.ok("Process request received and queued");
    }
}


