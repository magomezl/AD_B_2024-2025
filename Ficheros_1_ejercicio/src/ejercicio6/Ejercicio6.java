package ejercicio6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Scanner;

import repaso.Ejercicio3;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;


public class Ejercicio6 {

	private static Scanner sn = new Scanner(System.in);
	private final static String DATOSFILEOUT = "FicheroPersonas_15_10_24.dat";
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
	
	
	public static Persona obtenerDatos() {
		Persona persona = new Persona();
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
	
	
	public static void escribirObjeto(Persona persona) {
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
	
	public static void leerObjetos() {
		
		try {
			ObjectInputStream oIS = new ObjectInputStream(new FileInputStream
					(new File(Ejercicio3.RUTADATOS+DATOSFILEOUT)));
			try {
				System.out.println("CONTENIDO DEL FICHERO " + DATOSFILEOUT);
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
	
	
	
	public static void main(String[] args) {
		
		inicializar();
		escribirObjeto(obtenerDatos());
		escribirObjeto(obtenerDatos());
		leerObjetos();
		try {
			oOS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
