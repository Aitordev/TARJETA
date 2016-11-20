package com.diecinueve.TARJETA.DatabaseOp;

public class DataBaseOperations {

	public static void createUser(String user, String password) throws Exception{
		if (checkUserExists(user))
			throw new Exception("Usuario ya existe");
		//INSERT INTO Database User user, pwd)
	}
	
	public static void deleteUser(String userNick) throws Exception{
		if(!checkUserExists(userNick))
			throw new Exception("Usuario doesn't exist");
		//remove user where name = userNick
	}


	private static boolean checkUserExists(String user) {
		//return ( SELECT * FROM database WHERE nick).equals(user))
		return true;
	}
}
