package com.excelprocessor.service;

import com.excelprocessor.model.Campaign;
import com.excelprocessor.model.DataRequest;
import com.excelprocessor.model.Template;

public abstract class  ExcelProcessorContext {

	protected Template getTemplateFromId(String templateId)
	{
		// make API call to template service and get details of the template
		// create Template domain object from the template details
		// and return the domain object
		return new Template();
	}

	protected Campaign getCampaignFromId(String campaignId)
	{
		
		// make API call to campaign service and get details of the campaign
		// create Campaign DTO object from the campaign details
		// and create Campaign domain object from the DTO and return the domain object
		return new Campaign();
	}

	protected DataRequest getDataRequestFromId(String requestId)
	{
		// make API call to campaign service and get details of the data request
		// create DataRequest domain object from the data request details
		// and return the domain object
		return new DataRequest();
	}	
}
