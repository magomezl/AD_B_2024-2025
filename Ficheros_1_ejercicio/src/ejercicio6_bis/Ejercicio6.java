package ejercicio6_bis;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import repaso.Ejercicio3;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;


public class Ejercicio6 {

	private static Scanner sn = new Scanner(System.in);
	private final static String DATOSFILEOUT = "FicheroSerializaPersonas.dat";
	private final static String DATOSFILEOUT_BIS = "datosPersonasv2.dat";
	private static ObjectOutputStream oOS;
	
	public static void inicializar() {
		try {
			File file = new File(Ejercicio3.RUTADATOS+DATOSFILEOUT);
			if (file.exists() && file.length()>0) {
				oOS = new MyObjectOutputStream(new FileOutputStream(file, true));
			}else {
				oOS = new ObjectOutputStream(new FileOutputStream(file, true));
			}
		} catch (FileNotFoundException e) {
			System.out.println("No se encuentra el fichero");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de entrada/salida");
			e.printStackTrace();
		}
	}
	
	
	public static Persona_v1 obtenerDatos() {
		Persona_v1 persona = new Persona_v1();
		System.out.println("DATOS DE USUARIO:");
		System.out.println("\tNombre:");
		persona.setNombre(new StringBuilder(sn.nextLine()));
		System.out.println("\tPrimer Apellido:");
		persona.setApellido1(new StringBuilder(sn.nextLine()));
		System.out.println("\tSegundo Apellido:");
		persona.setApellido2(new StringBuilder(sn.nextLine()));
		System.out.println("\tFecha de Nacimiento (dd-MM-yyyy):");
		try {
			persona.setNacimiento(new SimpleDateFormat("dd-MM-yyyy").parse(sn.nextLine()));
		} catch (ParseException e) {
			System.out.println("Error de formato de fecha");
			e.printStackTrace();
		}
		return persona;
	}
	
	
	public static void escribirObjeto(Persona_v1 persona) {
		try {
			oOS.writeObject(persona);
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
		}
	}
	
	public static void leerObjetos(String ficheroALeer) {
		
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream
					(new File(Ejercicio3.RUTADATOS+ficheroALeer)));
			try {
				System.out.println("CONTENIDO DEL FICHERO " + ficheroALeer);
				while(true) {
					System.out.println((Persona) oIS.readObject());
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Error de lectura");
				e.printStackTrace();
			}finally {
				oIS.close();
			}
		} catch (EOFException e) {
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void leev1Escribev2() {
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream
					(new File(Ejercicio3.RUTADATOS+DATOSFILEOUT)));
			ObjectOutputStream oOS = new ObjectOutputStream(new FileOutputStream(new File(Ejercicio3.RUTADATOS+DATOSFILEOUT_BIS), true));
			Persona_v1 pOrigen = new Persona_v1();
			Persona_v2 pDestino = new Persona_v2();
			try {
				while(true) {
					pOrigen = (Persona_v1) oIS.readObject();
					pDestino.setApellido1(pOrigen.getApellido1());
					pDestino.setApellido2(pOrigen.getApellido2());
					pDestino.setNombre(pOrigen.getNombre());
					pDestino.setEdad(LocalDateTime.now().getYear()-pOrigen.getNacimiento().getYear());
					oOS.writeObject(pDestino);
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Error de lectura");
				e.printStackTrace();
			}finally {
				oIS.close();
			}
		} catch (EOFException e) {
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		inicializar();
//		escribirObjeto(obtenerDatos());
//		escribirObjeto(obtenerDatos());
//		
//		leev1Escribev2();
		leerObjetos(DATOSFILEOUT);
		leerObjetos(DATOSFILEOUT_BIS);
		try {
			oOS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
