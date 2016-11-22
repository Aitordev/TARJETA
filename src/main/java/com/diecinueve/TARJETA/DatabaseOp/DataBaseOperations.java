package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.diecinueve.TARJETA.Classes.*;

public class DataBaseOperations {

	//*****************************INITIALIZE VARIABLES****************************
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "password";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;

	private static List<User> users = new ArrayList<User>(); 

	
	
	//*****************************LOGIN METHODS***********************************
	public static boolean login(String userNick, int password) throws Exception{
//		if (checkUserExists(userNick))
//			throw new Exception("Usuario ya existe");
//		//ver si coincide userNick con password, recordar password.toString();
//		return true;
		for(int i = 0; i < users.size(); i++){
			User us = users.get(i);
			if(us.nick.equals(userNick) && us.password.equals(password))
				return true;
		}
		return false;
	}


	//*****************************USER METHODS************************************
	public static void createUser(String userNick, int password) throws Exception{
//		if (checkUserExists(userNick))
//			throw new Exception("Usuario ya existe");
//		//INSERT INTO Database User user, pwd), redordar password.toString()
//		InsertMethod("Cliente", userNick + "," + Integer.toString(password));
		users.add(new User(userNick, Integer.toString(password)));
	}

	public static void deleteUser(String userNick) throws Exception{
//		if(!checkUserExists(userNick))
//			throw new Exception("Usuario doesn't exist");
		//remove user where name = userNick
		for(int i = 0; i < users.size(); i++){
			User us = users.get(i);
			if(us.nick.equals(userNick) ){
				users.remove(i);
				break;
			}
		}
	}

	
	//*****************************SHOP METHODS************************************
	public static void altaTienda(String shopName) throws Exception{
//		if(checkShopExists(shopName))
//			throw new Exception("Tienda ya existe");
//		InsertMethod("Tiendas", shopName);
		
	}
	
	public static void bajaTienda(String shopName) throws Exception{
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		DeleteMethod("Tiendas", "nombre", shopName);
	}


	//*****************************PRIVATE METHODS*********************************

	private static void connect(){//Metodo para iniciar una conexion a la Base de Datos
		try{

			//Conexion con Base de Datos
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

		}
		catch(Exception e){
			//Hacer nada o sacar algun tipo de mensaje de error
		}
	}

	private static void InsertMethod(String WHERE, String VALUES){
		connect();
		//Preparo la query
		String query = "INSERT INTO " +WHERE+
				" VALUES ("+VALUES+")";
		try {
			stmt = conn.prepareStatement(query);

			//Ejecuto la query
			stmt.executeUpdate();
		}
		catch (Exception e) {
			//Indicar error
		}
		finally{
			//Cierro la conexion con la Base de Datos en cualquier caso
			try{
				if(stmt != null)
					stmt.close();

				if(conn!=null)
					conn.close();
			}
			catch(Exception e){
				//Hacer nada
			}
		}
	}
	
	private static void DeleteMethod(String FROM, String WHERE, String IS){
		connect();
		//Preparo la query
		String query = "DELETE * FROM " +FROM+
				" WHERE "+ WHERE +"='"+IS+"'";
		try {
			stmt = conn.prepareStatement(query);

			//Ejecuto la query
			stmt.executeUpdate();
		}
		catch (Exception e) {
			//Indicar error
		}
		finally{
			//Cierro la conexion con la Base de Datos en cualquier caso
			try{
				if(stmt != null)
					stmt.close();

				if(conn!=null)
					conn.close();
			}
			catch(Exception e){
				//Hacer nada
			}
		}
	}
	

	
	private static boolean checkUserExists(String user) {
		//return ( SELECT * FROM database WHERE nick).equals(user))
		return true;
	}
	
	private static boolean checkShopExists(String shop) {
		//return ( SELECT * FROM database WHERE shop).equals(shop))
		return true;
	}
}
