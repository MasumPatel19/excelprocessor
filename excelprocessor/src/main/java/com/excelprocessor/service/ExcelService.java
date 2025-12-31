package com.excelprocessor.service;


import java.util.Map;

public interface ExcelService {

	/**
	 * Get preview data for Excel export
	 * 
	 * @param campaignAppDTO Campaign application context
	 * @return ApiResponse containing preview data
	 */
	ApiResponse<Map<String, Object>> getExcelPreview(CampaignAppDTO campaignAppDTO);

	/**
	 * Generate Excel file bytes
	 * 
	 * @param campaignAppDTO Campaign application context
	 * @return byte array representing the Excel file
	 */
	byte[] generateExcel(CampaignAppDTO campaignAppDTO);
}

