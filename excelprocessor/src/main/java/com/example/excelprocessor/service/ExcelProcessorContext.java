package com.seventythreestrings.campaign.service.impl;

import com.seventythreestrings.campaign.entity.Campaign;
import com.seventythreestrings.campaign.entity.DataRequest;
import com.seventythreestrings.campaign.entity.Template;
import com.seventythreestrings.campaign.entity.cellID;

import net.minidev.json.JSONObject;

import java.util.Map;

public class ExcelProcessorContext {

	// fields to hold the campaign metadata
	private Campaign campaign;
	// fields required to hold request data
	private DataRequest dataRequest;
	// template id
	private Template template;
	// formula map
	private Map<cellID, JSONObject> formulaMap;


	public ExcelProcessorContext(Campaign campaign, DataRequest dataRequest, Template template, Map<cellID, JSONObject> formulaMap) {
		this.campaign = campaign;
		this.dataRequest = dataRequest;
		this.template = template;
		this.formulaMap = formulaMap;
	}

	
}
