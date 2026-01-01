package com.excelprocessor.processor.excel;

import com.excelprocessor.processor.formula.Formula;
import com.excelprocessor.service.ExcelProcessorContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.excelprocessor.service.DataResponseExcelProcessorContext;
import java.util.Map;

public class DataResponseExcelProcessor extends ExcelProcessorAbstract {


    DataResponseExcelProcessorContext dataResponseExcelProcessorContext;

	@Override
	public byte[] processExcel(ExcelProcessorContext excelProcessorContext) {
		
        if (excelProcessorContext instanceof DataResponseExcelProcessorContext) {
            dataResponseExcelProcessorContext = (DataResponseExcelProcessorContext) excelProcessorContext;
        } else {
            throw new IllegalArgumentException("ExcelProcessorContext must be of type DataResponseExcelProcessorContext");
        }

        // download the excel submited by the portco via email or portco portal upload

        // run validation on the excel

        Map<String, JsonNode> preprocessedFormulaMap = preprocessFormulaMap(dataResponseExcelProcessorContext.getFormulaMap());
        // process the formula map
        Map<String, String> resultMap = processFormulaMap(preprocessedFormulaMap);

        // what to return here? We possibly can return the same input excel as is. Ideally, for response processing 
        // We need to return something that reports errors during formula processing back to the calling method. 
        return new byte[0];
	}

    protected Map<String, JsonNode> preprocessFormulaMap(Map<String, JsonNode> formulaMap)
	{
		
        // remove all the other formulae which don't need to be processed at this stage (e.g get etc)
        
        // replace all pushT formulae in the formula map with underlying push formulae and value 
        // that need to be pushed
        return formulaMap;
	}
}
