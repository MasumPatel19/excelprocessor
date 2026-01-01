package com.excelprocessor.processor.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.excelprocessor.service.DataRequestExcelPreviewGeneratorContext;

public class PreviewExcelProcessor extends ExcelProcessorAbstract{

	private static final Logger logger = LogManager.getLogger(PreviewExcelProcessor.class);

    DataRequestExcelPreviewGeneratorContext dataRequestExcelPreviewGeneratorContext;
    
	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
		logger.info("processExcel method invoked");

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
