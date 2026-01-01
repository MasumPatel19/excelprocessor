package com.excelprocessor.service;

import com.excelprocessor.model.Template;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;

public class DataRequestExcelPreviewGeneratorContext extends ExcelProcessorContext {


    private Map<String, JsonNode> formulaMap;
    

    public DataRequestExcelPreviewGeneratorContext(CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
        super();

        // map DTO data to instance variables. Use methods provided by the parent class when needed.

        Template template = getTemplateFromId(campaignExcelPreviewDTO.getPlatformTemplateId());
		this.formulaMap = template.getFormulaMap();

        // GETT formulae need data from campaignexcelpreviewdto object. i.e the instance variables of this DTO object. Add this data to the formula map JSON.
    }

    public Map<String, JsonNode> getFormulaMap() {

        
        
        return formulaMap;
    }

}
