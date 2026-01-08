package com.excelprocessor.service;

import com.excelprocessor.model.ApiDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PreprocessingService {

    private static final Logger logger = LogManager.getLogger(PreprocessingService.class);

    private final RestTemplate restTemplate;

    @Value("${pluto.service.base.url:http://localhost:8080/api/v1}")
    private String plutoServiceBaseUrl;

    @Value("${campaign.service.base.url:http://localhost:8088/api/v3}")
    private String campaignServiceBaseUrl;

    @Value("${monitoring.service.base.url:http://localhost:8815/api/v1}")
    private String monitoringServiceBaseUrl;

    public PreprocessingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void executeApi(ApiDefinition api,
                            Map<String, Object> context,
                            Map<String, String> headers,
                            Map<String, String> errors) {

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

            // Common headers
            httpHeaders.set("x-auth-token", headers.get("x-auth-token"));

            // Pluto-specific header
            if (api.getServiceType() == ApiDefinition.ServiceType.PLUTO) {
                httpHeaders.set("x-user-id", headers.get("x-user-id"));
            }
            // Campaign-specific header
            if (api.getServiceType() == ApiDefinition.ServiceType.CAMPAIGN) {
                httpHeaders.set("x-campaign-id", headers.get("x-campaign-id"));
            }

            Object requestBody = api.getRequestBodyBuilder() != null
                    ? api.getRequestBodyBuilder().apply(context)
                    : null;

            HttpEntity<Object> entity = new HttpEntity<>(requestBody, httpHeaders);

            String baseUrl;
            if (api.getServiceType() == ApiDefinition.ServiceType.CAMPAIGN) {
                baseUrl = campaignServiceBaseUrl;
            } else if (api.getServiceType() == ApiDefinition.ServiceType.MONITORING) {
                baseUrl = monitoringServiceBaseUrl;
            } else {
                baseUrl = plutoServiceBaseUrl;
            }


            String endpoint = api.getEndpoint();

            if (api.getPathParamsBuilder() != null) {
                Map<String, String> params = api.getPathParamsBuilder().apply(context);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    endpoint = endpoint.replace("{" + entry.getKey() + "}", entry.getValue());
                }
            }

            if (requestBody instanceof Map<?, ?> map && map.containsKey("input")) {
                List<?> input = (List<?>) map.get("input");
                logger.info("getAttributeDetails input size = {}", input.size());
            }

            String fullUrl = baseUrl + endpoint;
            logger.info("Calling API: {} with full URL: {}", api.getName(), fullUrl);

            ResponseEntity<Object> response = restTemplate.exchange(
                    fullUrl,
                    api.getMethod(),
                    entity,
                    Object.class
            );

            context.put(api.getName(), response.getBody());

        } catch (Exception e) {
            errors.put(api.getName(), e.getMessage());
        }
    }

    private List<ApiDefinition> phase1Apis() {

        return List.of(
                new ApiDefinition(
                        "getEmsEnabledStatus",
                        "/users/getEmsEnabledStatus",
                        HttpMethod.POST,
                        null,
                        null,
                        ApiDefinition.ServiceType.PLUTO
                ),
                new ApiDefinition(
                        "getVersionList",
                        "/monitoring/getVersionList",
                        HttpMethod.POST,
                        null,
                        null,
                        ApiDefinition.ServiceType.PLUTO
                ),
                new ApiDefinition(
                        "getAttributeList",
                        "/attributes/getAttributeList",
                        HttpMethod.POST,
                        null,
                        null,
                        ApiDefinition.ServiceType.PLUTO
                ),
                new ApiDefinition(
                        "getQualitativeAnalysisTags",
                        "/monitoring/getQualitativeAnalysisTags",
                        HttpMethod.POST,
                        null,
                        null,
                        ApiDefinition.ServiceType.PLUTO
                ),
                new ApiDefinition(
                        "getFundCompanyEntities",
                        "/users/getFundCompanyEntities",
                        HttpMethod.POST,
                        null,
                        null,
                        ApiDefinition.ServiceType.PLUTO
                )
        );
    }

    @SuppressWarnings("unchecked")
    private <T> T extractResponse(Map<String, Object> context, String apiName) {

        Object wrapper = context.get(apiName);

        if (wrapper == null) {
            return null;
        }

        if (!(wrapper instanceof Map)) {
            throw new IllegalStateException(apiName + " response is not a Map");
        }

        Map<String, Object> responseWrapper = (Map<String, Object>) wrapper;
        return (T) responseWrapper.get("response");
    }

    private ApiDefinition getAttributeDetailsApi() {

        return new ApiDefinition(
                "getAttributeDetails",
                "/attributes/list/getAttributeDetails",
                HttpMethod.POST,
                context -> {

//                    List<Map<String, Object>> funds =
//                            extractResponse(context, "getFundCompanyEntities");
//
//                    List<String> companyIds = funds.stream()
//                            .filter(Objects::nonNull)
//                            .flatMap(fund -> {
//                                List<Map<String, Object>> companies =
//                                        (List<Map<String, Object>>) fund.get("companies");
//                                return companies == null ? Stream.empty() : companies.stream();
//                            })
//                            .map(company -> (String) company.get("id"))
//                            .filter(StringUtils::hasText)
//                            .toList();

                    Map<String, Object> attributeList =
                            extractResponse(context, "getAttributeList");

                    List<String> categories = attributeList.keySet().stream()
                            .map(key -> switch (key.toLowerCase()) {
                                case "financials" -> "FINANCIAL";
                                case "balance sheet" -> "BALANCE_SHEET";
                                case "cash flow" -> "CASH_FLOW";
                                case "covenant" -> "CONVENANT";
                                case "general details" -> "GENERAL_DETAILS";
                                case "custom general details" -> "CUSTOM_GENERAL_DETAILS";
                                case "income statement" -> "INCOME_STATEMENT";
                                case "all" -> "ALL";
                                case "esg" -> "ESG";
                                case "kpi" -> "KPI";
                                case "valuation" -> "VALUATION";
                                case "ratio analysis" -> "RATIO_ANALYSIS";
                                default -> null;
                            })
                            .filter(Objects::nonNull)
                            .toList();

                    List<Map<String, Object>> input = new ArrayList<>();

//                    for (String companyId : companyIds) {
                    for (String category : categories) {
                        input.add(Map.of(
                                "companyId", "",
                                "category", category,
                                "cellLocation", "A1",
                                "workSheet", "Sheet1"
                        ));
                    }
//                    }

                    logger.info("Input for getAttributeDetailsApi size={}", input.size());

                    return Map.of("input", input);
                },
                null,
                ApiDefinition.ServiceType.PLUTO
        );
    }

    private ApiDefinition getEntitySegmentsApi() {

        return new ApiDefinition(
                "getEntitySegments",
                "/monitoring/getEntitySegments",
                HttpMethod.POST,
                context -> {

                    Object fundCompanyResponse = context.get("getFundCompanyEntities");

                    Map<String, Object> fundCompanyMap =
                            (Map<String, Object>) fundCompanyResponse;

                    List<Map<String, Object>> funds =
                            (List<Map<String, Object>>) fundCompanyMap.get("response");

                    List<String> companyIds = funds.stream()
                            .map(fund -> (List<Map<String, Object>>) fund.get("companies"))
                            .filter(Objects::nonNull)
                            .flatMap(List::stream)
                            .map(company -> (String) company.get("id"))
                            .filter(Objects::nonNull)
                            .toList();


                    return Map.of("companyIds", companyIds);
                },
                null,
                ApiDefinition.ServiceType.PLUTO
        );
    }

    private ApiDefinition getExtractedSegmentsApi() {

        return new ApiDefinition(
                "getExtractedSegments",
                "/attributes/getExtractedSegments",
                HttpMethod.POST,
                context -> {

                    List<Map<String, Object>> payload = new ArrayList<>();

//                    for (Map<String, Object> fc : fundCompanies) {
//                        for (Map<String, Object> attr : generalDetails) {
                    payload.add(Map.of(
                            "companyId", "",
                            "attributeId", ""
                    ));
//                        }
//                    }
                    return payload;
                },
                null,
                ApiDefinition.ServiceType.PLUTO
        );
    }

    /**
     * API definition for fetching campaign request details from campaign service.
     * This API is conditionally executed only when campaign ID is available in headers.
     * Used by scheduler flow, not available in preview flow.
     */
    private ApiDefinition getCampaignRequestDetailsApi() {
        return new ApiDefinition(
                "getCampaignRequestDetails",
                "/campaign-requests/{campaignId}",
                HttpMethod.GET,
                null,
                context -> {
                    String campaignId = (String) context.get("campaignId");
                    if (campaignId == null || campaignId.isEmpty()) {
                        logger.warn("Campaign ID not found in context for getCampaignRequestDetails API");
                        return Map.of();
                    }
                    return Map.of("campaignId", campaignId);
                },
                ApiDefinition.ServiceType.CAMPAIGN
        );
    }

    /**
     * API definition for fetching template details from monitoring service.
     * This API is conditionally executed only when template ID is available.
     * Template ID is extracted from campaign request details response or from headers.
     * Used by scheduler flow after campaign details are fetched.
     */
    private ApiDefinition getTemplateByIdApi() {
        return new ApiDefinition(
                "getTemplateById",
                "/template-v3/{templateId}",
                HttpMethod.GET,
                null, // No request body for GET request
                context -> {
                    String templateId = extractResponse(context, "getCampaignRequestDetails");

                    if (templateId == null || templateId.isEmpty()) {
                        templateId = (String) context.get("templateId");
                    }

                    if (templateId == null || templateId.isEmpty()) {
                        logger.warn("Template ID not found in context for getTemplateById API");
                        return Map.of();
                    }

                    return Map.of("templateId", templateId);
                },
                ApiDefinition.ServiceType.MONITORING
        );
    }


    public Map<String, Object> collectPreprocessingData(Map<String, String> headers) {

        Map<String, Object> context = new HashMap<>();
        Map<String, String> errors = new HashMap<>();

        String campaignId = headers.get("x-campaign-id");
        if (campaignId != null && !campaignId.isEmpty()) {
            context.put("campaignId", campaignId);
        }

        String templateId = headers.get("x-template-id");
        if (templateId != null && !templateId.isEmpty()) {
            context.put("templateId", templateId);
        }

        // Phase 1
        for (ApiDefinition api : phase1Apis()) {
            logger.info("Executing API: {} with endpoint: {} and service type: {}", api.getName(), api.getEndpoint(), api.getServiceType());
            executeApi(api, context, headers, errors);
        }

        // Phase 2
        executeApi(getAttributeDetailsApi(), context, headers, errors);
        executeApi(getEntitySegmentsApi(), context, headers, errors);
        executeApi(getExtractedSegmentsApi(), context, headers, errors);

        // Phase 3: Campaign-specific APIs (only when campaign ID is available)
        if (campaignId != null && !campaignId.isEmpty()) {
            logger.info("Campaign ID found: {}. Executing campaign-specific APIs.", campaignId);
            executeApi(getCampaignRequestDetailsApi(), context, headers, errors);
        } else {
            logger.info("No campaign ID found in headers. Skipping campaign-specific APIs (preview mode).");
        }

        if (templateId != null && !templateId.isEmpty()) {
            logger.info("Template ID found: {}. Executing monitoring-specific APIs.", templateId);
            executeApi(getTemplateByIdApi(), context, headers, errors);
        } else {
            logger.info("No template ID found. Skipping monitoring-specific APIs.");
        }

        if (!errors.isEmpty()) {
            context.put("errors", errors);
        }

        return context;
    }
}