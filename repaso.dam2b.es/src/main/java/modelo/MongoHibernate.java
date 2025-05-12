package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import clasesHibernate.Ciudades;
import clasesHibernate.Idiomas;
import clasesHibernate.Paises;
import clasesHibernate.Practicareligiones;
import clasesHibernate.Religiones;

public class MongoHibernate {
	private static Configuration cfg = new Configuration().configure();
	private static SessionFactory sf = cfg.buildSessionFactory();
	
	private static MongoClient cliente = MongoClients.create();;
	private static MongoDatabase mDB = cliente.getDatabase("geografia");
	
	
	public static void main(String[] args) {
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		
		String hqlPaises= "from Paises";
		Query query = sesion.createQuery(hqlPaises, Paises.class);
		List<Paises> paises = query.getResultList();
		for(Paises pais: paises) {
			//Recorro este (origen Set de objetos Idiomas)
			Set<Idiomas> idiomasP = pais.getIdiomases();
			//Para llenar este (destino, Array de String para pasarselas a la propiedad idioma del documento bson (Mongo)
			List<String> ListaIdiomasDoc = new ArrayList<String>();
			for(Idiomas id: idiomasP) {
				ListaIdiomasDoc.add(id.getIdioma());
			}
			//Recorro este (origen Set de objetos Ciudades)
			Set<Ciudades> ciudadesP =  pais.getCiudadeses();
			//Para llenar este (destino, Array de documento para pasarselas a la propiedad ciudades del documento bson (Mongo)
			List<Document> listaCiudadesDoc = new ArrayList<Document>();
			for(Ciudades city: ciudadesP) {
				Document doc = new Document()
						.append("nombreCiudad", city.getNombre())
						.append("num_habitantes", city.getNumHabitantes());
				listaCiudadesDoc.add(doc);
			}
			
			//Recorro este (origen Set de objetos Practicareligiones)
			Set<Practicareligiones> religionesP =  pais.getPracticareligioneses();
			//Para llenar este (destino, Array de documento para pasarselas a la propiedad religiones del documento bson (Mongo)
			List<Document> listaReligionesDoc = new ArrayList<Document>();
			for(Practicareligiones reli: religionesP) {
				Document doc = new Document()
						.append("nombreReligion", reli.getReligiones().getNombre() )
						.append("practicantes", reli.getPracticantes());
				listaReligionesDoc.add(doc);
			}
			
			Document docPais = new Document()
					.append("nombrePais", pais.getNombre())
					.append("num_habitantes", pais.getNumHabitantes())
					.append("idioma", ListaIdiomasDoc)
					.append("ciudades", listaCiudadesDoc)
					.append("religiones", listaReligionesDoc);
			mDB.getCollection("datos").insertOne(docPais);		
		}
	}
}
