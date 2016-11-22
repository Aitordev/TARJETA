package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class Shop {


	public void altaTienda(String nombreTienda) throws Exception{
		checkStringNotNull(nombreTienda);
		DataBaseOperations.altaTienda(nombreTienda);
	}

	
	public void bajaTienda(String nombreTienda) throws Exception{
		checkStringNotNull(nombreTienda);
		DataBaseOperations.bajaTienda(nombreTienda);
	}
	
	
	private void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}
