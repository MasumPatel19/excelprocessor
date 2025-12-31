package com.excelprocessor.processor.formula;

/**
 * Implementation of Formula interface for PUSH-type formulas.
 */
public class PushFormula implements Formula {

	@Override
	public String calculate() {
		return null;
	}

	@Override
	public String getType() {
		return "PUSH";
	}
}

