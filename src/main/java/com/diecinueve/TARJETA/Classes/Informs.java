package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class Informs {

	public static String informUsers(){
		return DataBaseOperations.userInform();
	}
	
	public static String informCard(){
		return DataBaseOperations.CardInform();
	}
	
	public static String informShop(){
		return DataBaseOperations.ShopInform();
	}
	
	public static String informPurchases(){
		return DataBaseOperations.purchasesInform();
	}
}
