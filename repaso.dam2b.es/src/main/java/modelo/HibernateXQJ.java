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
		try {
			Session sesion = sf.openSession();
			Transaction t = sesion.beginTransaction();
			if (traspasaReligiones(sesion)==-1) {
				t.rollback();
			}else {
				t.commit();
			}

			sesion.close();

			xqc.close();
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static int traspasaReligiones(Session sesion) {
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
			
		} catch (XQException e) {
			
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

}
