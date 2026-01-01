package com.excelprocessor.processor.excel;

import java.util.Map;
import java.util.stream.Collectors;
import com.excelprocessor.processor.formula.FormulaFactory;
import com.fasterxml.jackson.databind.JsonNode;

public abstract class ExcelProcessorAbstract implements ExcelProcessor{

    protected abstract Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap);
	

	protected Map<String, String> processFormulaMap(Map<String, JsonNode> formulaMap)
	{
			
		// process formula map entries in parallel using parallel streams
        // This will likely create many short lived light weight formula processor objects. Possible one per thread. 
		return formulaMap.entrySet().parallelStream()
			.collect(Collectors.toConcurrentMap(
				Map.Entry::getKey,
				entry -> calculateFormula(entry.getValue()),
				(existing, replacement) -> existing
			));
	}

	protected String calculateFormula(JsonNode formula)
	{
        // The formula processor objects created by the factory will be short lived objects 
        // because its reference is not propogated further. Once the calculate method exists, the object is garbage collected in 
        // the next cycle. 
		return (new FormulaFactory().createFormula(formula.get("type").asText())).calculate(formula.get("formulaString").asText());
		
	}

	protected byte[] insertResultMapIntoExcel(Map<String, String> resultMap)
	{
        // insert the result map into the excel
        return new byte[0];
	}

}
