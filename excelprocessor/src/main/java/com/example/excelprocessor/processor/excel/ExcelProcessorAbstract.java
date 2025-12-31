package com.seventythreestrings.campaign.excelprocessor;

public abstract class ExcelProcessorAbstract implements ExcelProcessor{

    protected abstract Map<cellID, Formula> preprocessFormulaMap(Map<cellID, Formula> formulaMap);
	

	protected Map<cellID, String> processFormulaMap(Map<cellID, Formula> formulaMap)
	{
			
		// process formula map entries in parallel using parallel streams
        // This will likely create many short lived light weight formula processor objects. Possible one per thread. 
		return formulaMap.entrySet().parallelStream()
			.collect(Collectors.toConcurrentMap(
				Map.Entry::getKey,
				entry -> calculateFormula(entry.getValue()),
				(existing, replacement) -> existing
			));
	}

	protected String calculateFormula(Formula formula)
	{
        // The formula processor objects created by the factory will be short lived objects 
        // because its reference is not propogated further. Once the calculate method exists, the object is garbage collected in 
        // the next cycle. 
		return (formulaFactory.createFormula(formula.getType())).calculate();
		
	}

}
