package com.seventythreestrings.campaign.excelprocessor;

import com.seventythreestrings.campaign.service.impl.ExcelProcessorContext;

public class DataResponseExcelProcessor extends ExcelProcessorAbstract {

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
		
        Map<cellID, Formula> formulaMap = excelProcessorContext.getTemplate().getFormulaMap();

        // download the excel submited by the portco via email or portco portal upload

        // run validation on the excel

        Map<cellID, Formula> preprocessedFormulaMap = preprocessFormulaMap(formulaMap);
        // process the formula map
        Map<cellID, String> resultMap = processFormulaMap(formulaMap);

	}

    private Map<cellID, Formula> preprocessFormulaMap(Map<cellID, Formula> formulaMap)
	{
		
        // remove all the other formulae which don't need to be processed at this stage (e.g get etc)
        
        // replace all pushT formulae in the formula map with underlying push formulae and value 
        // that need to be pushed
	}
}
