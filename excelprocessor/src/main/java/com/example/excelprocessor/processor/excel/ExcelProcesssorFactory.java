package com.seventythreestrings.campaign.excelprocessor;

import org.springframework.stereotype.Component;

/**
 * Factory class for creating ExcelProcessor instances.
 * Creates processor objects based on processor type string.
 */
@Component
public class ExcelProcesssorFactory {

	/**
	 * Creates an ExcelProcessor instance based on the processor type string.
	 * 
	 * @param processorType the processor type string (DATA_REQUEST, PREVIEW, or DATA_RESPONSE)
	 * @return ExcelProcessor instance, or null if processorType is invalid
	 */
	public ExcelProcessor createProcessor(String processorType) {
		if (processorType == null) {
			return null;
		}

		String type = processorType.trim().toUpperCase();

		switch (type) {
			case "DATA_REQUEST":
				return new DataRequestExcelProcessor();
			case "PREVIEW":
				return new PreviewExcelProcessor();
			case "DATA_RESPONSE":
				return new DataResponseExcelProcessor();
			default:
				return null;
		}
	}
}
