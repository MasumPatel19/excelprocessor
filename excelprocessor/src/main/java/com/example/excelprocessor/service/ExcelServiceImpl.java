package com.seventythreestrings.campaign.service.impl;

import com.seventythreestrings.campaign.dto.CampaignAppDTO;
import com.seventythreestrings.campaign.entity.ApiResponse;
import com.seventythreestrings.campaign.service.ExcelService;

import net.minidev.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelServiceImpl implements ExcelService {

	private static final Logger logger = LogManager.getLogger(ExcelServiceImpl.class);
	
	/**
	 * This method will be called by the scheduler for a scheduled data request
	 * @param campaignID
	 * @param requestID
	 * @return
	 */
	public ApiResponse<Map<String, Object>> generateDataRequestExcel(campaignID, requestID)
	{
			// get campaign data using ID
			Campaign campaign = getCampaignFromId(campaignID);
			// get request data using ID
			DataRequest dataRequest = getDataRequestFromId(requestID);
			// get template id from campaign id
			Template template = getTemplateFromId(campaign.getTemplateId());
			Map<cellID, JSONObject> formulaMap = template.getFormulaMap();
			// create a ExcelProcessorContext
			ExcelProcessorContext excelProcessorContext = new ExcelProcessorContext(campaign, dataRequest, template, formulaMap);

			excel = new ExcelProcesssorFactory().createProcessor("DATA_REQUEST").processExcel(excelProcessorContext);

			return excel;


	}


	/**
	 * This method will be called when the preview button is clicked
	 * @param JSONObject that has data inputs from the frontend screen from where the campaign is being created
	 * @return
	 */
	@Override
	public ApiResponse<Map<String, Object>> generateDataRequestExcel(JSONObject campaign))
	{
		// create a campaign domain object from the campaign JSON object
		Campaign campaign = new Campaign(campaignJSON);

		// since this is preview, the data request has not bee created yet.
		DataRequest dataRequest = null;
		Template template = getTemplateFromId(campaign.getTemplateId());
		Map<cellID, JSONObject> formulaMap = template.getFormulaMap();
		// create a ExcelProcessorContext
		ExcelProcessorContext excelProcessorContext = new ExcelProcessorContext(campaign, dataRequest, template, formulaMap);

		excel = new ExcelProcesssorFactory().createProcessor("PREVIEW").processExcel(excelProcessorContext);

		return excel;
	}

	
	@Override
	private byte[] generateExcel(ExcelProcessorContext excelProcessorContext) {

	}

	


	private Template getTemplateFromId(String templateId)
	{
		return CampaignService.getTemplateFromId(templateId);
	}

	private Campaign getCampaignFromId(String campaignId)
	{
		return CampaignService.getCampaignFromId(campaignId);
	}

	private DataRequest getDataRequestFromId(String requestId)
	{
		return CampaignService.getDataRequestFromId(requestId);
	}
}


