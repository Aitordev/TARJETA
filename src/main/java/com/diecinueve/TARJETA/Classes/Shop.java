package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class Shop {
	
	public String nombreTienda;
	
	public Shop() {
		super();
	}

	public Shop(String nombreTienda) {
		super();
		this.nombreTienda = nombreTienda;
	}


	public static void altaTienda(String nombreTienda) throws Exception{
		checkStringNotNull(nombreTienda);
		DataBaseOperations.altaTienda(nombreTienda);
	}
	
	public static void editTienda(String nombreTienda, String newNombreTienda) throws Exception{
		checkStringNotNull(nombreTienda);
		checkStringNotNull(newNombreTienda);

		DataBaseOperations.editTienda(nombreTienda, newNombreTienda);
	}

	
	public static void bajaTienda(String nombreTienda) throws Exception{
		checkStringNotNull(nombreTienda);
		DataBaseOperations.bajaTienda(nombreTienda);
	}
	
	
	private static void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}
