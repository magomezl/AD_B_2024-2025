package MVC_ejercicio18.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	private static Conexion instancia;
	private static Connection con;
	
	public static Connection getCon() {
		return con;
	}
	private Conexion() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/empresa", "root", "toor");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 public static Conexion getInstance() {
		 if (instancia==null) {
			 instancia = new Conexion();
		 }
		 return instancia;
	 }
	 
	 public static void cerrarConexion() {
		 try {
			instancia.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	 }
	 
}
