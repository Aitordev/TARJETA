package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataBaseOperations {

	//*****************************INITIALIZE VARIABLES******************************
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "root";
	private Connection conn = null;
	private PreparedStatement stmt = null;


	//*****************************LOGIN METHODS************************************
	public static boolean login(String userNick, int password) throws Exception{
		if (checkUserExists(userNick))
			throw new Exception("Usuario ya existe");
		//ver si coincide userNick con password, recordar password.toString();

		return true;
	}


	//*****************************USER METHODS************************************
	public static void createUser(String userNick, int password) throws Exception{
		if (checkUserExists(userNick))
			throw new Exception("Usuario ya existe");
		//INSERT INTO Database User user, pwd), redordar password.toString()
	}

	public static void deleteUser(String userNick) throws Exception{
		if(!checkUserExists(userNick))
			throw new Exception("Usuario doesn't exist");
		//remove user where name = userNick
	}

	
	//*****************************SHOP METHODS************************************
	public void altaTienda(String shopName) throws Exception{
		if(!checkShopExists(shopName))
			throw new Exception("Tienda ya existe");
		InsertMethod("Tiendas", shopName);
	}


	//*****************************PRIVATE METHODS************************************

	private void connect(){//Metodo para iniciar una conexion a la Base de Datos
		try{

			//Conexion con Base de Datos
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

		}
		catch(Exception e){
			//Hacer nada o sacar algun tipo de mensaje de error
		}
	}

	private void InsertMethod(String WHERE, String VALUES){
		this.connect();
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
	
	private void DeleteMethod(String FROM, String WHERE, String IS){
		this.connect();
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
