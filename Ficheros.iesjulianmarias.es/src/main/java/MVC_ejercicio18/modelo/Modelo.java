package MVC_ejercicio18.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import ficheros.Utilidades;

public class Modelo {
//	private static Connection con;
	private static final String DBSQLite = "dptos_sqlite.dat";
	
//	public static void main(String[] args) {
//		establecerConexion_SQLite();
//		listarDptos();
//		System.out.println((anadirDpto("Marketing", "Segovia")==1)?"Departamento añadido correctamente":"NO se añadió el departamento. Revise el código del departamento");
//		System.out.println("Despues de añadir Marketing en Segovia");
//		listarDptos();
//		System.out.println((modificarDpto(1, "Finanzas", "Pamplona")!=0)?"Departamento modificado correctamente":"El departamento no se modificó");
//		System.out.println("Despues de modificar Finanzas a Pamplona");
//		listarDptos();
//		System.out.println((borrarDpto(1)!=0)?"Departamento modificado correctamente":"El departamento no se modificó");
//		System.out.println("Despues de borrar Finanzas (23) de Pamplona");
//		listarDptos();
//		
//		
////		anadirEmpleado("Aurora", "Gómez", "López", "RRHH");
//		
//		try {
//			con.close();
//		} catch (SQLException e) {;
//			e.printStackTrace();
//		}
//		con = null;
//
//	}

	
//	private static int anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
//		try {
//			con.setAutoCommit(false);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		PreparedStatement sentencia = con.prepareStatement("SELECT dept_no FROM departamentos WHERE dnombre = ? ");
//		try {
//			sentencia.setString(1, dpto);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ResultSet resultado;
//		try {
//			resultado = sentencia.executeQuery();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		try {
////			//resultado.next();
////		} catch (SQLException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		
//		
//	}
	
	private static int anadirDpto(String dptoNom, String dptoLoc) {
		try {
			PreparedStatement sentencia = Conexion.getInstance().getCon().prepareStatement(
					"INSERT INTO departamentos (dnombre, loc) VALUES (?, ?)");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			return sentencia.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException  e) {
			return 0;
		} catch (SQLException e) {
			return -1;
		}
	}

	public static int modificarDpto(Integer dptoNum, String dptoNom, String dptoLoc) {
		PreparedStatement sentencia;
		try {
			sentencia = Conexion.getInstance().getCon().prepareStatement("UPDATE departamentos SET dnombre = ?, loc = ? WHERE dept_no = ?");
			sentencia.setString(1, dptoNom);
			sentencia.setString(2, dptoLoc);
			sentencia.setInt(3, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public static int borrarDpto(Integer dptoNum) {
		PreparedStatement sentencia;
		try {
			sentencia = Conexion.getInstance().getCon().prepareStatement("DELETE FROM departamentos WHERE dept_no = ?");
			sentencia.setInt(1, dptoNum);
			return sentencia.executeUpdate();
		} catch (SQLException e) {
			return 0;
		}
	}

	private static void listarDptos() {
		PreparedStatement sentencia =null;
		ResultSet resultado = null;
		try {
			sentencia = Conexion.getInstance().getCon().prepareStatement("SELECT * FROM departamentos");
			resultado = sentencia.executeQuery();
			while (resultado.next()) {
				System.out.println("\t" + resultado.getInt(1) + "\t" + resultado.getString(2) +
						"\t" + resultado.getString(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				sentencia.close();
				resultado.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	private static void establecerConexion_SQLite() {
//		try {
//			Class.forName("org.sqlite.JDBC");
//			con = DriverManager.getConnection("jdbc:sqlite:" + Utilidades.getRutadatos()+DBSQLite);
//			con.createStatement().execute("create table departamentos (dept_no integer not null primary key autoincrement, "
//					+ " dnombre varchar(15), loc varchar(15))");
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	

}
