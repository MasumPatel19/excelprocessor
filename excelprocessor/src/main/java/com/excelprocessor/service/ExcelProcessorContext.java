package com.excelprocessor.service;

import com.excelprocessor.model.Campaign;
import com.excelprocessor.model.DataRequest;
import com.excelprocessor.model.Template;

public abstract class  ExcelProcessorContext {

	protected Template getTemplateFromId(String templateId)
	{
		// make API call to template service and get details of the template
		//	TODO: Step 1 - Make HTTP GET call to Template Service(Monitoring Service) :
		//	TODO: Step 1.1 -  GET /api/v1/template-v3/{templateId}
		//	from that above api takes orgid, name [filename]
		//	TODO: Step 1.2 - GET /api/v1/campaign-mgmt-support/organizations/{orgID}/templates/{templateID}?filename=""
		// create Template domain object from the template details
		//	TODO: Step 2 - Parse JSON response to extract formulaMap structure
		//	TODO: Step 3 - Convert formulaMap JSON to Map<String, JsonNode>
		// and return the domain object
		//	TODO: Step 4 - Create Template domain object with formulaMap
		//	TODO: Step 5 - Handle API errors with proper exception handling and logs
		//	TODO: Step 6 - Return populated Template object with actual formulaMap

		// TODO: Hard-coding this for initial test purposes. 
		// TODO: Returning empty template for initial test purposes. 
		// TODO:Will be replaced with actual data from the template service. 

		return new Template();
	}

	protected Campaign getCampaignFromId(String campaignId)
	{
		
		// make API call to campaign service and get details of the campaign
		//	TODO: Step 1 - Create RestTemplate bean for HTTP calls
		//	TODO: Step 2 - Configure Campaign Service base URL in application.properties
		//	TODO: Step 3 - Make HTTP GET call to Campaign Service: GET /api/v1/campaignrequest/{campaignId}
		// create Campaign DTO object from the campaign details
		//	TODO: Step 4 - Parse JSON response into CampaignDTO or Map
		// and create Campaign domain object from the DTO and return the domain object
		//	TODO: Step 5 - Map CampaignDTO fields to Campaign domain object
		//	TODO: Step 6 - Handle API errors (404, 500, timeout) with proper exception handling and logs
		//	TODO: Step 7 - Return populated Campaign object

		return new Campaign();
	}

	protected DataRequest getDataRequestFromId(String requestId)
	{
		// make API call to campaign service and get details of the data request
		//	TODO: Step 1 - Make HTTP GET call to Campaign Service: GET /data-requests/{requestId}
		// create DataRequest domain object from the data request details
		//	TODO: Step 2 - Parse JSON response into DataRequestDTO or Map
		// and return the domain object
		//	TODO: Step 3 - Map DataRequestDTO fields to DataRequest domain object
		//	TODO: Step 4 - Handle API errors with proper exception handling and logs
		//	TODO: Step 5 - Return populated DataRequest object

		return new DataRequest();
	}	
}
