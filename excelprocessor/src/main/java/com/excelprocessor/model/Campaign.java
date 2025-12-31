package com.excelprocessor.model;

import com.excelprocessor.transport.CampaignExcelPreviewDTO;

/**
 * Domain object representing a Campaign.
 */
public class Campaign {

    private String templateId;

    public Campaign(CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
        
        // build this domain object from in coming campaignExcelPreviewDTO
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTemplateId() {
        return templateId;
    }
}

