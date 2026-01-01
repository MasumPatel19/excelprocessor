package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Formula interface for PUSH-type formulas.
 */
public class PushFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(PushFormula.class);

	@Override
	public String calculate() {
		logger.info("calculate method invoked");
		return null;
	}

	@Override
	public String getType() {
		return "PUSH";
	}
}

