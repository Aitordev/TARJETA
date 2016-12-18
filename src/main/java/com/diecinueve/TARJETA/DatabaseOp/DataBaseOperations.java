package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.vaadin.ui.Notification;

public class DataBaseOperations {

	//*****************************INITIALIZE VARIABLES****************************
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/TARJETA";
	static final String DB_NAME = "TARJETA";
	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "root";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
	/*

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/tarjeta?serverTimezone=UTC&autoReconnect=true&useSSL=false";
	static final String DB_NAME = "tarjeta";
	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "password";
	private static Connection conn = null;
	private static PreparedStatement stmt = null;
 */


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
	
	public static void editUser(String nick, String newPasswordHash) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario doesn't exist");
		UpdateMethod();	
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
	
	public static void editTienda(String shopName) throws Exception {
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		UpdateMethod();
	}

	public static void bajaTienda(String shopName) throws Exception{
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		DeleteMethod("tienda", "nombre", shopName);
	}

	//*****************************CARD METHODS************************************

	
	public static boolean createCard(String nick, String fullName, String phone, String adress, String mail) throws Exception {

		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		
		InsertMethod("tarjeta", "(`nombreCompleto`, `telefono`, `direccion`, `email`)" , "'"+fullName +"' , '"+ phone+ "' , '"+adress +"' , '"+mail +"'");
		//TODO: lo de arriba que devuelva el id tarjeta quizas?
		//TODO: int cardID = lookForCardId(nick) -> returns id user of the card
		//Edit method user a tarjeta = idTarjeta
		return false;
	}
	
	public static boolean editCard(int idUser, String fullName, String phone, String adress, String mail) {
		// TODO
		return false;
	}

	public static boolean deleteCard(String nick) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		//TODO: int cardID = lookForCardId(nick) -> returns id user of the card
		int cardId = 0;
		DeleteMethod("tarjeta", "idTarjeta", Integer.toString(cardId));
		return false;
	}
	
	public static boolean buy(String nick, String shop, double price) throws Exception {
		// TODO Auto-generated method stub
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		if(!checkShopExists(shop))
			throw new Exception("Tienda no existe");
		//TODO: int cardID = lookForCardId(nick) -> returns id user of the card
		//TODO: int shopID = lookForShopId(shop) -> returns id shop

		int cardID = 0;
		int shopID = 0;
		
		InsertMethod("compras", "(`idTienda`, `idTarjeta`, `importe`)", "'"+shopID+ "' , '"+cardID +"' , '"+ Double.toString(price)+"'");
		int points = (int) (price + 0.5);//TODO: probar correcto funcionamiento
		//TODO: edit card: add points
		return false;
	}

	public static boolean exchange(String nick, String prize) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		if(!checkPrizeExists(prize))
			throw new Exception("El premio no existe");
		
		//TODO: int cardID = lookForCardId(nick) -> returns id user of the card
		//TODO: int prizeID = lookForPrizeId(nick) -> returns id prize
		int cardID = 0;
		int prizeID = 0;
		
		//TODO: get pointsCard
		//TODO: get pointsPrize
		int pointsCard = 0;
		int pointsPrize = 0;
		if(pointsCard < pointsPrize)
			throw new Exception("No hay puntos suficientes");
		InsertMethod("canjeos", "(`idPremio`, `idTarjeta`)", "'"+prizeID+ "' , '"+cardID +"'");
		//TODO: edit tarjeta y quitarle pointsPrize
		return false;
	}

	
	//*****************************PRIZE METHODS**********************************

	public static boolean createPrize(String concept, String quantity, int priceInPoints) throws Exception {
		if(checkPrizeExists(concept))
			throw new Exception("El premio ya existe");
		
		InsertMethod("premios", "(`concepto`, `cantidad`, `puntosNecesarois`)", "'"+concept+ "' , '"+quantity + "' , '"+Integer.toString(priceInPoints)+"'");
		return true;
	}

	public static boolean editPrize(String concept, String quantity, String priceInPoints) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean deletePrize(String concept) throws Exception {
		if(!checkPrizeExists(concept))
			throw new Exception("El premio no existe");
		DeleteMethod("premios", "concepto", concept);
		return true;
	}


	//*****************************INFORM METHODS**********************************

	public static String userInform(){
		connect();
		String query = "SELECT idCliente, idTarjeta, nick FROM "+DB_NAME+".cliente;";
		String result = "Id\t idTarjeta\t nick\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					result = result + columnValue + "\t";
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
	
	public static String purchasesInform(){
		connect();
		String query = "SELECT compras.idCompras, tarjeta.nombreCompleto, tienda.nombre, compras.importe, compras.date"
					+ "	FROM "+DB_NAME+".compras"
					+ "	INNER JOIN "+DB_NAME+".tarjeta"
					+ " ON compras.idTarjeta=tarjeta.idTarjeta"
					+ "	INNER JOIN "+DB_NAME+".tienda"
					+ "	ON compras.idTarjeta=tienda.idTienda"
					+ "	ORDER BY compras.idCompras;";
		String result = "IdCompras\t userName\t nombreTienda\t importe\t fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = rs.getString(i);
					result = result + columnValue + "\t";
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
	
	private static void UpdateMethod() {
		// TODO Auto-generated method stub
		
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
	
	private static boolean checkUserHasCard(String user) {
		//TODO: return true if the user has a cardId
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

	private static boolean checkPrizeExists(String prize) {
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"canjeos WHERE nombre='"+prize+"'";
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
