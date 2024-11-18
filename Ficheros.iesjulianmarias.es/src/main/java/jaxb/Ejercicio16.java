package jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import ficheros.Utilidades;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jaxb.clasesEjercicio16.Autor;
import jaxb.clasesEjercicio16.Examen;

public class Ejercicio16 {
	private static JAXBContext jC;
	private static Examen examen;
	private static ArrayList<Autor> autores;
	
	private static final String  DOCUMENTO_XML = "ejercicio16.xml";
	
	
	public static void main(String[] args) {
		cargarEnmemoriaXML();
		menu();
		persistirEnFicheroXML();
	}
	
	private static void leerAutores() {
		Iterator<Autor> it = examen.getListaAutores().iterator();
		while(it.hasNext()) {
			System.out.println(it.next());
		}
	}

	private static void persistirEnFicheroXML() {
		
		try {
			Marshaller Ms = jC.createMarshaller();
			Ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Ms.marshal(examen, new File(Utilidades.getRutadatos()+ DOCUMENTO_XML));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Transformamos el documento XML en el javaBean creado y etiquetado. Unmarshalling
	 */
	private static void cargarEnmemoriaXML() {
		try {
			jC = JAXBContext.newInstance(Examen.class);
			Unmarshaller uM = jC.createUnmarshaller();
			examen = (Examen) uM.unmarshal(new File(Utilidades.getRutadatos()+ DOCUMENTO_XML));
			autores = examen.getListaAutores();
			
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
		
	}

	public static void menu() {
		Scanner sc = new Scanner(System.in);
		Scanner scI = new Scanner(System.in);
		boolean salir = false;
		while(!salir) {
			System.out.println("\n1. Leer autores");
			System.out.println("2. Eliminar autor");
			System.out.println("3. Añadir autor");
			System.out.println("4. Modificar autor. Entidad de trabajo y/o puesto");
			System.out.println("5. Salir");
			try {
				System.out.println("Escribe una de las opciones: ");
				switch(sc.nextInt()){
				case 1:
					System.out.println("LEYENDO AUTORES");
					leerAutores();
					
					break;
				case 2:
					System.out.println("ELIMINAR AUTOR");
					System.out.println("Indique el id del autor a eliminar: ");
					System.out.println(borrarAutor(scI.nextLine())?"El autor se eliminó con éxito":"El autor no existe");
					break;
				case 3:
					System.out.println("AÑADIENDO AUTOR");
					
					System.out.println(anadirAutor(pedirDatosAutor())?"Autor añadido con éxito":"Autor NO añadido");
					break;
				case 4:
					System.out.println("MODIFICANDO DATOS DE AUTOR");
					System.out.println("Indique el id del autor a modificar: ");
					String idAutor = scI.nextLine();
					System.out.println("Nueva Entidad de Trabajo: ");
					String newET = scI.nextLine();
					System.out.println("Nuevo Puesto: ");
					String newP = scI.nextLine();
					System.out.println(modificarAutor(idAutor, newET, newP)?"Autor modificado con éxito":"Autor NO modificado");
					break;
				case 5:
					salir = true;
					break;
				default:
					System.out.println("Solo números entre 1 y 5");
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("Debes escribir un número");
				sc.next();
			}catch (NoSuchElementException e) {
				System.out.println("ERROR");
				
			}
		}
		
	}
	
	private static boolean modificarAutor(String idAutor, String newET, String newP) {
		
		Autor autorModificar = localizarAutor(idAutor);
		autorModificar.setEntidadTrabajo(newET);
		autorModificar.setPuesto(newP);
		if (autores.set(localizarLugarAutor(idAutor), autorModificar)!=null)
			return true;
		return false;
	}

	/**
	 * 
	 * @param idAutor
	 * @return posición del autor cuyo id se pasa como parámetro o -1 en caso se no existir
	 */
	private static int localizarLugarAutor(String idAutor) {
		Iterator<Autor> it = autores.iterator();
		Autor autorEncurso;
		int i = 0;
		while(it.hasNext()) {
			autorEncurso = it.next();
			if (autorEncurso.getId().equals(idAutor)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	/**
	 * 
	 * @param id
	 * @return true si se elimina el elemento y false si no se elimnina
	 */
	private static boolean borrarAutor(String id) {
		return autores.remove(localizarAutor(id));
		
	}

	private static Autor localizarAutor(String id) {
		Iterator<Autor> it = autores.iterator();
		Autor autorEncurso;
		while(it.hasNext()) {
			autorEncurso = it.next();
			if (autorEncurso.getId().equals(id)) {
				return autorEncurso;
			}
		}
		return null;
	}

	private static boolean anadirAutor(Autor autor) {
		return autores.add(autor);
	}

	private static Autor pedirDatosAutor() {
		Scanner sn = new Scanner(System.in);
		Autor autor = new Autor();
		
		System.out.println("Identificador: ");
		autor.setId(sn.nextLine());
		
		System.out.println("Nombre: ");
		autor.setNombre(sn.nextLine());
		System.out.println("Primer Apellido: ");
		autor.setApellido1(sn.nextLine());
		System.out.println("Segundo Apellido: ");
		autor.setApellido2(sn.nextLine());
		System.out.println("Entidad de Trabajo: ");
		String eT = sn.nextLine();
		autor.setEntidadTrabajo(eT);
		System.out.println("Puesto: ");
		String p = sn.nextLine();
		autor.setPuesto(p);
		
		return autor;
	}
	
}
