package com.excelprocessor.processor.formula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Implementation of Formula interface for GET-type formulas.
 */
public class GetFormula implements Formula {

    @Autowired
    private RabbitTemplate rabbitTemplate;

	@Override
	public String calculate() {
		Map<String, Object> message = new HashMap<>();
        message.put("id", UUID.randomUUID().toString());
        message.put("timestamp", LocalDateTime.now().toString());
        message.put("status", "PROCESSING");
        message.put("details", "Excel processing request received");

        rabbitTemplate.convertAndSend("excel-processing-queue", message);
        return null; // TODO: implement proper return value
	}

	@Override
	public String getType() {
		return "GET";
	}
}

