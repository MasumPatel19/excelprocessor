package com.example.excelprocessor.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processRequest() {
        Map<String, Object> message = new HashMap<>();
        message.put("id", UUID.randomUUID().toString());
        message.put("timestamp", LocalDateTime.now().toString());
        message.put("status", "PROCESSING");
        message.put("details", "Excel processing request received");

        rabbitTemplate.convertAndSend("excel-processing-queue", message);
    }
}


