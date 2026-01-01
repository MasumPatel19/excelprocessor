package com.excelprocessor.controller;

import com.excelprocessor.service.ExcelService;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExcelController{

	private static final Logger logger = LogManager.getLogger(ExcelController.class);

	@Autowired
	private ExcelService excelService;

	/**
	 * Returns preview version of data request excel file
	 * @param campaignExcelPreviewDTO DTO containing campaign preview data
	 * @return Byte array of the preview version of the data request excel file
	 */
	@PostMapping("request/excel/generate/preview")
	public ResponseEntity<byte[]> generatePreviewExcel(@Valid @RequestBody CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
		logger.info("generatePreviewExcel method invoked");
		// generateDataRequestExcel is an overloaded method. with CampaignExcelPreviewDTO object as input
		// will create the preview version of the data request excel file
		byte[] result = excelService.generateDataRequestExcel(campaignExcelPreviewDTO);
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
	 * @param campaignID Campaign ID
	 * @param requestID Data Request ID
	 * @return Byte array of the data request excel file ready to be sent to the Portco
	 */
	@GetMapping("/request/excel/generate")
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

	/**
	 * Processes the data response excel file
	 * @param campaignID Campaign ID
	 * @param requestID Data Request ID
	 * @return String indicating the result of the data response excel processing
	 */
	@GetMapping("response/excel/process")
	public ResponseEntity<String> processDataResponseExcel(
			@RequestParam String campaignID,
			@RequestParam String requestID) {
		// TODO: Need to implement this method
		String result = "Data response excel processed successfully";
		return ResponseEntity.ok().body(result);
	}
}

