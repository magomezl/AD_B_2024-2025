package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;

public class Modelo {
	private static MongoClient cliente;
	private static MongoDatabase db; 

	public static void main(String[] args) {
		conexionLocal();
//		conexionRemota();
		
//		ArrayList<String> personajesLBN = new ArrayList<String>(Arrays.asList("David", "Mae"));
//		ArrayList<String> personajesELAR = new ArrayList<String>(Arrays.asList("David", "Mae"));
//		anadirLibroAutorEmbebido("La conjura de los necios", "novela", "John Kennedy Toole", "Americana", 1937, personajes);
		
//		anadirLibroAutorReferenciado("La conjura de los necios", "novela", "John Kennedy Toole", "Americana", 1937, personajes);
		
		
//		anadirLibroAutorReferenciado("La biblia de neón", "novela", "John Kennedy Toole", "Americana", 1937, personajesLBN);
//		anadirLibroAutorReferenciado("El libro de los amores ridículos", "novela", "Milan Kundera", "Checa", 1929, personajesELAR);
		
//		mostrarLibros("ciencia ficción");
	
		actualizarPrecios(-50);
		System.out.println("/n/n/nCON SUBIDA");
		
		cliente.close();
	}

	
	private static void actualizarPrecios(double porcentaje) {
		db.getCollection("libros").updateMany(Filters.exists("precio", true), Updates.mul("precio", 1+porcentaje/100)); 
	}


	private static void ponerPrecios() {
		db.getCollection("libros").updateMany(Filters.exists("precio", false), 
				Updates.set("precio", new Random().nextDouble(25-10+1)+10));
		
	}
	

	private static void mostrarLibros() {
		FindIterable<Document> fitL =  db.getCollection("libros").find();
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}

	private static void mostrarLibros(String genero) {
		FindIterable<Document> fitL =  db.getCollection("libros").find(Filters.eq("genero", genero));
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}
	
	private static void mostrarLibros(String titulo, String nombreAutor ) {
		HashSet<ObjectId> idsAutor = new HashSet<ObjectId>();
		
		//Obtener el ObjectId del autor que puede estar más de una vez
		FindIterable<Document> fitA =  db.getCollection("autores").find(Filters.eq("nombre", nombreAutor));
		Iterator<Document> itA = fitA.iterator();
		while(itA.hasNext()) {
			idsAutor.add(itA.next().getObjectId("_id"));
		}
		
		/**
		FindIterable<Document> fitL =  db.getCollection("libros").find(
				Filters.or(
						Filters.and(
								Filters.eq("titulo", titulo),
								Filters.eq("autor.nombre", nombreAutor)
						),
						Filters.and(
								Filters.eq("titulo", titulo),
								Filters.in("autor", idsAutor)
						)
				)
		);
		**/
		FindIterable<Document> fitL =  db.getCollection("libros").find(
				Filters.and(
						Filters.eq("titulo", titulo),
						Filters.or(
								Filters.eq("autor.nombre", nombreAutor),
								Filters.in("autor", idsAutor)
						)
				)
		);
		
		Iterator<Document> itL = fitL.iterator();
		while(itL.hasNext()) {
			System.out.println(itL.next().toString());
		}
	}

	
	
	
	private static void anadirLibroAutorEmbebido(String titulo, String genero, String nombreAutor, 
			String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		
		Document doc = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", 
						new Document()
							.append("nombre", nombreAutor)
							.append("nacionalidad", nacionalidadAutor)
							.append("nacimiento", nacimientoAutor)
							
				)
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(doc);
	}

	private static void anadirLibroAutorReferenciado(String titulo, String genero, String nombreAutor, 
			String nacionalidadAutor, int nacimientoAutor, ArrayList<String> personajes) {
		Document docAutor = null;
		ObjectId id;
		docAutor = db.getCollection("autores").find(Filters.and(
				Filters.eq("nombre", nombreAutor),
				Filters.eq("nacionalidad", nacionalidadAutor),
				Filters.eq("nacimiento", nacimientoAutor))).first();
		
		//Compruebo si existe el autor buscado en la coleccion autores
		if (docAutor==null) {
			docAutor = new Document()
					.append("nombre", nombreAutor)
					.append("nacionalidad", nacionalidadAutor)
					.append("nacimiento", nacimientoAutor);
			db.getCollection("autores").insertOne(docAutor);
		}
		id = docAutor.getObjectId("_id");
				
		Document docLibro = new Document()
				.append("titulo", titulo)
				.append("genero", genero)
				.append("autor", id)
				.append("personajes", personajes);
		db.getCollection("libros").insertOne(docLibro);
	}

	
	

	private static void conexionRemota() {
		String connectionString = "mongodb+srv://root:toor@cluster0.pgow3.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("admin");
                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
		
	}

	
	
	private static void conexionLocal() {
		cliente = MongoClients.create();
		db = cliente.getDatabase("biblioteca");
	}
	
	

}
