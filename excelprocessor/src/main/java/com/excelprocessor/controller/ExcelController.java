package com.excelprocessor.controller;

import com.excelprocessor.service.ExcelService;
import com.excelprocessor.transport.CampaignExcelPreviewDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.excelprocessor.service.OdsAnalysisService;
import java.util.Map;
import java.io.IOException;

@RestController
@Tag(name = "Excel Processing", description = "APIs for generating and processing Excel files")
public class ExcelController{

	private static final Logger logger = LogManager.getLogger(ExcelController.class);

	@Autowired
	private ExcelService excelService;

	@Autowired
	private OdsAnalysisService odsAnalysisService;

	/**
	 * Returns preview version of data request excel file
	 * @param campaignExcelPreviewDTO DTO containing campaign preview data
	 * @return Byte array of the preview version of the data request excel file
	 */
	@PostMapping("request/excel/generate/preview")
	@Operation(summary = "Generate preview Excel", description = "Generates a preview version of the data request Excel file")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Excel file generated successfully",
					content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "400", description = "Invalid input data")
	})
	public ResponseEntity<byte[]> generatePreviewExcel(
			@Parameter(description = "Campaign preview data", required = true)
			@Valid @RequestBody CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
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
	@Operation(summary = "Generate data request Excel", description = "Generates a data request Excel file ready to be sent to the Portco")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Excel file generated successfully",
					content = @Content(mediaType = "application/octet-stream")),
			@ApiResponse(responseCode = "400", description = "Invalid parameters")
	})
	public ResponseEntity<byte[]> generateDataRequestExcel(
			@Parameter(description = "Campaign ID", required = true)
			@RequestParam String campaignID,
			@Parameter(description = "Data Request ID", required = true)
			@RequestParam String requestID) {
		// assuming that the request param has campaign details and request id and template id
		// TODO:  Validates campaignID and requestID parameters
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
	@Operation(summary = "Process data response Excel", description = "Processes the data response Excel file")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Excel file processed successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid parameters")
	})
	public ResponseEntity<String> processDataResponseExcel(
			@Parameter(description = "Campaign ID", required = true)
			@RequestParam String campaignID,
			@Parameter(description = "Data Request ID", required = true)
			@RequestParam String requestID) {
		// TODO: Need to implement this method
		String result = "Data response excel processed successfully";
		return ResponseEntity.ok().body(result);
	}

	/**
	 * Analyzes an ODS file to count formula occurrences and writes analysis to JSON file
	 * @param file The ODS file to analyze
	 * @return Response containing analysis results and JSON file path
	 */
	@PostMapping(value = "/ods/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "Analyze ODS file", description = "Analyzes an ODS file to count formula occurrences and saves the analysis to a JSON file")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ODS file analyzed successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid file or file processing error")
	})
	public ResponseEntity<Map<String, Object>> analyzeOdsFile(
			@Parameter(description = "ODS file to analyze", required = true)
			@RequestParam("file") MultipartFile file) {
		logger.info("analyzeOdsFile method invoked for file: {}", file.getOriginalFilename());

		try {
			// Validate file
			if (file.isEmpty()) {
				return ResponseEntity.badRequest()
						.body(Map.of("error", "File is empty"));
			}

			if (!file.getOriginalFilename().toLowerCase().endsWith(".ods")) {
				return ResponseEntity.badRequest()
						.body(Map.of("error", "File must be an ODS file (.ods extension)"));
			}

			// Analyze the ODS file
			Map<String, Object> analysisResult = odsAnalysisService.analyzeOdsFile(
					file.getInputStream(), 
					file.getOriginalFilename()
			);

			// Write analysis to JSON file
			String jsonFilePath = odsAnalysisService.writeAnalysisToJson(analysisResult);

			// Add JSON file path to response
			analysisResult.put("jsonFilePath", jsonFilePath);
			analysisResult.put("status", "success");

			return ResponseEntity.ok(analysisResult);

		} catch (IOException e) {
			logger.error("Error processing ODS file: {}", e.getMessage(), e);
			return ResponseEntity.badRequest()
					.body(Map.of("error", "Failed to process ODS file: " + e.getMessage()));
		} catch (Exception e) {
			logger.error("Unexpected error analyzing ODS file: {}", e.getMessage(), e);
			return ResponseEntity.badRequest()
					.body(Map.of("error", "Unexpected error: " + e.getMessage()));
		}
	}
}

