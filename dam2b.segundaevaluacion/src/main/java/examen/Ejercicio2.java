package examen;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import clasesHibernate.Provincias;
import clasesHibernate.Tipos;

public class Ejercicio2 {

	public static void creaRecursoHosteleriaMySQL_ExistDB() {
		
		
		try {
			//Conexión con ExistDB
			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.getDeclaredConstructor().newInstance();
			DatabaseManager.registerDatabase(database);
			Collection colPadre = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/",
					"admin", "toor");
			CollectionManagementService serCM = (CollectionManagementService) colPadre.getService("CollectionManagementService", "1.0");
			Collection colTrabajo = serCM.createCollection("HosteleriaCyL");
	
			
			//Conexion MySQL
			Configuration cfg = new Configuration().configure();
			SessionFactory sf = cfg.buildSessionFactory();
			
			Session sesion = sf.openSession();
			
			List<Provincias> provincias = sesion.createQuery("from Provincias", Provincias.class).getResultList();
			StringBuilder contenido = new StringBuilder();
			for(Provincias prov: provincias) {
				//Creo recurso con nombre de la provincia
				Resource recurso = colTrabajo.createResource(prov.getNombre()+".xml", XMLResource.RESOURCE_TYPE);
				contenido.append("<provincia nombre='" + prov.getNombre()+"'>");
				List<Tipos> tipos = sesion.createQuery("from Tipos", Tipos.class).getResultList();
				for(Tipos tip: tipos) {
					Long numero = sesion.createQuery("select count(AL) from Alojamientosturismorural AL where "
							+ " AL.provincias.nombre = :provincia and AL.tipos.descripcion = :tipo", Long.class)
							.setParameter("provincia", prov.getNombre())
							.setParameter("tipo", tip.getDescripcion())
							.getSingleResult();
					contenido.append("<alojamiento tipo='" + tip.getDescripcion() + "' num_establecimientos='" + numero + "'/>");
				}
				contenido.append("</provincia>");
				recurso.setContent(contenido);
				colTrabajo.storeResource(recurso);
				recurso = null;
				contenido.setLength(0);
			}
			sesion.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
