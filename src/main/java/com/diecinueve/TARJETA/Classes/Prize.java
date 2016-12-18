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


	public static boolean createPrize(String concept, String quantity, int priceInPoints) throws Exception{
		checkStringNotNull(concept);
		checkStringNotNull(quantity);
		
		return DataBaseOperations.createPrize(concept, quantity, priceInPoints);
	}

	public static boolean editPrize(String concept, String quantity, String priceInPoints) throws Exception{
		checkStringNotNull(concept);
		checkStringNotNull(quantity);
		checkStringNotNull(priceInPoints);
		
		return DataBaseOperations.editPrize(concept, quantity, priceInPoints);
	}
	
	public static boolean deletePrize(String concept) throws Exception{
		checkStringNotNull(concept);
		
		return DataBaseOperations.deletePrize(concept);
	}
	
	private static void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}