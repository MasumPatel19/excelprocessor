package com.excelprocessor.transport;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;

/**
 * DTO is used to collect data from the request. 
 * It will be used as input to the excel processor that creates the preview version of the data request excel file.
 * 
 * The preview request may not have campaign id or request id. That is the reason 
 * why this DTO is separate from the Campaign and DataRequest domain objects.
 * Data from this DTO is the input to the excel processor that creates the preview version of the data request excel file.
 * 
 * TODO: Make this class immutable by making the instance variables final.
 * TODO: add getters for the instance variables.
 * 
 */
public class CampaignExcelPreviewDTO {
    
    @NotBlank(message = "entity is required")
    private String entity;
    @NotBlank(message = "businessUnit is required")
    private String businessUnit;
    @NotBlank(message = "campaignTag is required")
    private String campaignTag;
    @NotBlank(message = "modeOfCollection is required")
    private String modeOfCollection;
    @NotBlank(message = "documents is required")
    private String documents;
    @NotBlank(message = "platformTemplateId is required")
    private String platformTemplateId;
    @NotBlank(message = "version is required")
    private String version;
    @NotBlank(message = "periodType is required")
    private String periodType;
    @NotNull(message = "reportingDate is required")
    private LocalDate reportingDate;
    @NotBlank(message = "periodRangeDate is required")
    private String periodRangeDate;
    @NotBlank(message = "periodRangeNumber is required")
    @Min(value = 1, message = "periodRangeNumber must be greater than 0")
    private Integer periodRangeNumber;
    @NotBlank(message = "orgId is required")
    private String orgId;
    
    
    public CampaignExcelPreviewDTO(HttpServletRequest request) {
        // extract the data from the request body JSON and store in the instance variables
    }

    public String getEntity() {
        return entity;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public String getCampaignTag() {
        return campaignTag;
    }

    public String getModeOfCollection() {
        return modeOfCollection;
    }

    public String getDocuments() {
        return documents;
    }

    public String getPlatformTemplateId() {
        return platformTemplateId;
    }

    public String getVersion() {
        return version;
    }

    public String getPeriodType() {
        return periodType;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public String getPeriodRangeDate() {
        return periodRangeDate;
    }

    public Integer getPeriodRangeNumber() {
        return periodRangeNumber;
    }

    public String getOrgId() {
        return orgId;
    }
   
}
