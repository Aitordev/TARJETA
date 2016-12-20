package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class Prize {
	
	public String concept;
	public String quantity;
	public String priceInPoints;

	
	public Prize() {
		super();
	}

	public Prize(String concept, String quantity, String priceInPoints) {
		super();
		this.concept = concept;
		this.quantity = quantity;
		this.priceInPoints = priceInPoints;
	}


	public static void createPrize(String concept, String quantity, int priceInPoints) throws Exception{
		checkStringNotNull(concept);
		checkStringNotNull(quantity);
		
		DataBaseOperations.createPrize(concept, quantity, priceInPoints);
	}

	public static void editPrize(String concept, String NewConcept, String quantity, int priceInPoints) throws Exception{
		checkStringNotNull(concept);
		checkStringNotNull(NewConcept);
		checkStringNotNull(quantity);
		
		DataBaseOperations.editPrize(concept, NewConcept, quantity, priceInPoints);
	}
	
	public static void deletePrize(String concept) throws Exception{
		checkStringNotNull(concept);
		
		DataBaseOperations.deletePrize(concept);
	}
	
	private static void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}