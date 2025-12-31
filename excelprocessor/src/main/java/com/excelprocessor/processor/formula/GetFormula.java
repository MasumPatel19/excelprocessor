package com.excelprocessor.processor.formula;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
	}

	@Override
	public String getType() {
		return "GET";
	}
}

