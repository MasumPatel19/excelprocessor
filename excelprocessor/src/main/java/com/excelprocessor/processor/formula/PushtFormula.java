package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Formula interface for PUSHT-type formulas.
 */
public class PushtFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(PushtFormula.class);

	@Override
	public String calculate(String formulaString) {
		logger.info("calculate method invoked");
		return null;
	}

	@Override
	public String getType() {
		return "PUSHT";
	}
}

