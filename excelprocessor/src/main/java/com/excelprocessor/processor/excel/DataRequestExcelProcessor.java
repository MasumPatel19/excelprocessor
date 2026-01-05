package com.excelprocessor.processor.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.excelprocessor.service.DataRequestExcelGeneratorContext;

public class DataRequestExcelProcessor extends ExcelProcessorAbstract {

	private static final Logger logger = LogManager.getLogger(DataRequestExcelProcessor.class);
	
        DataRequestExcelGeneratorContext dataRequestExcelGeneratorContext;

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
		logger.info("processExcel method invoked");

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

                //  TBD: Use Workbook
                //  Pass workbook instance to insertResultMapIntoExcel() method

                // insert the result map back into the excel
                insertResultMapIntoExcel(resultMap);

                // get the upload URL of the portco portal (not sure which service will provide this)
                //  TODO: Step 1 - Determine which service provides upload URL (Portco Portal Service?)
                //  TODO: Step 2 - Create HTTP client call to get upload URL endpoint
                //  TODO: Step 3 - Pass campaignID and requestID as parameters
                //  TODO: Step 4 - Parse response to extract upload URL
                //  TODO: Step 5 - Handle API Errors and fallback scenarios
                //  TODO: Step 6 - Store upload URL for hidden tab insertion

                // Insert hidden tab in the excel and add required content to it
                //  TODO: Step 1 - Create new sheet in workbook
                //  TODO: Step 2 - Set sheet name
                //  TODO: Step 3 - Hide the sheet
                //  TODO: Step 4 - Create cell structure for hidden tab content:
                //  TODO: Step 4.1 - Cell A1: Upload URL value
                //  TODO: Step 4.2 - Cell A2: Campaign ID
                //  TODO: Step 4.3 - Cell A3: Request ID
                //  TODO: Step 4.4 - Cell A4: Template ID
                //  TODO: Step 5 - Verify sheet is properly hidden

                return new byte[0];
	}

    protected Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap)
	{
        //  Call pluto service and preprocess the formulae
        //  TODO: Step 1 - Create processedFormulaMap - copy of formulaMap
        //  TODO: Step 2 - Iterate through all formulaMap entries and group by type
        //  TODO: Step 2.1 - Separate formulas by type (e.g. Map<String, JsonNode> getFormulas = new HashMap<>();)
        //  TODO: Step 2.2 - Iterate and according to the type (e.g.  getFormulas.put(cellRef, formulaNode);
        //  TODO: Step 3 - For each formula, Call Pluto service to preprocess
        //  TODO: Step 3.1 - For each GET formula, call Pluto
        //  TODO: Step 3.1.1 - Prepare request payload
        //  TODO: Step 3.1.2 - Call Pluto service
        //  TODO: Step 4 - Merge preprocessed formulas back into the processedFormulaMap
        //  TODO: Step 4.1 - Extract preprocessed formulas from Pluto response - create preprocessedGetFormulas and add in that
        //  TODO: Step 4.2 -  Merge back into processed formula map
        //  TODO: Step 5 - return processedFormulaMap
        return formulaMap;
	}

}
