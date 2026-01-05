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
		//	TODO: Step 1 - Create a workbook, sheet ( or take the old template worksheet with styling and empty sheets and recreate)
		//	TODO: Step 2 - Iterate through resultMap entries (cellReference -> calculatedValue)
		//	TODO: Step 3 - Parse cell reference (e.g., "A1", "B5") to row and column indices
		//	TODO: Step 4 - Get or create cell at the specified location
		//	TODO: Step 5 - Set cell value type (STRING, NUMERIC, etc.) based on value
		//	TODO: Step 6 - Set cell value from resultMap
		//	TODO: Step 7 - Apply cell formatting ( not sure. maybe be take the template with style before any processing and used. and add result map in that)
		//	TODO: Step 8 - Handle invalid cell references with error logging
		//	TODO: Step 9 - Convert workbook to byte array using ByteArrayOutputStream
		//	TODO: Step 10 - Close workbook
		//	TODO: Step 11 - Return Excel file as byte array

		return new byte[0];
	}

}
