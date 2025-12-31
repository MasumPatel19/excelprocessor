package com.seventythreestrings.campaign.controller;

import com.seventythreestrings.campaign.dto.CampaignAppDTO;
import com.seventythreestrings.campaign.entity.ApiResponse;
import com.seventythreestrings.campaign.service.ExcelService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ExcelController extends BaseCampaignControler {

	private static final Logger logger = LogManager.getLogger(ExcelController.class);

	@Autowired
	private ExcelService excelService;

	/**
	 * Preview endpoint for Excel data
	 * Returns preview/metadata information about the Excel export
	 * 
	 * @param request HTTP request containing headers for context
	 * @return ApiResponse with preview data
	 */
	@GetMapping("/excel/preview")
	public ApiResponse<Map<String, Object>> generatePreviewExcel(HttpServletRequest request) {
		
		// call generateDataRequestExcel method from excel service with preview flag true
		return excelService.generateDataRequestExcel(new CampaignExcelPreviewDTO(request));
	}


	/**
	 * Excel download endpoint
	 * Returns Excel file for download
	 * 
	 * @param request HTTP request containing headers for context
	 * @return ResponseEntity with Excel file bytes
	 */
	@GetMapping("/excel/generate")
	public ResponseEntity<byte[]> generateDataRequestExcel(HttpServletRequest request) {
		// assuming that the request param has campaign details and request id and template id
		// call generateExcel method from excel service
		return excelService.generateDataRequestExcel(request.campaignID, request.requestID);
	}
}

