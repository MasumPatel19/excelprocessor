package com.excelprocessor.controller;

import com.excelprocessor.service.ExcelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExcelController.class)
class ExcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExcelService excelService;

    @Autowired
    private ObjectMapper objectMapper;

    private Map<String, Object> validRequestJson;

    @BeforeEach
    void setUp() {
        // Configure ObjectMapper to handle LocalDate
        objectMapper.registerModule(new JavaTimeModule());
        
        // Create valid request JSON that maps to CampaignExcelPreviewDTO fields
        validRequestJson = new HashMap<>();
        validRequestJson.put("entity", "TestEntity");
        validRequestJson.put("businessUnit", "TestBusinessUnit");
        validRequestJson.put("campaignTag", "TestCampaignTag");
        validRequestJson.put("modeOfCollection", "Email");
        validRequestJson.put("documents", "Document1,Document2");
        validRequestJson.put("platformTemplateId", "template-123");
        validRequestJson.put("version", "1.0");
        validRequestJson.put("periodType", "Monthly");
        validRequestJson.put("reportingDate", "2024-01-15");
        validRequestJson.put("periodRangeDate", "2024-01-01");
        validRequestJson.put("periodRangeNumber", 1);
        validRequestJson.put("orgId", "org-123");
    }

    @Test
    void testGeneratePreviewExcel_Success() throws Exception {
        // Mock the service response
        byte[] mockExcelBytes = new byte[]{1, 2, 3, 4, 5};
        when(excelService.generateDataRequestExcel(any())).thenReturn(mockExcelBytes);

        // Perform the request
        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestJson)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"preview.xlsx\""))
                .andExpect(content().bytes(mockExcelBytes));
    }

    @Test
    void testGeneratePreviewExcel_MissingRequiredField() throws Exception {
        // Remove a required field
        Map<String, Object> invalidRequest = new HashMap<>(validRequestJson);
        invalidRequest.remove("entity");

        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGeneratePreviewExcel_InvalidPeriodRangeNumber() throws Exception {
        // Set periodRangeNumber to invalid value (less than 1)
        Map<String, Object> invalidRequest = new HashMap<>(validRequestJson);
        invalidRequest.put("periodRangeNumber", 0);

        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGeneratePreviewExcel_MissingReportingDate() throws Exception {
        // Remove reportingDate (required field)
        Map<String, Object> invalidRequest = new HashMap<>(validRequestJson);
        invalidRequest.remove("reportingDate");

        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGeneratePreviewExcel_EmptyStringFields() throws Exception {
        // Set required string fields to empty strings
        Map<String, Object> invalidRequest = new HashMap<>(validRequestJson);
        invalidRequest.put("entity", "");
        invalidRequest.put("businessUnit", "");

        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGeneratePreviewExcel_ServiceException() throws Exception {
        // Mock service to throw an exception
        when(excelService.generateDataRequestExcel(any()))
                .thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/request/excel/generate/preview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequestJson)))
                .andExpect(status().isInternalServerError());
    }
}

