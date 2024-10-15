package repaso;

import java.util.Properties;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Ejercicio7 {
	private static Properties conf = new Properties();
	public final static String FICHEROTRABAJO = "configuracion.props";
	
	public static void main(String[] args) {
		creaConfiguracion();
		System.out.println("DOCUMENTO ORIGINAL");
		leerConfiguracion();
		modificaConfiguracion();
		System.out.println("DOCUMENTO MODIFICADO");
		leerConfiguracion();
	}

	private static void modificaConfiguracion() {
		Scanner sc = new Scanner (System.in);

		System.out.println("MODIFICANDO CONFIGURACIÓN");
		System.out.println("user: ");
		String usuario = sc.nextLine();
		System.out.println("password: ");
		String password = sc.nextLine();
		System.out.println("server: ");
		String servidor = sc.nextLine();
		System.out.println("valor a sumar al puerto: ");
		int puerto = Integer.valueOf(sc.nextLine()) + 1;
		System.out.println("nuevo mes para la fecha: ");
		int mes  = Integer.valueOf(sc.nextLine());
		try {
			conf.load(new FileInputStream(Ejercicio3.RUTADATOS+FICHEROTRABAJO));
			conf.setProperty("user", usuario);
			conf.setProperty("password", password);
			conf.setProperty("server", servidor );
			conf.setProperty("port", String.valueOf(puerto));
			//Tomo la propiedad (String) la paso a parseo a LocalDate, le modifico el mes (wirhMonth) y la transformo en String
			//TODO Fomato de fecha
			conf.setProperty("date", String.valueOf(
					LocalDate.parse(conf.getProperty("date")).withMonth(mes))
					);
			//Cambio el valor del power
			conf.setProperty("power", String.valueOf(!(Boolean.valueOf(conf.getProperty("power")))));
			conf.store(new FileOutputStream(Ejercicio3.RUTADATOS+FICHEROTRABAJO), "Fichero de configuración");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void leerConfiguracion() {
		try {
			conf.load(new FileInputStream(Ejercicio3.RUTADATOS+FICHEROTRABAJO));
			conf.list(System.out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void creaConfiguracion() {
		conf.setProperty("user", "usuario");
		conf.setProperty("password", "micontrasena");
		conf.setProperty("server", "localhost");
		conf.setProperty("port", "3306");
		conf.setProperty("date", "2022-11-08");
		conf.setProperty("power", "true");
		try {
			conf.store(new FileOutputStream(Ejercicio3.RUTADATOS+FICHEROTRABAJO), "Fichero de configuración");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
