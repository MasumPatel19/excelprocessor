package com.excelprocessor.processor.excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Factory class for creating ExcelProcessor instances.
 * Creates processor objects based on processor type string.
 */
@Component
public class ExcelProcesssorFactory {

	private static final Logger logger = LogManager.getLogger(ExcelProcesssorFactory.class);

	/**
	 * Creates an ExcelProcessor instance based on the processor type string.
	 * 
	 * @param processorType the processor type string (DATA_REQUEST, PREVIEW, or DATA_RESPONSE)
	 * @return ExcelProcessor instance, or null if processorType is invalid
	 */
	public ExcelProcessor createProcessor(String processorType) {
		logger.info("createProcessor method invoked");
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
