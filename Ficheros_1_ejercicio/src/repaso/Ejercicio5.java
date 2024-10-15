package repaso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio5 {
	private static RandomAccessFile RFile;
	private final static String DATOSFILEOUT = "FicheroAleatorio.dat";
	private final static int TAMANIO = 4;

	public static void main(String[] args) {
		try {
			RFile = new RandomAccessFile(new File(Ejercicio3.RUTADATOS + DATOSFILEOUT), "rw");
			menu();
			RFile.close();
		} catch (IOException e) {
			System.out.println("Error apertura/creación fichero");
			e.printStackTrace();
		}
	}

	/**
	 * escribe números del 1 al 100 en un fichero de acceso aleatorio
	 * @return número de elementos escritos o 0 en caso de error
	 */
	public static int escribir() {
		try {
			int i;
			for(i=1; i<=100; i++)
				RFile.writeInt(i);
			return i;
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * lee el contenido de un fichero de acceso aleatorio 
	 * @return número de elementos leidos o 0 en caso de error
	 */
	public static int leer() {
		
		try {
			int i=0;
			RFile.seek(0);
			while(RFile.getFilePointer()!=RFile.length()) {
				System.out.println(RFile.readInt());
				i++;
			}
			return i;
		} catch (IOException e) {
			System.out.println("Error de lectura");
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * muestra por consola el valor almacenado en una determinada posición
	 * @param lugar posición a la que se accederá para mostrar el valor almacenado
	 * @return valor almacenado en la posición pasada como parámetro
	 *          -1 en caso de error
	 */
	public static int consultar(int lugar) {
		try {
			long posicion = (lugar-1)*TAMANIO;
			if (posicion>=RFile.length() || lugar<=0) {
				System.out.println("No existe ningún valor en esa posición");
				return -1;
			}
			RFile.seek(posicion);
			return RFile.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error de lectura");
			return -1;
		}
	}
	
	/**
	 * añade un valor al final del fichero
	 * @param valor número a añadir
	 * @return true si el valor se añade correctamente y false si ocurre un error
	 */
	public static boolean anadirFinal(int valor) {
		try {
			RFile.seek(RFile.length());	
			RFile.writeInt(valor);
			return true;
		} catch (IOException e) {
			System.out.println("Error de escritura");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * modifica el valor de la posición lugar con el número indicado por el paramétro valor
	 * @param valor nuevo valor
	 * @param lugar posición a modificar
	 * @return true si el valor se modifica correctamente y false si ocurre un error
	 */
	public static boolean modificarValor(int valor, int lugar) {
		
		try {
			long posicion = (lugar-1)*TAMANIO;
			if (posicion>=RFile.length() || lugar<=0) {
				System.out.println("No existe ningún valor en esa posición");
				return false;
			}
			RFile.seek(posicion);
			RFile.writeInt(valor);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * muestra el menú de opciones
	 */
	public static void menu() {

		Scanner sn = new Scanner(System.in);
		boolean salir = false;
		int posicion, valor;

		while(!salir) {
			System.out.println("\n1. Leer fichero");
			System.out.println("2. Consultar una posición");
			System.out.println("3. Añadir al final");
			System.out.println("4. Modificar valor");
			System.out.println("5. Salir");
			try {
				System.out.println("Escribe una de las opciones: ");
				switch(sn.nextInt()){
				case 1:
					System.out.println("LEYENDO EL CONTENIDO DEL FICHERO");
					leer();
					break;
				case 2:
					System.out.println("CONSULTANDO POSICIÓN DEL FICHERO");
					System.out.println("indique la posición a consultar (valor numérico): ");
					posicion = sn.nextInt();
					int value;
					if ((value=consultar(posicion))!=-1){
						System.out.println("Valor en la posición " + posicion + ": " + value);
					}
					break;
				case 3:
					System.out.println("AÑADIENDO ELEMENTO AL FINAL DEL FICHERO");
					System.out.println("indique valor numérico a añadir: ");
					valor = sn.nextInt();
					System.out.println(anadirFinal(valor)?"Valor añadido correctamente":"ERROR: Valor no añadido");
					break;
				case 4:
					System.out.println("MODIFICANDO VALOR EN POSICIÓN");
					System.out.println("indique posición: ");
					posicion = sn.nextInt();
					System.out.println("indique nuevo: ");
					valor = sn.nextInt();
					System.out.println(modificarValor(valor, posicion)?"Valor modificado correctamente":"ERROR: Valor no modificado");
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
				sn.next();
			}
		}
		
	}
}
