package com.excelprocessor.processor.formula;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of Formula interface for GETT-type formulas.
 */
public class GettFormula implements Formula {

	private static final Logger logger = LogManager.getLogger(GettFormula.class);

	@Override
	public String calculate(String formulaString) {
		logger.info("GETT called. With FormulaString: " + formulaString);
		return "1"; // TODO: implement proper return value
	}

	@Override
	public String getType() {
		return "GETT";
	}
}

