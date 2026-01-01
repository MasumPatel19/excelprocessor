package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.time.LocalDateTime;

/**
 * Implementation of Formula interface for GET-type formulas.
 */
public class GetFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(GetFormula.class);

    // @Autowired
    // private RabbitTemplate rabbitTemplate;

	@Override
	public String calculate(String formulaString) {

		//TODO: Hard-coding this for initial test purposes. 
		logger.info("GET called. With FormulaString: " + formulaString);
		// Map<String, Object> message = new HashMap<>();
        // message.put("id", UUID.randomUUID().toString());
        // message.put("timestamp", LocalDateTime.now().toString());
        // message.put("status", "PROCESSING");
        // message.put("details", "GET called. With FormulaString: " + formulaString);

        // rabbitTemplate.convertAndSend("excel-processing-queue", message);
        return "1"; // TODO: implement proper return value
	}

	@Override
	public String getType() {
		return "GET";
	}
}

