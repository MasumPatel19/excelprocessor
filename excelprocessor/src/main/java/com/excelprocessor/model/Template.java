package com.excelprocessor.model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

/**
 * Domain object representing a Template.
 */
public class Template {
	
	private Map<String, JsonNode> formulaMap;
	
	public Map<String, JsonNode> getFormulaMap() {
		return formulaMap;
	}
	
	public void setFormulaMap(Map<String, JsonNode> formulaMap) {
		this.formulaMap = formulaMap;
	}
}

