package com.excelprocessor.service;

import com.excelprocessor.model.Campaign;
import com.excelprocessor.model.DataRequest;
import com.excelprocessor.model.Template;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

public class DataRequestExcelGeneratorContext extends ExcelProcessorContext {

    private Campaign campaign;
    private DataRequest dataRequest;
    private Template template;
    private Map<String, JsonNode> formulaMap;

    public DataRequestExcelGeneratorContext(String campaignID, String requestID) {
    		// get campaign data using ID
			campaign = getCampaignFromId(campaignID);
			// get request data using ID
			dataRequest = getDataRequestFromId(requestID);
			// get template id from campaign id
			template = getTemplateFromId(campaign.getTemplateId());
			formulaMap = template.getFormulaMap();
    }

    public Map<String, JsonNode> getFormulaMap() {
        return formulaMap;
    }

    public Template getTemplate() {
        return template;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public DataRequest getDataRequest() {
        return dataRequest;
    }
}
