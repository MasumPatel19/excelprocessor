package com.excelprocessor.processor.excel;

import com.excelprocessor.service.ExcelProcessorContext;

public interface ExcelProcessor {
    byte[] processExcel(ExcelProcessorContext excelProcessorContext);
}
