package com.excelprocessor.service;

import com.excelprocessor.service.ExcelService;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;
import com.excelprocessor.model.Campaign;
import com.excelprocessor.model.DataRequest;
import com.excelprocessor.model.Template;
import com.excelprocessor.processor.excel.ExcelProcesssorFactory;
import com.excelprocessor.service.ExcelProcessorContext;

@Service
public class ExcelServiceImpl implements ExcelService {

	private static final Logger logger = LogManager.getLogger(ExcelServiceImpl.class);
	
	/**
	 * This method will be called by the scheduler for a scheduled data request
	 * @param campaignID
	 * @param requestID
	 * @return
	 */
	public byte[] generateDataRequestExcel(String campaignID, String requestID)
	{
			// get campaign data using ID
			Campaign campaign = getCampaignFromId(campaignID);
			// get request data using ID
			DataRequest dataRequest = getDataRequestFromId(requestID);
			// get template id from campaign id
			Template template = getTemplateFromId(campaign.getTemplateId());
			Map<String, JsonNode> formulaMap = template.getFormulaMap();
			// create a ExcelProcessorContext
			ExcelProcessorContext excelProcessorContext = new ExcelProcessorContext(campaign, dataRequest, template, formulaMap);

			byte[] excel = new ExcelProcesssorFactory().createProcessor("DATA_REQUEST").processExcel(excelProcessorContext);

			return excel;


	}


	/**
	 * This method will be called when the preview button is clicked
	 * @param campaign JsonNode that has data inputs from the frontend screen from where the campaign is being created
	 * @return
	 */
	@Override
	public byte[] generateDataRequestExcel(CampaignExcelPreviewDTO campaignExcelPreviewDTO)
	{
		// create a campaign domain object from the campaign JSON object
		Campaign campaignObj = new Campaign(campaignExcelPreviewDTO);

		// since this is preview, the data request has not bee created yet.
		DataRequest dataRequest = null;
		Template template = getTemplateFromId(campaignObj.getTemplateId());
		Map<String, JsonNode> formulaMap = template.getFormulaMap();
		// create a ExcelProcessorContext
		ExcelProcessorContext excelProcessorContext = new ExcelProcessorContext(campaignObj, dataRequest, template, formulaMap);

		excel = new ExcelProcesssorFactory().createProcessor("PREVIEW").processExcel(excelProcessorContext);

		return excel;
	}

	
	private Template getTemplateFromId(String templateId)
	{
		// make API call to template service and get details of the template
		// create Template domain object from the template details
		// and return the domain object
		return new Template();
	}

	private Campaign getCampaignFromId(String campaignId)
	{
		CampaignExcelPreviewDTO campaignExcelPreviewDTO = new CampaignExcelPreviewDTO();
		// make API call to campaign service and get details of the campaign
		// create Campaign DTO object from the campaign details
		// and create Campaign domain object from the DTO and return the domain object
		return new Campaign(campaignExcelPreviewDTO);
	}

	private DataRequest getDataRequestFromId(String requestId)
	{
		// make API call to campaign service and get details of the data request
		// create DataRequest domain object from the data request details
		// and return the domain object
		return new DataRequest();
	}
}


