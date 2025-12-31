package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import java.util.Map;

public class PreviewExcelProcessor extends ExcelProcessorAbstract{

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
        
                return excel;;
	}

    protected Map<String, Formula> preprocessFormulaMap(Map<String, Formula> formulaMap)
	{
		// Perform ID lookups for get formulae

        // For GETT processing: We don't want each gett formula calling the campaign service. Also, we get campaign related data in the preview request. 
        // from request DTO, get the campaign data and add this data in JSON format in the formula map. This formula map will be supplied to the 
        // gett formula processor. This process will look up the formula map(campaign data) and return the value for the formula. 

        return formulaMap;
	}
}
