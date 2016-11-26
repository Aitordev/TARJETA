package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataBaseOperations {

	//*****************************INITIALIZE VARIABLES****************************
/*	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/TARJETA";
	static final String DB_NAME = "TARJETA";
	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "root";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
*/
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/tarjeta?serverTimezone=UTC&autoReconnect=true&useSSL=false";
	static final String DB_NAME = "tarjeta";
	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "password";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;



	//*****************************LOGIN METHODS***********************************
	public static boolean login(String userNick, Integer password) throws Exception{
		if (checkUserLogin(userNick, password.toString()))
			return true;
		else
			return false;
	}


	//*****************************USER METHODS************************************
	public static void createUser(String userNick, int password) throws Exception{
		if (checkUserExists(userNick))
			throw new Exception("Usuario ya existe");
		//INSERT INTO Database User user, pwd), redordar password.toString()
		else
			InsertMethod("Cliente", "(`nick`, `pass`)" , "'"+userNick +"' , '"+ Integer.toString(password)+"'");
	}

	public static void deleteUser(String userNick) throws Exception{
		if(!checkUserExists(userNick))
			throw new Exception("Usuario doesn't exist");
		//remove user where name = userNick
	}


	//*****************************SHOP METHODS************************************
	public static void altaTienda(String shopName) throws Exception{
		if(checkShopExists(shopName))
			throw new Exception("Tienda ya existe");
		else
			InsertMethod("Tiendas","(`nombre`)", shopName);
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
			e.printStackTrace();	
		}
	}

	private static void InsertMethod(String WHERE,String columns, String VALUES){
		connect();
		//Preparo la query INSERT INTO `TARJETA`.`Cliente` (`nick`, `pass`) VALUES ('admin', '1216925212');

		String query = "INSERT INTO "+DB_NAME+"."+WHERE+ columns+
				" VALUES ("+VALUES+");";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			//Ejecuto la query
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();	
			}
		}
	}

	private static void DeleteMethod(String FROM, String WHERE, String IS){
		connect();
		//Preparo la query
		String query = "DELETE * FROM "+DB_NAME+"."+FROM+
				" WHERE "+ WHERE +"='"+IS+"'";
		try {
			stmt = conn.prepareStatement(query);

			//Ejecuto la query
			stmt.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();	
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
				e.printStackTrace();	
			}
		}
	}


	private static boolean checkUserExists(String user) {
		connect();
		String query = "SELECT * FROM  "+DB_NAME+"."+"Cliente WHERE nick='"+user+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{
				if(stmt !=null)
					stmt.close();
				if(conn !=null)
					conn.close();
			}
			catch(Exception e){
				e.printStackTrace();	
			}
		}
		return result;
	}

	private static boolean checkShopExists(String shop) {
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"Tienda WHERE nombre='"+shop+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{
				if(stmt !=null)
					stmt.close();
				if(conn !=null)
					conn.close();
			}
			catch(Exception e){
				e.printStackTrace();	
			}
		}
		return result;
	}

	private static boolean checkUserLogin(String user, String pass) {
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"Cliente WHERE nick='"+user+"' AND pass='"+pass+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		finally{
			try{
				if(stmt !=null)
					stmt.close();
				if(conn !=null)
					conn.close();
			}
			catch(Exception e){
				e.printStackTrace();	
			}
		}
		return result;
	}

}
