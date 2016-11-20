package com.diecinueve.TARJETA.Classes;

import java.sql.*;

public class Shop {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/EMP";

	//Credenciales Base de Datos
	static final String USER = "root";
	static final String PASS = "root";

	private Connection conn = null;
	private PreparedStatement stmt = null;

	//Metodo para iniciar una conexion a la Base de Datos
	private void connect(){
		try{

			//Conexion con Base de Datos
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);

		}
		catch(Exception e){
			//Hacer nada o sacar algun tipo de mensaje de error
		}
	}

	public void altaTienda(String nombreTienda){
		try{

			//Creo una conexion con la base de datos
			this.connect();

			//Preparo la query
			String query = "INSERT INTO Tiendas "+
					"VALUES ("+nombreTienda+")";
			stmt = conn.prepareStatement(query);

			//Ejecuto la query
			stmt.executeUpdate();

		}
		catch(Exception e){
			//Indicar que la tienda ya existe o que hay algun problema

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

	
	public void bajaTienda(String nombreTienda){
		try {

			//Creo una conexion con la base de datos
			this.connect();

			//Preparo la query
			String query = "DELETE * FROM Tiendas "+
					"WHERE nombre='"+nombreTienda+"'";
			stmt = conn.prepareStatement(query);

			//Ejecuto la query
			stmt.executeUpdate();

		}
		catch(Exception e){
			//Hacer nada
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
}
