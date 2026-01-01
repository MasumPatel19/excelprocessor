package com.excelprocessor.transport;

import jakarta.servlet.http.HttpServletRequest;

public class CampaignExcelPreviewDTO {
    
    private HttpServletRequest request;
    
    public CampaignExcelPreviewDTO(HttpServletRequest request) {
        this.request = request;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }
}
