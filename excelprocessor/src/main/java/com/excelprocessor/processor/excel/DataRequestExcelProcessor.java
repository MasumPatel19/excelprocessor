package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;

public class DataRequestExcelProcessor extends ExcelProcessorAbstract {

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
        // get the formula map from the template
        Map<String, Formula> formulaMap = excelProcessorContext.getTemplate().getFormulaMap();
        // preprocess the formula map. This is where the logic that 
        // currently resides in the frontend will be moved to.
        Map<String, Formula> preprocessedFormulaMap = preprocessFormulaMap(formulaMap);
        // process the formula map
        Map<String, String> resultMap = processFormulaMap(formulaMap);

        // insert the result map back into the excel
        insertResultMapIntoExcel(resultMap);

        // get the upload URL of the portco portal (not sure which service will provide this)
        
        // Insert hidden tab in the excel and add required content to it
        
        return excel;
	}

}
