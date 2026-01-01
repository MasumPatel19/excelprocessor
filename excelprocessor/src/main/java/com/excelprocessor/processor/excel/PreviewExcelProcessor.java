package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.excelprocessor.service.DataRequestExcelPreviewGeneratorContext;

public class PreviewExcelProcessor extends ExcelProcessorAbstract{


    DataRequestExcelPreviewGeneratorContext dataRequestExcelPreviewGeneratorContext;
    
	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {

        if (excelProcessorContext instanceof DataRequestExcelPreviewGeneratorContext) {
            dataRequestExcelPreviewGeneratorContext = (DataRequestExcelPreviewGeneratorContext) excelProcessorContext;
            } else {
                throw new IllegalArgumentException("ExcelProcessorContext must be of type DataRequestExcelPreviewGeneratorContext");
            }

        // preprocess the formula map. This is where the logic that 
        // currently resides in the frontend will be moved to.
        Map<String, JsonNode> preprocessedFormulaMap = preprocessFormulaMap(dataRequestExcelPreviewGeneratorContext.getFormulaMap());
        // process the formula map
        Map<String, String> resultMap = processFormulaMap(preprocessedFormulaMap);

        // insert the result map back into the excel
        insertResultMapIntoExcel(resultMap);

        return new byte[0];
	}

    protected Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap)
	{
		// Perform ID lookups for get formulae

        return formulaMap;
	}
}
