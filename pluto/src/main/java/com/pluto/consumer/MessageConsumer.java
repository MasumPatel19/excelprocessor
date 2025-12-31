package com.pluto.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    private static final String LOG_FILE_PATH = "/app/logs/messages.log";

    @RabbitListener(queues = "excel-processing-queue")
    public void consumeMessage(Map<String, Object> message) {
        String logMessage = String.format("[%s] Received message - ID: %s, Timestamp: %s, Status: %s, Details: %s",
                LocalDateTime.now(),
                message.get("id"),
                message.get("timestamp"),
                message.get("status"),
                message.get("details"));

        // Log to console
        logger.info(logMessage);

        // Log to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(logMessage);
            writer.flush();
        } catch (IOException e) {
            logger.error("Error writing to log file: {}", e.getMessage());
        }
    }
}


