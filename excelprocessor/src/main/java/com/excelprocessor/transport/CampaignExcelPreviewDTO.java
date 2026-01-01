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
    
    private String entity;
    private String businessUnit;
    private String campaignTag;
    private String modeOfCollection;
    private String documents;
    private String platformTemplateId;
    private String version;
    private String periodType;
    private LocalDate reportingDate;
    private String periodRangeDate;
    private Integer periodRangeNumber;
    private String orgId;
    
    
    public CampaignExcelPreviewDTO() {
        // Default constructor for JSON deserialization
    }
    
    public CampaignExcelPreviewDTO(HttpServletRequest request) {
        // extract the data from the request body JSON and store in the instance variables
    }
    
    // Setters for JSON deserialization
    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }
    
    public void setCampaignTag(String campaignTag) {
        this.campaignTag = campaignTag;
    }
    
    public void setModeOfCollection(String modeOfCollection) {
        this.modeOfCollection = modeOfCollection;
    }
    
    public void setDocuments(String documents) {
        this.documents = documents;
    }
    
    public void setPlatformTemplateId(String platformTemplateId) {
        this.platformTemplateId = platformTemplateId;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }
    
    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }
    
    public void setPeriodRangeDate(String periodRangeDate) {
        this.periodRangeDate = periodRangeDate;
    }
    
    public void setPeriodRangeNumber(Integer periodRangeNumber) {
        this.periodRangeNumber = periodRangeNumber;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
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
