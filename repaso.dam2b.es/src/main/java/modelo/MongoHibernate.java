package modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

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
//		pasadatosHibernateMongo();
		anadePracticantesReligionPais("España", "Islam", -1500000);

	}



	private static void anadePracticantesReligionPais(String paisNombre, String religionNombre, int cantidad) {
		//Este método, por su tamaño lo he dividido en tres métodos uno por cada DB
		anadePracticantesReligionPais_Hibernate(paisNombre, religionNombre, cantidad);
		anadePracticantesReligionPais_Mongo(paisNombre, religionNombre, cantidad);
		anadePracticantesReligionPais_Exist(paisNombre, religionNombre, cantidad);
		
				

				
				
	}



	private static void anadePracticantesReligionPais_Exist(String paisNombre, String religionNombre, int cantidad) {
		//ExistDB
		try {
			XQConnection conn = ConexionXQJ.getInstancia("admin", "toor").getCon();
			XQPreparedExpression expr; 
			XQResultSequence result;
						
			String idPais, idReligion;
			String query;
			Float practicantesNew;
			
			
			//Consulta para obtener el nombre el id del pais
			query = "doc('EjerciciosRepaso/religiones.xml')//paises/pais[@nombre='" +
					paisNombre + "']//@id_pais/string()";
			
			expr = conn.prepareExpression(query);
			result = expr.executeQuery();
			
			if (result.next()) {
				idPais =result.getAtomicValue();
			}else {
				System.out.println("No existe el pais");
				return;
			}
			
			
			//Consulta para obtener el nombre el id de la religión
			query = "doc('EjerciciosRepaso/religiones.xml')//religiones/religion[@denominacion='" +
					religionNombre + "']//@id_religion/string()";
			
			expr = conn.prepareExpression(query);
			result = expr.executeQuery();
			
			if (result.next()) {
				idReligion =result.getAtomicValue();
			}else {
				System.out.println("No existe la religión");
				return;
			}
			
			//Consulta para obtener el número de prácticantes
			query = "doc('EjerciciosRepaso/religiones.xml')//practica[@id_pais='" +
					idPais + "' and @id_religion='" + idReligion + "']/@practicantes/string()";
			
			expr = conn.prepareExpression(query);
			result = expr.executeQuery();
			
			if (result.next()) {
				practicantesNew = Float.valueOf(result.getAtomicValue()) + cantidad;
				if (practicantesNew > 0) {
					//Consulta para modificar el número de practicantes
					query =	"update value doc('EjerciciosRepaso/religiones.xml')//practica[@id_pais='" +
							idPais + "' and @id_religion='" + idReligion + "']/@practicantes with '" +
							practicantesNew + "'";
				}else {
					//Consulta para eliminar la religión en el pais si el número de practicantes es inferior a 0
					query = "update delete doc('EjerciciosRepaso/religiones.xml')//practica[@id_pais='" +
							idPais + "' and @id_religion='" + idReligion + "']";
				}
				XQExpression xqe = conn.createExpression();
				xqe.executeCommand(query);
			}
			
			conn.close();
		} catch (XQException e) {
			e.printStackTrace();
		}
		
	}



	private static void anadePracticantesReligionPais_Mongo(String paisNombre, String religionNombre, int cantidad) {
		
		Document criterio= new Document()
			.append("nombrePais", paisNombre)
			.append("religiones.nombreReligion", religionNombre);
		
		//Obtener los practicantes y sumar, si es menor que 0, borrar, si no modificar
		Document documento = mDB.getCollection("datos").find(criterio).first();
		List<Document> listaDocReligionesPais = new ArrayList<Document>();
		//Toma los documentos de la propiedad religiones que es un arrey de documentos
		listaDocReligionesPais = (List<Document>) documento.get("religiones");
		Document ReligionABorrar = new Document();
		for (Document docReli: listaDocReligionesPais) {
			if (docReli.getString("nombreReligion").equalsIgnoreCase(religionNombre)) {
				Double practicantesNew = docReli.getDouble("practicantes") + cantidad;
				if (practicantesNew>0) {
					docReli.put("practicantes", practicantesNew);
				}else {
					ReligionABorrar = docReli;
				}
			}
		}

		listaDocReligionesPais.remove(ReligionABorrar);
		documento.remove("religiones");
		documento.append("religiones", listaDocReligionesPais);
		mDB.getCollection("datos").updateOne(criterio, new Document("$set", documento));
		
		
	}



	private static void anadePracticantesReligionPais_Hibernate(String paisNombre, String religionNombre, int cantidad) {
	
		Session sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		String hqlReligionesPais = "from Practicareligiones where paises.nombre='" + paisNombre +"' and religiones.nombre='" + religionNombre +"'";
		TypedQuery<Practicareligiones> consultaReligionesPais = sesion.createQuery(hqlReligionesPais, Practicareligiones.class);
		Practicareligiones prP =  consultaReligionesPais.getResultList().get(0);
		if (prP!=null) {
			prP.setPracticantes(prP.getPracticantes()+cantidad);
			sesion.update(prP);
			if (prP.getPracticantes()<=0) {
				sesion.delete(prP);
			}
			t.commit();
		}
		sesion.close();
	}



	private static void pasadatosHibernateMongo() {
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
