package com.excelprocessor.service;

import com.excelprocessor.model.Template;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;

public class DataRequestExcelPreviewGeneratorContext extends ExcelProcessorContext {


    private Template template;
    private Map<String, JsonNode> formulaMap;
    

    public DataRequestExcelPreviewGeneratorContext(CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
        super();

        // map DTO data to instance variables. Use methods provided by the parent class when needed.

        this.template = getTemplateFromId(campaignExcelPreviewDTO.getPlatformTemplateId());
		this.formulaMap = template.getFormulaMap();

        // GETT formulae need data from campaignexcelpreviewdto object. i.e the instance variables of this DTO object. Add this data to the formula map JSON.
    }

    public Map<String, JsonNode> getFormulaMap() {
        return formulaMap;
    }

    public Template getTemplate() {
        return template;
    }
}
