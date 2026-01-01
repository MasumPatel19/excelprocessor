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
	 * Returns preview version of data request excel file
	 * @param request HTTP request containing headers for context
	 * @return Byte array of the preview version of the data request excel file
	 */
	@GetMapping("/excel/preview")
	public ResponseEntity<byte[]> generatePreviewExcel(HttpServletRequest request) {
		
		// generateDataRequestExcel is an overloaded method. with CampaignExcelPreviewDTO object as input
		// will create the preview version of the data request excel file
		byte[] result = excelService.generateDataRequestExcel(new CampaignExcelPreviewDTO(request));
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"preview.xlsx\"")
				.body(result);
	}


	/**
	 * Returns data request excel file, ready to be sent to the Portco
	 * 
	 * This will be called once after creation of the campaign and data request.
	 * Or this may be called by the scheduler when it is time to send a data request to the portco
	 * as part of a campaign with reoccuring schedule
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

