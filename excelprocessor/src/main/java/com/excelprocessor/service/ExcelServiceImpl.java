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

			// create a ExcelProcessorContext
			ExcelProcessorContext excelProcessorContext = new DataRequestExcelGeneratorContext(campaignID, requestID);

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
		
		// create a ExcelProcessorContext
		ExcelProcessorContext excelProcessorContext = new DataRequestExcelPreviewGeneratorContext(campaignExcelPreviewDTO);

		byte[] excel = new ExcelProcesssorFactory().createProcessor("PREVIEW").processExcel(excelProcessorContext);

		return excel;
	}

	

}


