package com.excelprocessor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OdsAnalysisService {

    private static final Logger logger = LogManager.getLogger(OdsAnalysisService.class);
    private static final String OUTPUT_DIR = "/home/dhaval/code/temp/excelprocessor/excelprocessor/src/main/resources";

    /**
     * Analyzes an ODS file and counts formula occurrences
     * @param inputStream InputStream of the ODS file
     * @param fileName Original filename
     * @return Map containing formula counts
     */
    public Map<String, Object> analyzeOdsFile(InputStream inputStream, String fileName) throws IOException {
        logger.info("analyzeOdsFile method invoked for file: {}", fileName);

        Map<String, Integer> formulaCounts = new HashMap<>();
        Map<String, Integer> customFormulaCounts = new HashMap<>();
        Map<String, JsonNode> formulaMap = new HashMap<>(); // Formula map similar to Template.java
        ObjectMapper objectMapper = new ObjectMapper();
        int totalFormulas = 0;
        int totalSheets = 0;

        try {
            // ODS files are ZIP archives, extract content.xml
            byte[] contentXml = extractContentXml(inputStream);
            
            if (contentXml == null) {
                throw new IOException("Could not extract content.xml from ODS file");
            }

            // Parse the XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(contentXml));

            // Find all table elements (sheets)
            NodeList tableNodes = document.getElementsByTagNameNS(
                    "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "table");

            totalSheets = tableNodes.getLength();

            // Process each sheet
            for (int i = 0; i < tableNodes.getLength(); i++) {
                Element tableElement = (Element) tableNodes.item(i);
                String sheetName = tableElement.getAttributeNS(
                        "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "name");
                if (sheetName == null || sheetName.isEmpty()) {
                    sheetName = "Sheet" + (i + 1);
                }

                // Process rows to track cell positions
                NodeList rowNodes = tableElement.getElementsByTagNameNS(
                        "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "table-row");
                processRowsForFormulas(rowNodes, formulaCounts, customFormulaCounts, formulaMap, 
                                      objectMapper, sheetName);
            }

            // Count total formulas
            totalFormulas = formulaCounts.values().stream().mapToInt(Integer::intValue).sum() +
                           customFormulaCounts.values().stream().mapToInt(Integer::intValue).sum();

        } catch (Exception e) {
            logger.error("Error analyzing ODS file: {}", e.getMessage(), e);
            throw new IOException("Failed to analyze ODS file: " + e.getMessage(), e);
        }

        // Build result map
        Map<String, Object> result = new HashMap<>();
        result.put("fileName", fileName);
        result.put("totalSheets", totalSheets);
        result.put("totalFormulas", totalFormulas);
        result.put("standardFormulas", formulaCounts);
        result.put("customFormulas", customFormulaCounts);
        result.put("formulaMap", formulaMap); // Add formulaMap similar to Template.java structure
        result.put("analysisTimestamp", System.currentTimeMillis());

        return result;
    }

    /**
     * Extracts content.xml from ODS file (which is a ZIP archive)
     */
    private byte[] extractContentXml(InputStream inputStream) throws IOException {
        try (ZipArchiveInputStream zipInput = new ZipArchiveInputStream(inputStream)) {
            ZipArchiveEntry entry;
            while ((entry = zipInput.getNextZipEntry()) != null) {
                if (entry.getName().equals("content.xml")) {
                    // Read all bytes from the entry
                    java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
                    byte[] data = new byte[8192];
                    int nRead;
                    while ((nRead = zipInput.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    return buffer.toByteArray();
                }
            }
        }
        return null;
    }

    /**
     * Processes rows to extract and count formulas, building formulaMap
     */
    private void processRowsForFormulas(NodeList rowNodes, Map<String, Integer> formulaCounts,
                                        Map<String, Integer> customFormulaCounts, 
                                        Map<String, JsonNode> formulaMap,
                                        ObjectMapper objectMapper, String sheetName) {
        int rowNum = 0;
        
        for (int rowIdx = 0; rowIdx < rowNodes.getLength(); rowIdx++) {
            Node rowNode = rowNodes.item(rowIdx);
            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rowElement = (Element) rowNode;
                
                // Get row repeat count (default 1)
                String rowRepeatStr = rowElement.getAttributeNS(
                        "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "number-rows-repeated");
                int rowRepeat = rowRepeatStr != null && !rowRepeatStr.isEmpty() 
                        ? Integer.parseInt(rowRepeatStr) : 1;
                
                NodeList cellNodes = rowElement.getElementsByTagNameNS(
                        "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "table-cell");
                
                int colNum = 0;
                for (int cellIdx = 0; cellIdx < cellNodes.getLength(); cellIdx++) {
                    Node cellNode = cellNodes.item(cellIdx);
                    if (cellNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element cellElement = (Element) cellNode;
                        
                        // Get column repeat count (default 1)
                        String colRepeatStr = cellElement.getAttributeNS(
                                "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "number-columns-repeated");
                        int colRepeat = colRepeatStr != null && !colRepeatStr.isEmpty() 
                                ? Integer.parseInt(colRepeatStr) : 1;
                        
                        // Process this cell (and repeated columns)
                        for (int repeat = 0; repeat < colRepeat; repeat++) {
                            String cellRef = getCellReference(rowNum, colNum);
                            
                            // Check for formula attribute
                            String formulaAttr = cellElement.getAttributeNS(
                                    "urn:oasis:names:tc:opendocument:xmlns:table:1.0", "formula");
                            if (formulaAttr != null && !formulaAttr.isEmpty()) {
                                processFormula(formulaAttr, formulaCounts, customFormulaCounts);
                                addToFormulaMap(cellRef, formulaAttr, formulaMap, objectMapper);
                            }
                            
                            // Also check text content for formulas (like Q.GETT, Q.PUSHT)
                            NodeList textNodes = cellElement.getElementsByTagNameNS(
                                    "urn:oasis:names:tc:opendocument:xmlns:text:1.0", "p");
                            for (int j = 0; j < textNodes.getLength(); j++) {
                                Node textNode = textNodes.item(j);
                                String textContent = getTextContent(textNode);
                                if (textContent != null && (textContent.contains("Q.GETT") || 
                                                             textContent.contains("Q.PUSHT") || 
                                                             textContent.contains("Q.GET") ||
                                                             textContent.contains("Q.PUSH"))) {
                                    processCustomFormula(textContent, customFormulaCounts);
                                    addToFormulaMap(cellRef, textContent, formulaMap, objectMapper);
                                }
                            }
                            
                            colNum++;
                        }
                    }
                }
                
                rowNum += rowRepeat;
            }
        }
    }

    /**
     * Converts row and column numbers to Excel cell reference (e.g., 0,0 -> A1)
     */
    private String getCellReference(int row, int col) {
        // Convert column number to letter(s)
        StringBuilder colLetters = new StringBuilder();
        int colNum = col;
        while (colNum >= 0) {
            colLetters.insert(0, (char) ('A' + (colNum % 26)));
            colNum = colNum / 26 - 1;
            if (colNum < 0) break;
        }
        // Row is 1-based in Excel
        return colLetters.toString() + (row + 1);
    }

    /**
     * Adds formula to formulaMap in the format similar to Template.java
     */
    private void addToFormulaMap(String cellRef, String formulaString, Map<String, JsonNode> formulaMap,
                                 ObjectMapper objectMapper) {
        // Remove 'of:' prefix if present
        String cleanFormula = formulaString.replaceFirst("^of:", "");
        
        // Determine formula type
        String formulaType = determineFormulaType(cleanFormula);
        
        // Create JsonNode similar to Template.java structure
        ObjectNode formulaNode = objectMapper.createObjectNode();
        formulaNode.put("type", formulaType);
        formulaNode.put("formulaString", cleanFormula);
        
        formulaMap.put(cellRef, formulaNode);
    }

    /**
     * Determines the formula type based on formula content
     */
    private String determineFormulaType(String formula) {
        // Check for custom functions first (case-insensitive check)
        String upperFormula = formula.toUpperCase();
        if (upperFormula.contains("Q.GETT(")) {
            return "GETT";
        } else if (upperFormula.contains("Q.PUSHT(")) {
            return "PUSHT";
        } else if (upperFormula.contains("Q.GET(")) {
            return "GET";
        } else if (upperFormula.contains("Q.PUSH(")) {
            return "PUSH";
        } else {
            // For standard Excel functions, default to "GET" type
            // This matches the pattern in Template.java where most formulas are type "GET"
            return "GET";
        }
    }

    /**
     * Extracts text content from a node, handling nested text spans
     */
    private String getTextContent(Node node) {
        if (node == null) {
            return null;
        }
        StringBuilder text = new StringBuilder();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                text.append(child.getNodeValue());
            } else if (child.getNodeType() == Node.ELEMENT_NODE) {
                String childText = getTextContent(child);
                if (childText != null) {
                    text.append(childText);
                }
            }
        }
        return text.length() > 0 ? text.toString() : null;
    }

    /**
     * Processes standard formulas (EOMONTH, SUM, IFERROR, etc.)
     */
    private void processFormula(String formula, Map<String, Integer> formulaCounts,
                               Map<String, Integer> customFormulaCounts) {
        // Remove 'of:' prefix if present
        String cleanFormula = formula.replaceFirst("^of:", "");

        // Extract function names using regex
        Pattern pattern = Pattern.compile("([A-Z_]+)\\s*\\(");
        Matcher matcher = pattern.matcher(cleanFormula);

        while (matcher.find()) {
            String functionName = matcher.group(1);
            
            // Check if it's a custom function
            if (functionName.startsWith("Q.") || functionName.equals("GETT") || 
                functionName.equals("PUSHT") || functionName.equals("GET") || 
                functionName.equals("PUSH")) {
                // Extract full custom function call
                Pattern customPattern = Pattern.compile("Q\\.(GETT|PUSHT|GET|PUSH)\\s*\\(");
                Matcher customMatcher = customPattern.matcher(cleanFormula);
                if (customMatcher.find()) {
                    String customFunc = "Q." + customMatcher.group(1);
                    customFormulaCounts.put(customFunc, customFormulaCounts.getOrDefault(customFunc, 0) + 1);
                }
            } else {
                // Standard function
                formulaCounts.put(functionName, formulaCounts.getOrDefault(functionName, 0) + 1);
            }
        }
    }

    /**
     * Processes custom formulas from text content
     */
    private void processCustomFormula(String textContent, Map<String, Integer> customFormulaCounts) {
        // Extract Q.GETT, Q.PUSHT, Q.GET, Q.PUSH patterns
        Pattern pattern = Pattern.compile("Q\\.(GETT|PUSHT|GET|PUSH)\\s*\\(");
        Matcher matcher = pattern.matcher(textContent);
        
        while (matcher.find()) {
            String customFunc = "Q." + matcher.group(1);
            customFormulaCounts.put(customFunc, customFormulaCounts.getOrDefault(customFunc, 0) + 1);
        }
    }

    /**
     * Writes analysis result to JSON file
     * @param analysisResult Analysis result map
     * @return Path to the written JSON file
     */
    public String writeAnalysisToJson(Map<String, Object> analysisResult) throws IOException {
        logger.info("writeAnalysisToJson method invoked");

        // Ensure output directory exists
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Generate filename with timestamp
        String fileName = (String) analysisResult.get("fileName");
        String baseName = fileName != null && fileName.contains(".") 
                ? fileName.substring(0, fileName.lastIndexOf('.')) 
                : "ods_analysis";
        String jsonFileName = baseName + "_analysis_" + System.currentTimeMillis() + ".json";
        File jsonFile = new File(outputDir, jsonFileName);

        // Convert map to JSON and write
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.valueToTree(analysisResult);

        try (FileOutputStream fos = new FileOutputStream(jsonFile)) {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(fos, jsonNode);
        }

        logger.info("Analysis JSON written to: {}", jsonFile.getAbsolutePath());
        return jsonFile.getAbsolutePath();
    }
}

