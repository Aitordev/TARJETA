package com.diecinueve.TARJETA.DatabaseOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.diecinueve.TARJETA.Classes.Card;
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
			InsertMethod("cliente", "(`nick`, `pass`)" , "('"+userNick +"' , '"+ password+"')");
	}

	public static void editUser(String nick, String newPasswordHash) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario doesn't exist");
		int idCliente = getUserId(nick);
		UpdateMethod("cliente", "`pass`='" + newPasswordHash +"'", "`idCliente`='" + idCliente +"'");
	}


	public static void deleteUser(String userNick) throws Exception{
		if(!checkUserExists(userNick))
			throw new Exception("Usuario doesn't exist");
		int id = getUserId(userNick);
		DeleteMethod("cliente", "idCliente",Integer.toString(id));
	}

	public static int getCardIdFromUser(String Nick){
		connect();
		String query = "SELECT idTarjeta FROM "+DB_NAME+"."+"cliente WHERE nick='"+Nick+"'";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("idTarjeta");
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

	public static int getCardPoints(String nick){
		return getCardPoints(getCardIdFromUser(nick));
	}

	public static int getCardPoints(int cardId){
		connect();
		String query = "SELECT puntos FROM "+DB_NAME+"."+"tarjeta WHERE idTarjeta='"+cardId+"'";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("puntos");
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


	//*****************************SHOP METHODS************************************
	public static void altaTienda(String shopName) throws Exception{
		if(checkShopExists(shopName))
			throw new Exception("Tienda ya existe");
		else
			InsertMethod("tienda"," (`nombre`) ", "('" + shopName + "')");
	}

	public static void editTienda(String shopName, String shopNewName) throws Exception {
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		if(checkShopExists(shopNewName))
			throw new Exception("Nombre de tienda usado");
		int idTienda = getIdTienda(shopName);
		UpdateMethod("tienda", "`nombre`='" + shopNewName +"'", "`idTienda`='" + idTienda +"'");
	}

	public static void bajaTienda(String shopName) throws Exception{
		if(!checkShopExists(shopName))
			throw new Exception("Tienda no existe");
		DeleteMethod("tienda", "nombre", shopName);
	}

	//*****************************CARD METHODS************************************


	public static void createCard(String nick, String fullName, String phone, String adress, String mail) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		InsertMethod("tarjeta", "(`nombreCompleto`, `telefono`, `direccion`, `email`)" , "('"+fullName +"' , '"+ phone+ "' , '"+adress +"' , '"+mail +"')");
		String idCard = selectMethod("idTarjeta", "tarjeta", "nombreCompleto = '"+fullName +"'");
		int userId = getUserId(nick);
		UpdateMethod("cliente", "`idTarjeta`='"+ idCard + "'", "`idCliente`='"+ userId +"'" );
	}

	public static void editCard(String nick, String fullName, String phone, String adress, String mail) {
		int cardId = getCardIdFromUser(nick);
		UpdateMethod("tarjeta", "`nombreCompleto`='"+fullName+"', `telefono`='"+phone+"', `direccion`='"+adress+"', `email`='"+mail+"'", "`idTarjeta`='" + cardId +"'");
	}

	public static void deleteCard(String nick) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		int userId = getUserId(nick);
		int cardId = getCardIdFromUser(nick);
		DeleteMethod("tarjeta", "idTarjeta", Integer.toString(cardId));
		UpdateMethod("cliente", "`idTarjeta`='"+ 0 + "'", "`idCliente`='"+ userId +"'" );
	}

	public static Card getCardData(String nick) throws Exception{
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");

		return getCardData(getCardIdFromUser(nick));
	}


	public static void buy(String nick, String shop, double price) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		if(!checkShopExists(shop))
			throw new Exception("Tienda no existe");

		int cardID = getCardIdFromUser(nick);
		int shopID = getIdTienda(shop);

		InsertMethod("compras", "(`idTienda`, `idTarjeta`, `importe`)", "('"+shopID+ "' , '"+cardID +"' , '"+ Double.toString(price)+"')");
		int points = (int) (price + 0.5);
		int cardPoints = getCardPoints(cardID);
		UpdateMethod("tarjeta", "`puntos`='" + cardPoints + points +"'", "`idTarjeta`='"+ cardID +"'");
	}

	public static void exchange(String nick, String prize) throws Exception {
		if(!checkUserExists(nick))
			throw new Exception("Usuario no existe");
		if(!checkUserHasCard(nick))
			throw new Exception("Usuario no tiene tarjeta");
		if(!checkPrizeExists(prize))
			throw new Exception("El premio no existe");

		int cardID = getCardIdFromUser(nick);
		int prizeID = getIdPremio(prize);

		int pointsCard = getCardPoints(cardID);
		int pointsPrize = getPointsPrize(prizeID);
		if(pointsCard < pointsPrize)
			throw new Exception("No hay puntos suficientes");
		int resultPoints = pointsCard - pointsPrize;
		InsertMethod("canjeos", "(`idPremio`, `idTarjeta`)", "('"+prizeID+ "' , '"+cardID +"')");
		UpdateMethod("tarjeta", "`puntos`='" + resultPoints +"'", "`idTarjeta`='"+ cardID +"'");
	}


	//*****************************PRIZE METHODS**********************************

	public static void createPrize(String concept, String quantity, int priceInPoints) throws Exception {
		if(checkPrizeExists(concept))
			throw new Exception("El premio ya existe");

		InsertMethod("premios", "(`concepto`, `cantidad`, `puntosNecesarois`)", "('"+concept+ "' , '"+quantity + "' , '"+Integer.toString(priceInPoints)+"')");
	}

	public static void editPrize(String concept, String newConcept, String quantity, int priceInPoints) throws Exception {
		if(!checkPrizeExists(concept))
			throw new Exception("El premio no existe");
		if(checkPrizeExists(newConcept))
			throw new Exception("El nuevo concepto ya esta en uso");
		int idPrize = getIdPremio(concept);
		UpdateMethod("`premios`", "`concepto`='"+newConcept+"', `cantidad`='"+quantity+"', `puntosNecesarois`='"+priceInPoints+"'", "`idPremios`='"+ idPrize +"'");
	}


	public static void deletePrize(String concept) throws Exception {
		if(!checkPrizeExists(concept))
			throw new Exception("El premio no existe");
		DeleteMethod("premios", "concepto", concept);
	}


	//*****************************INFORM METHODS**********************************

	public static String userInform(){
		connect();
		String query = "SELECT idCliente, idTarjeta, nick FROM "+DB_NAME+".cliente;";
		String result = "Id, idTarjeta, nick\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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

	public static String CardInform(){
		connect();
		String query = "SELECT cliente.idCliente, cliente.nick, tarjeta.nombreCompleto, tarjeta.telefono, tarjeta.direccion, tarjeta.email, tarjeta.puntos"
				+ "	FROM "+DB_NAME+".cliente"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ " ON cliente.idTarjeta=tarjeta.idTarjeta"
				+ "	ORDER BY cliente.idCliente;";
		String result = "IdCliente, Nick, nombreCompleto, Telefono, Dirección, Email, Puntos \n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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

	public static String ShopInform(){
		connect();
		String query = "SELECT * FROM "+DB_NAME+".tienda;";
		String result = "IdTienda, Nombre \n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
	
	public static String prizesInform(){
		connect();
		String query = "SELECT * FROM "+DB_NAME+".premios;";
		String result = "IdPremio, Concepto, Cantidad, Puntos Necesarios \n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
		String query = "SELECT compras.idCompras,tarjeta.nombreCompleto, tienda.nombre, compras.importe, compras.date"
				+ "	FROM "+DB_NAME+".compras"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ " ON compras.idTarjeta=tarjeta.idTarjeta"
				+ "	INNER JOIN "+DB_NAME+".tienda"
				+ "	ON compras.idTarjeta=tienda.idTienda"
				+ "	ORDER BY compras.idCompras;";
		String result = "IdCompras, userName, nombreTienda, importe, fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
	
	public static String purchasesInform(String nick){
		int idTarjeta = getCardIdFromUser(nick);
		connect();
		String query = "SELECT compras.idCompras, tarjeta.nombreCompleto, tienda.nombre, compras.importe, compras.date"
				+ "	FROM "+DB_NAME+".compras"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ " ON compras.idTarjeta=tarjeta.idTarjeta"
				+ "	INNER JOIN "+DB_NAME+".tienda"
				+ "	ON compras.idTarjeta=tienda.idTienda"
				+ "	WHERE tarjeta.idTarjeta = '"+idTarjeta+"'"
				+ "	ORDER BY compras.idCompras;";
		String result = "IdCompras, userName, nombreTienda, importe, fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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

	public static String exchangesInform(){
		connect();
		String query = "SELECT canjeos.idCanjeos, premios.concepto, tarjeta.nombreCompleto, canjeos.date"
				+ "	FROM "+DB_NAME+".canjeos"
				+ "	INNER JOIN "+DB_NAME+".premios"
				+ " ON canjeos.idPremio=premios.idPremios"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ "	ON canjeos.idTarjeta=tarjeta.idTarjeta"
				+ "	ORDER BY canjeos.idCanjeos;";
		String result = "idCanjeos, Concepto, Nombre Tarjeta, fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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

	public static String monthlyUser(String nick){
		int idUser = getCardIdFromUser(nick);
		String result = "";
		result = result + monthlyUser1(idUser);
		result = result + monthlyUser2(idUser);
		result = result + "Puntos en la tarjeta:\t" +	getCardPoints(idUser);
		return result;
	}

	public static String monthlyAdmin(){
		String result = "";
		result = result + monthlyAdmin1();
		result = result + monthlyAdmin2();
		return result;
	}
	
	private static String monthlyAdmin1(){
		connect();
		String query = "SELECT cliente.idTarjeta, cliente.nick, cliente.idTarjeta, SUM(compras.importe), tarjeta.puntos"
				+ "	FROM "+DB_NAME+".cliente"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ " ON cliente.idTarjeta=tarjeta.idTarjeta"
				+ "	INNER JOIN "+DB_NAME+".compras"
				+ " ON cliente.idTarjeta=compras.idTarjeta"
				+ " ORDER BY cliente.idTarjeta;";
		String result = "Info mensual:\n Id Usuario, Nick Usuario, idTarjeta, Dinero gastado al mes, Puntos a fin de mes Fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
	
	private static String monthlyAdmin2(){
		connect();
		String query = "SELECT canjeos.idTarjeta, canjeos.idCanjeos, premios.concepto, tarjeta.nombreCompleto, canjeos.date"
				+ "	FROM "+DB_NAME+".canjeos"
				+ "	INNER JOIN "+DB_NAME+".premios"
				+ " ON canjeos.idPremio=premios.idPremios"
				+ "	INNER JOIN "+DB_NAME+".tarjeta"
				+ "	ON canjeos.idTarjeta=tarjeta.idTarjeta"
				+ "	ORDER BY canjeos.idTarjeta;";
		String result = "Compras:\n Id Tarjeta, idCanjeo, Nick Usuario, idTarjeta, Dinero gastado al mes, Puntos a fin de mes Fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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

	private static String monthlyUser1(int idUser){
		connect();
		String query = "SELECT compras.idCompras, tienda.nombre, compras.importe, compras.date"
				+ "	FROM "+DB_NAME+".compras"
				+ "	INNER JOIN "+DB_NAME+".tienda"
				+ " ON compras.idTienda=tienda.idTienda"
				+ "	WHERE compras.idTarjeta = '"+idUser+"'"
				+ " ORDER BY compras.idCompras;";
		String result = "Compras:\n Id Compra, Nombre Tienda, Importe, Fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
	
	private static String monthlyUser2(int idUser){
		connect();
		String query = "SELECT canjeos.idcanjeos, premios.concepto, canjeos.date"
				+ "	FROM "+DB_NAME+".canjeos"
				+ "	INNER JOIN "+DB_NAME+".premios"
				+ " ON canjeos.idPremio=premios.idPremios"
				+ "	WHERE canjeos.idTarjeta = '"+idUser+"'"
				+ " ORDER BY canjeos.idcanjeos;";
		String result = "Canjes:\n Id Canje, Concepto, Fecha\n";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					result = result + columnValue + ", ";
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
				" VALUES "+VALUES+";";
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




	private static void UpdateMethod(String UPDATE, String SET, String WHERE) {
		connect();
		//Preparo la query UPDATE `tarjeta`.`cliente` SET `nick`='aitor' WHERE `idCliente`='11';

		String query = "UPDATE "+DB_NAME+"."+UPDATE+ " SET "+ SET+
				" WHERE " + WHERE + ";";
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
		String query = "DELETE FROM "+DB_NAME+"."+FROM+
				" WHERE "+ WHERE +"='"+IS+"';";
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

	private static String selectMethod(String SELECT, String FROM, String WHERE) {
		connect();
		String query = "SELECT "+ SELECT +" FROM "+DB_NAME+"."+FROM+" WHERE " + WHERE +";";
		String result = "";
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) 
				result = rs.getString(SELECT);
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
		String query = "SELECT * FROM "+DB_NAME+"."+"premios WHERE concepto='"+prize+"'";
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

	private static int getUserId(String nick){
		connect();
		String query = "SELECT idCliente FROM "+DB_NAME+"."+"cliente WHERE nick='"+nick+"'";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("idCliente");
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


	private static int getIdTienda(String Shop){
		connect();
		String query = "SELECT idTienda FROM "+DB_NAME+"."+"tienda WHERE nombre='"+Shop+"';";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("idTienda");
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

	private static int getIdPremio(String prize){
		connect();
		String query = "SELECT idPremios FROM "+DB_NAME+"."+"premios WHERE concepto='"+prize+"'";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("idPremios");
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


	private static int getPointsPrize(int prizeId){
		connect();
		String query = "SELECT puntosNecesarois FROM "+DB_NAME+"."+"premios WHERE idPremios='"+prizeId+"'";
		int result = 0;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				result = rs.getInt("puntosNecesarois");
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

	private static Card getCardData(int cardId){
		connect();
		String query = "SELECT * FROM "+DB_NAME+"."+"tarjeta WHERE idTarjeta='"+cardId+"'";
		Card result = new Card();
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				result.fullName = rs.getString("nombreCompleto");
				result.phone = rs.getString("telefono");
				result.adress = rs.getString("direccion");
				result.mail = rs.getString("email");
				result.points = rs.getInt("puntos");
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
}
