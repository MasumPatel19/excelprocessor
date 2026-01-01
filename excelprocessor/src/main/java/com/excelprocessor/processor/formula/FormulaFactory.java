package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating Formula instances.
 * Creates formula objects based on formula type string.
 */
@Component
public class FormulaFactory {

	private static final Logger logger = LogManager.getLogger(FormulaFactory.class);

	/**
	 * Creates a Formula instance based on the formula type string.
	 * 
	 * @param formulaType the formula type string (GET, GETT, PUSH, or PUSHT)
	 * @return Formula instance, or null if formulaType is invalid
	 */
	public Formula createFormula(String formulaType) {
		logger.info("createFormula method invoked");
		if (formulaType == null) {
			return null;
		}

		String type = formulaType.trim().toUpperCase();

		switch (type) {
			case "GET":
				return new GetFormula();
			case "GETT":
				return new GettFormula();
			case "PUSH":
				return new PushFormula();
			case "PUSHT":
				return new PushtFormula();
			default:
				return null;
		}
	}
}

