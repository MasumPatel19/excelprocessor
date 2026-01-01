package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.excelprocessor.service.DataRequestExcelGeneratorContext;

public class DataRequestExcelProcessor extends ExcelProcessorAbstract {

        DataRequestExcelGeneratorContext dataRequestExcelGeneratorContext;

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {

                if (excelProcessorContext instanceof DataRequestExcelGeneratorContext) {
                dataRequestExcelGeneratorContext = (DataRequestExcelGeneratorContext) excelProcessorContext;
                } else {
                throw new IllegalArgumentException("ExcelProcessorContext must be of type DataRequestExcelGeneratorContext");
                }
                
                // preprocess the formula map. This is where the logic that 
                // currently resides in the frontend will be moved to.
                Map<String, JsonNode> preprocessedFormulaMap = preprocessFormulaMap(dataRequestExcelGeneratorContext.getFormulaMap());
                // process the formula map
                Map<String, String> resultMap = processFormulaMap(preprocessedFormulaMap);

                // insert the result map back into the excel
                insertResultMapIntoExcel(resultMap);

                // get the upload URL of the portco portal (not sure which service will provide this)
                
                // Insert hidden tab in the excel and add required content to it
                
                return new byte[0];
	}

    protected Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap)
	{
		return formulaMap;
	}

}
