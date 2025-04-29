package modelo;


import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.Node;

import clasesHibernate.Religiones;

public class HibernateXQJ {
	private static Configuration cfg = new Configuration().configure();
	private static SessionFactory sf = cfg.buildSessionFactory();
	
	private static XQConnection xqc = ConexionXQJ.getInstancia("admin", "toor").getCon();
	
	
	public static void main(String[] args) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		try {

			String queryRel = "for $r in doc(\"EjerciciosRepaso/religiones.xml\")/geografia/religiones/religion  \r\n"
					+ "return <religion denominacion='{$r/@denominacion}'/>";


			XQPreparedExpression exprRel = xqc.prepareExpression(queryRel);
			XQResultSequence resultRel = exprRel.executeQuery();
			while(resultRel.next()) {
				Node nodo = resultRel.getNode();
				Religiones religion = new Religiones(nodo.getAttributes().getNamedItem("denominacion").getNodeValue());
				sesion.persist(religion);
			}
			t.commit();
			sesion.close();
		} catch (XQException e) {
			t.rollback();
			e.printStackTrace();
		}

	}

}
