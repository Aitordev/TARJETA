package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.vaadin.ui.Notification;

public class DataBaseOperations {

	//*****************************INITIALIZE VARIABLES****************************
	/*
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
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
	public static boolean login(String userNick, String password) throws Exception{
		if (checkUserLogin(userNick, password))
			return true;
		else
			return false;
	}


	//*****************************USER METHODS************************************
	public static void createUser(String userNick, String password) throws Exception{
		if (checkUserExists(userNick))
			throw new Exception("Usuario ya existe");
		else
			InsertMethod("cliente", "(`nick`, `pass`)" , "'"+userNick +"' , '"+ password+"'");
	}

	public static void deleteUser(String userNick) throws Exception{
		if(!checkUserExists(userNick))
			throw new Exception("Usuario doesn't exist");
		DeleteMethod("cliente", "'nick'", "'" + userNick +"'");
	}


	//*****************************SHOP METHODS************************************
	public static void altaTienda(String shopName) throws Exception{
		if(checkShopExists(shopName))
			throw new Exception("Tienda ya existe");
		else
			InsertMethod("tienda","(`nombre`)", shopName);
	}

	public static void bajaTienda(String shopName) throws Exception{
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		DeleteMethod("tienda", "nombre", shopName);
	}


	//*****************************INFORM METHODS**********************************

	public static String userInform(){
		connect();
		String query = "SELECT idCliente, idTarjeta, nick FROM tarjeta.cliente;";
		String result = "Id      idTarjeta       nick\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					result = result + columnValue + "      ";
				}
				result = result + "\n";
			}
		}
		catch (Exception e) {
			e.printStackTrace();	
			Notification.show("Cannot connect BD!");	
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
				Notification.show("Cannot connect BD!");	
			}
		}
		return result;
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
			Notification.show("Cannot connect BD!");	
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
			Notification.show("Cannot connect BD!");
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
				Notification.show("Cannot connect BD!");	
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
			Notification.show("Cannot connect BD!");	
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
				Notification.show("Cannot connect BD!");	
			}
		}
	}


	private static boolean checkUserExists(String user) {
		connect();
		String query = "SELECT * FROM  "+DB_NAME+"."+"cliente WHERE nick='"+user+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
			Notification.show("Cannot connect BD!");	
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
				Notification.show("Cannot connect BD!");	
			}
		}
		return result;
	}

	private static boolean checkShopExists(String shop) {
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"tienda WHERE nombre='"+shop+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
			Notification.show("Cannot connect BD!");	
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
				Notification.show("Cannot connect BD!");	
			}
		}
		return result;
	}

	private static boolean checkUserLogin(String user, String pass) {
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"cliente WHERE nick='"+user+"' AND pass='"+pass+"'";
		boolean result = false;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) result = true;
		}
		catch (Exception e) {
			e.printStackTrace();	
			Notification.show("Cannot connect BD!");
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
				Notification.show("Cannot connect BD!");
			}
		}
		return result;
	}

}
