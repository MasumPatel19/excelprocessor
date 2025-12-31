package com.excelprocessor.service;

import com.excelprocessor.model.Campaign;
import com.excelprocessor.model.DataRequest;
import com.excelprocessor.model.Template;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

public class ExcelProcessorContext {

	// fields to hold the campaign metadata
	private Campaign campaign;
	// fields required to hold request data
	private DataRequest dataRequest;
	// template id
	private Template template;
	// formula map
	private Map<String, JsonNode> formulaMap;


	public ExcelProcessorContext(Campaign campaign, DataRequest dataRequest, Template template, Map<String, JsonNode> formulaMap) {
		this.campaign = campaign;
		this.dataRequest = dataRequest;
		this.template = template;
		this.formulaMap = formulaMap;
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
