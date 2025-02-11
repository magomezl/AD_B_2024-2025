package examen;

import java.util.Iterator;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import clasesHibernate.Alojamientosturismorural;
import clasesHibernate.Provincias;
import clasesHibernate.Tipos;

public class Ejercicio1 {
	private static Session sesion;
	public static void migraDatosAlojamientosTurismoRurasMongoDB_MySQL() {
		//Abrir conexión con MySQL
		Configuration cfg = new Configuration().configure();
		SessionFactory sf = cfg.buildSessionFactory();
		
		
		// Abrir conexión con MongoDB
		MongoClient cliente = MongoClients.create();
		MongoDatabase db = cliente.getDatabase("TurismoCyL");
		
		//COnsulta dobre MongoDB
		FindIterable<Document> fit = db.getCollection("Turismo").find(Filters.eq("establecimiento", "Alojam. Turismo Rural"));
		Iterator<Document> it = fit.iterator();
		//Abro sesión con MySQL
		sesion = sf.openSession();
		while(it.hasNext()) {
			Transaction t = sesion.beginTransaction();
			Document establecimientoACT = it.next();
			gestionaTipo(establecimientoACT.getString("tipo"));
			
			Alojamientosturismorural ATR = new Alojamientosturismorural(
					sesion.get(Tipos.class, gestionaTipo(establecimientoACT.getString("tipo"))),
					sesion.get(Provincias.class, gestionaProvincia(establecimientoACT.getString("provincia"))),
					establecimientoACT.getString("categoria"),
					establecimientoACT.getString("nombre"),
					establecimientoACT.getString("direccion"),
					establecimientoACT.getString("c_postal"),
					establecimientoACT.getString("localidad"),
					establecimientoACT.getString("telefono_1"));
			sesion.persist(ATR);
			t.commit();
		}
		sesion.close();
	}

	private static int gestionaTipo(String description) {
		Tipos tipo = null;
		try {
			tipo = sesion.createQuery("from Tipos where descripcion = :decrip", Tipos.class)
				.setParameter("decrip", description)
				.getResultList()
				.get(0);
			return tipo.getId();
		}catch(IndexOutOfBoundsException e) {
			tipo = new Tipos(description);
			sesion.persist(tipo);
			return gestionaTipo(description);
		}
	}
	
	
	private static int gestionaProvincia(String nombre) {
		Provincias provincia = null;
		try {
			provincia = sesion.createQuery("from Provincias where nombre = :name", Provincias.class)
				.setParameter("name", nombre)
				.getResultList()
				.get(0);
			return provincia.getId();
		}catch(IndexOutOfBoundsException e) {
			provincia = new Provincias(nombre);
			sesion.persist(provincia);
			return gestionaProvincia(nombre);
		}
	}

	
	

}
