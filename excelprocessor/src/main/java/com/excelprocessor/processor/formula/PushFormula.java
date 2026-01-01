package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Formula interface for PUSH-type formulas.
 */
public class PushFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(PushFormula.class);

	@Override
	public String calculate(String formulaString) {
		logger.info("PUSH called. With FormulaString: " + formulaString);
		return "1"; // TODO: implement proper return value
	}

	@Override
	public String getType() {
		return "PUSH";
	}
}

