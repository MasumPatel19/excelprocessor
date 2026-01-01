package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Formula interface for GETT-type formulas.
 */
public class GettFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(GettFormula.class);

	@Override
	public String calculate() {
		logger.info("calculate method invoked");
		return null;
	}

	@Override
	public String getType() {
		return "GETT";
	}
}

