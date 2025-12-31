package com.example.scheduler.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class SchedulerController {

    private final WebClient webClient;

    public SchedulerController(@Value("${excelprocessor.url:http://excelprocessor:8080}") String excelprocessorUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(excelprocessorUrl)
                .build();
    }

    @PostMapping("/trigger")
    public ResponseEntity<String> trigger() {
        try {
            String response = webClient.post()
                    .uri("/process")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            
            return ResponseEntity.ok("Triggered successfully. Response: " + response);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Error triggering process: " + e.getMessage());
        }
    }
}


