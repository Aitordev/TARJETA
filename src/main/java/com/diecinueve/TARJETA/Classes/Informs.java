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
	
	public static String informPurchases(String nick){
		return DataBaseOperations.purchasesInform();
	}
	
	public static String informPrizes(){
		return DataBaseOperations.prizesInform();
	}
	
	public static String informExchanges(){
		return DataBaseOperations.exchangesInform();
	}
	
	public static String informMonthlyUser(String name){
		return DataBaseOperations.monthlyUser(name);
	}
	
	public static String informMonthlyAdmin(){
		return DataBaseOperations.monthlyAdmin();
	}
}
