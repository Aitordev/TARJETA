package com.diecinueve.TARJETA.Classes;

import com.diecinueve.TARJETA.DatabaseOp.DataBaseOperations;

public class User {
	
	public String nick;
	public String password;
	
	public User() {
		super();
	}
	
	public User(String nick, String password) {
		super();
		this.nick = nick;
		this.password = password;
	}

	public boolean login(String nick, String password) throws Exception{
		checkStringNotNull(nick, password);
		int passwordHash = password.hashCode();
		return DataBaseOperations.login(nick, passwordHash);
	}
	
	public static void altaUsuario(String nick, String password) throws Exception{
		checkStringNotNull(nick, password);
		if(password.length() < 8 || !password.matches(".*\\d.*"))
			throw new Exception("pwd needs 8 caracters and a number");
		int passwordHash = password.hashCode();
		DataBaseOperations.createUser(nick, passwordHash);
	}
	
	public void bajaUsuario(String userNick) throws Exception{
		checkStringNotNull(userNick);
		DataBaseOperations.deleteUser(userNick);
	}
	
	
	private void checkStringNotNull(String str1) throws Exception{
		if(str1.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
	
	private static void checkStringNotNull(String str1, String str2) throws Exception{
		if(str1.isEmpty() || str2.isEmpty())
			throw new Exception("Nick or pwd empty");
	}
}