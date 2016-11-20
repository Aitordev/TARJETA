package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class User {
	
	int idUser;
	String nick;
	String password;
	
	public void altaUsuario(String nick, String password) throws Exception{
		if(nick.isEmpty() || password.isEmpty())
			throw new Exception("Nick or pwd empty");
		else if(password.length() < 8 || !password.matches(".*\\d.*"))
			throw new Exception("pwd needs 8 caracters and a number");
		DataBaseOperations.createUser(nick, password);
	}
	
	public void bajaUsuario(String userNick) throws Exception{
		if(nick.isEmpty() || password.isEmpty())
			throw new Exception("Nick or pwd empty");
		DataBaseOperations.deleteUser(userNick);
	}
}
