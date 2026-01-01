package com.excelprocessor.model;

import com.excelprocessor.transport.CampaignExcelPreviewDTO;

/**
 * Domain object representing a Campaign.
 */
public class Campaign {

    private String templateId;

    // temporary constructor for testing. This should be replaced by
    // constructor that takes a json object coming as a response from the campaign service
    // and builds the domain object from the json object.
    public Campaign() {
        this.templateId = null;
    }

    public Campaign(CampaignExcelPreviewDTO campaignExcelPreviewDTO) {
        
        // build this domain object from in coming campaignExcelPreviewDTO
    }


    public String getTemplateId() {
        return templateId;
    }
}

