package modelo;

import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

public class ConexionXMLDB {
	private static  ConexionXMLDB instancia;
	private static String user;
	private static String passwd;
	private static Collection col; 
	
	
	private ConexionXMLDB(String user, String passwd, String URICol) {
		this.user = user;
		this.passwd = passwd;
		try {
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			col = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc" + URICol,
					user, passwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static ConexionXMLDB getInstancia(String user, String passwd, String URICol) {
		if (instancia==null ) {
			instancia = new ConexionXMLDB(user, passwd, URICol);
		}
		return instancia;
	}

	public static Collection getCol() {
		return col;
	}
}
