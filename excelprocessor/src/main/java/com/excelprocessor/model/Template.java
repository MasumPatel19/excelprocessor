package com.excelprocessor.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;

/**
 * Domain object representing a Template.
 */
public class Template {
	
	private Map<String, JsonNode> formulaMap;
	
	public Map<String, JsonNode> getFormulaMap() {

		//TODO: Hard-coding this for initial test purposes. 
        //TODO: Will be replaced with actual data from the campaignexcelpreviewdto object. This will be done in the next iteration.
        
        ObjectMapper objectMapper = new ObjectMapper();
        formulaMap = new HashMap<>();
        
        // Create JsonNode for A1 with type and value
        ObjectNode a1Node = objectMapper.createObjectNode();
        a1Node.put("type", "GET");
        a1Node.put("formulaString", "A1");
        formulaMap.put("A1", a1Node);
        
        // Create JsonNode objects for formula strings
        ObjectNode a2Node = objectMapper.createObjectNode();
		a2Node.put("type", "GET");
        a2Node.put("formulaString", "=A1+1");
        formulaMap.put("A2", a2Node);
        
        ObjectNode a3Node = objectMapper.createObjectNode();
		a3Node.put("type", "GET");
        a3Node.put("formulaString", "=A2+1");
        formulaMap.put("A3", a3Node);
        
        ObjectNode a4Node = objectMapper.createObjectNode();
		a4Node.put("type", "GET");
        a4Node.put("formulaString", "=A3+1");
        formulaMap.put("A4", a4Node);
        
        ObjectNode a5Node = objectMapper.createObjectNode();
		a5Node.put("type", "GET");
        a5Node.put("formulaString", "=A4+1");
        formulaMap.put("A5", a5Node);
        
        ObjectNode a6Node = objectMapper.createObjectNode();
		a6Node.put("type", "GET");
        a6Node.put("formulaString", "=A5+1");
        formulaMap.put("A6", a6Node);
        
        ObjectNode a7Node = objectMapper.createObjectNode();
		a7Node.put("type", "GET");
        a7Node.put("formulaString", "=A6+1");
        formulaMap.put("A7", a7Node);
        
        ObjectNode a8Node = objectMapper.createObjectNode();
		a8Node.put("type", "GET");
        a8Node.put("formulaString", "=A7+1");
        formulaMap.put("A8", a8Node);
        
        ObjectNode a9Node = objectMapper.createObjectNode();
		a9Node.put("type", "GET");
        a9Node.put("formulaString", "=A8+1");
        formulaMap.put("A9", a9Node);
        
        ObjectNode a10Node = objectMapper.createObjectNode();
		a10Node.put("type", "GET");
        a10Node.put("formulaString", "=A9+1");
        formulaMap.put("A10", a10Node);
        
        ObjectNode a11Node = objectMapper.createObjectNode();
		a11Node.put("type", "GET");
        a11Node.put("formulaString", "=A10+1");
        formulaMap.put("A11", a11Node);
        
        ObjectNode a12Node = objectMapper.createObjectNode();
		a12Node.put("type", "GET");
        a12Node.put("formulaString", "=A11+1");
        formulaMap.put("A12", a12Node);
        
        ObjectNode a13Node = objectMapper.createObjectNode();
		a13Node.put("type", "GET");
        a13Node.put("formulaString", "=A12+1");
        formulaMap.put("A13", a13Node);
        
        ObjectNode a14Node = objectMapper.createObjectNode();
		a14Node.put("type", "GET");
        a14Node.put("formulaString", "=A13+1");
        formulaMap.put("A14", a14Node);
        
        ObjectNode a15Node = objectMapper.createObjectNode();
		a15Node.put("type", "GET");
        a15Node.put("formulaString", "=A14+1");
        formulaMap.put("A15", a15Node);
        
        ObjectNode a16Node = objectMapper.createObjectNode();
		a16Node.put("type", "GET");
        a16Node.put("formulaString", "=A15+1");
        formulaMap.put("A16", a16Node);
        
        ObjectNode a17Node = objectMapper.createObjectNode();
		a17Node.put("type", "GET");
        a17Node.put("formulaString", "=A16+1");
        formulaMap.put("A17", a17Node);
        
        ObjectNode a18Node = objectMapper.createObjectNode();
		a18Node.put("type", "GET");
        a18Node.put("formulaString", "=A17+1");
        formulaMap.put("A18", a18Node);
        
        ObjectNode a19Node = objectMapper.createObjectNode();
		a19Node.put("type", "GETT");
        a19Node.put("formulaString", "=A18+1");
        formulaMap.put("A19", a19Node);
        
        ObjectNode a20Node = objectMapper.createObjectNode();
		a20Node.put("type", "PUSH");
        a20Node.put("formulaString", "=A19+1");
        formulaMap.put("A20", a20Node);
        
        ObjectNode a21Node = objectMapper.createObjectNode();
		a21Node.put("type", "PUSHT");
        a21Node.put("formulaString", "=A20+1");
        formulaMap.put("A21", a21Node);

		return formulaMap;
	}
	
	public void setFormulaMap(Map<String, JsonNode> formulaMap) {
		this.formulaMap = formulaMap;
	}
}

