package com.seventythreestrings.campaign.excelprocessor;

import java.util.Map;

import com.seventythreestrings.campaign.entity.Formula;
import com.seventythreestrings.campaign.entity.cellID;
import com.seventythreestrings.campaign.service.impl.ExcelProcessorContext;

public class DataRequestExcelProcessor extends ExcelProcessorAbstract {

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
        // get the formula map from the template
        Map<cellID, Formula> formulaMap = excelProcessorContext.getTemplate().getFormulaMap();
        // preprocess the formula map. This is where the logic that 
        // currently resides in the frontend will be moved to.
        Map<cellID, Formula> preprocessedFormulaMap = preprocessFormulaMap(formulaMap);
        // process the formula map
        Map<cellID, String> resultMap = processFormulaMap(formulaMap);

        // insert the result map back into the excel
        insertResultMapIntoExcel(resultMap);

        // get the upload URL of the portco portal (not sure which service will provide this)
        
        // Insert hidden tab in the excel and add required content to it
        
        return excel;
	}

    private Map<cellID, Formula> preprocessFormulaMap(Map<cellID, Formula> formulaMap)
	{
		// preprocess formula map in parallel
	}
}
