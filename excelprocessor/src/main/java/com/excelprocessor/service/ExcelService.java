package com.excelprocessor.service;

import com.excelprocessor.transport.CampaignExcelPreviewDTO;

public interface ExcelService {

	/**
	 * Get preview data for Excel export
	 * 
	 * @param campaignAppDTO Campaign application context
	 * @return ApiResponse containing preview data
	 */
	byte[] generateDataRequestExcel(CampaignExcelPreviewDTO campaignExcelPreviewDTO);

	public byte[] generateDataRequestExcel(String campaignID, String requestID);
}

