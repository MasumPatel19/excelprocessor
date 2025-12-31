package com.excelprocessor.processor.formula;

/**
 * Interface representing a formula that can be calculated.
 * Different formula types implement this interface to provide specific calculation logic.
 */
public interface Formula {

	/**
	 * Calculates the formula result
	 * 
	 * @return the calculated result as a String
	 */
	String calculate();
	String getType();
}

