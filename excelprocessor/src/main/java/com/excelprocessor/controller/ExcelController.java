package com.excelprocessor.controller;

import com.excelprocessor.service.ExcelService;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;
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
public class ExcelController{

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
	public ResponseEntity<byte[]> generatePreviewExcel(HttpServletRequest request) {
		
		// call generateDataRequestExcel method from excel service with preview flag true
		byte[] result = excelService.generateDataRequestExcel(new CampaignExcelPreviewDTO(request));
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"preview.xlsx\"")
				.body(result);
	}


	/**
	 * Excel download endpoint
	 * Returns Excel file for download
	 * 
	 * @param request HTTP request containing headers for context
	 * @return ResponseEntity with Excel file bytes
	 */
	@GetMapping("/excel/generate")
	public ResponseEntity<byte[]> generateDataRequestExcel(
			@RequestParam String campaignID,
			@RequestParam String requestID) {
		// assuming that the request param has campaign details and request id and template id
		// call generateExcel method from excel service
		byte[] result = excelService.generateDataRequestExcel(campaignID, requestID);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"excel.xlsx\"")
				.body(result);
	}
}

