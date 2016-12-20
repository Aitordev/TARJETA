package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class Card {
	
	public String fullName;
	public String phone;
	public String adress;
	public String mail;
	public int points;
	
	public Card() {
		super();
	}
	
	public Card(String fullName, String phone, String adress, String mail) {
		super();
		this.fullName = fullName;
		this.phone = phone;
		this.adress = adress;
		this.mail = mail;
		this.points = 0;
	}

	public static void createCard(String nick, String fullName, String phone, String adress, String mail) throws Exception{
		checkStringNotNull(nick);
		checkStringNotNull(fullName);
		checkStringNotNull(phone);
		checkStringNotNull(adress);
		checkStringNotNull(mail);
		
		DataBaseOperations.createCard(nick, fullName, phone, adress, mail);
	}
	
	public static void editCard(String idUser, String fullName, String phone, String adress, String mail) throws Exception{
		checkStringNotNull(fullName);
		checkStringNotNull(phone);
		checkStringNotNull(adress);
		checkStringNotNull(mail);
		
		DataBaseOperations.editCard(idUser, fullName, phone, adress, mail);
	}
	
	public static void deleteCard(String nick) throws Exception{
		DataBaseOperations.deleteCard(nick);
	}
	
	public static void buy(String nick, String shop, double price) throws Exception{
		checkStringNotNull(shop);
		DataBaseOperations.buy(nick, shop, price);
	}
	
	public static void exchange(String nick, String premio) throws Exception{
		checkStringNotNull(premio);
		DataBaseOperations.exchange(nick, premio);
	}
	
	public static void getMyPoints(String nick) throws Exception{
		checkStringNotNull(nick);
		DataBaseOperations.getCardPoints(nick);
	}
	
	private static void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}