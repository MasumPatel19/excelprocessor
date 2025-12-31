package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class DataResponseExcelProcessor extends ExcelProcessorAbstract {

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
		
        Map<String, JsonNode> formulaMap = excelProcessorContext.getFormulaMap();

        // download the excel submited by the portco via email or portco portal upload

        // run validation on the excel

        Map<String, JsonNode> preprocessedFormulaMap = preprocessFormulaMap(formulaMap);
        // process the formula map
        Map<String, String> resultMap = processFormulaMap(formulaMap);

	}

    protected Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap)
	{
		
        // remove all the other formulae which don't need to be processed at this stage (e.g get etc)
        
        // replace all pushT formulae in the formula map with underlying push formulae and value 
        // that need to be pushed
        return formulaMap;
	}
}
