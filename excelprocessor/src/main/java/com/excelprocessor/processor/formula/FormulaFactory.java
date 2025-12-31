package com.excelprocessor.processor.formula;

import org.springframework.stereotype.Component;

/**
 * Factory class for creating Formula instances.
 * Creates formula objects based on formula type string.
 */
@Component
public class FormulaFactory {

	/**
	 * Creates a Formula instance based on the formula type string.
	 * 
	 * @param formulaType the formula type string (GET, GETT, PUSH, or PUSHT)
	 * @return Formula instance, or null if formulaType is invalid
	 */
	public Formula createFormula(String formulaType) {
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

