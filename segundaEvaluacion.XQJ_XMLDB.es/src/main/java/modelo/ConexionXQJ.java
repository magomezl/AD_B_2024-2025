package modelo;



import javax.xml.xquery.*;
import javax.xml.namespace.QName;
import net.xqj.exist.ExistXQDataSource;


public class ConexionXQJ {
	private static  ConexionXQJ instancia;
	private static XQConnection conn;
	
	private ConexionXQJ(String user, String passwd) {
		try {
			XQDataSource xqs = new ExistXQDataSource();

			xqs.setProperty("serverName", "localhost");
			xqs.setProperty("port", "8080");
			xqs.setProperty("user", user);
			xqs.setProperty("password", passwd);
			conn = xqs.getConnection();

		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ConexionXQJ getInstancia(String user, String passwd) {
		if (instancia==null ) {
			instancia = new ConexionXQJ(user, passwd);
		}
		return instancia;
	}

	public static XQConnection getCon() {
		return conn;
	}
}
