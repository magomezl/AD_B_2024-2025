package jaxb;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Iterator;
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
	private static final String  DOCUMENTO_XML = "ejercicio16.xml";
	
	
	public static void main(String[] args) {
		cargarEnmemoriaXML();
//		menu();
		leerAutores();
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
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

//	public static void menu() {
//
//		Scanner sn = new Scanner(System.in);
//		boolean salir = false;
//		
//
//		while(!salir) {
//			System.out.println("\n1. Leer autores");
//			System.out.println("2. Eliminar autor");
//			System.out.println("3. Añadir autor");
//			System.out.println("4. Modificar autor. Entidad de trabajo y/o puesto");
//			System.out.println("5. Salir");
//			try {
//				System.out.println("Escribe una de las opciones: ");
//				switch(sn.nextInt()){
//				case 1:
//					System.out.println("LEYENDO AUTORES");
//					leer();
//					break;
//				case 2:
//					System.out.println("ELIMINAR AUTOR");
//					
//					System.out.println("indique la posición a consultar (valor numérico): ");
//					posicion = sn.nextInt();
//					int value;
//					leer(posicion);
//					break;
//				case 3:
//					System.out.println("AÑADIENDO AUTOR");
//					System.out.println("indique el valor del mes (tres letras): ");
//					mes = sn.next();
//					System.out.println("indique temperatura mínima: ");
//					minima = sn.nextInt();
//					System.out.println("indique temperatura máxima: ");
//					maxima = sn.nextInt();
//					System.out.println(escribir(mes, minima, maxima)?"Temperaturas añadidas correctamente":"ERROR: Temperaturas no añadidas");
//					break;
//				case 4:
//					System.out.println("MODIFICANDO VALOR EN POSICIÓN");
//					System.out.println("indique posición: ");
//					posicion = sn.nextInt();
//					System.out.println("indique el valor del mes (tres letras): ");
//					mes = sn.next();
//					System.out.println("indique temperatura mínima: ");
//					minima = sn.nextInt();
//					System.out.println("indique temperatura máxima: ");
//					maxima = sn.nextInt();
//					System.out.println(escribir(mes, posicion, minima, maxima)?"Valor modificado correctamente":"ERROR: Valor no modificado");
//					break;
//				case 5:
//					salir = true;
//					break;
//				default:
//					System.out.println("Solo números entre 1 y 5");
//					break;
//				}
//			}catch(InputMismatchException e) {
//				System.out.println("Debes escribir un número");
//				sn.next();
//			}
//		}
//		
//	}
	
}
