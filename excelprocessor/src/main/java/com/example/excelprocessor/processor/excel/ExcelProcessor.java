package com.seventythreestrings.campaign.excelprocessor;

import com.seventythreestrings.campaign.service.impl.ExcelProcessorContext;

public interface ExcelProcessor {
    byte[] processExcel(ExcelProcessorContext excelProcessorContext);
}
