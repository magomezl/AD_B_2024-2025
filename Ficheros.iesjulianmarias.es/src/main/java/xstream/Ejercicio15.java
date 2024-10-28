package xstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.File;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileOutputStream;

import ejercicio6.Persona;
import ficheros.Utilidades;

public class Ejercicio15 {
	private final static String FICHEROTRABAJO_IN = "FicheroPersonas_15_10_24.dat";
	private final static String FICHEROTRABAJO_OUT = "Ejercicio15.xml";
	private static XStream xS = new XStream(new DomDriver("UTF-8"));
	private static ObjectInputStream oIS = null;

	public static void main(String[] args) {
		
		deserializar_a_XML();

	}

	private static void deserializar_a_XML() {
		ListaPersonas lP = new ListaPersonas();
		try {
			oIS = new ObjectInputStream(new FileInputStream(new File(Utilidades.getRutadatos()+FICHEROTRABAJO_IN)));
			while(true) {
				Persona person = (Persona) oIS.readObject();
				lP.anadir(person);
			}
			
		}catch (EOFException e) { 
		}catch (IOException e) {
			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				xS.alias("familia", ListaPersonas.class);
				xS.alias("miembro", Persona.class);
				xS.aliasField("primerApellido", Persona.class, "apellido1");
				xS.aliasField("segundoApellido", Persona.class, "apellido2");
				xS.aliasField("name", Persona.class, "nombre");
				xS.useAttributeFor(Persona.class, "nombre");
				xS.addImplicitCollection(ListaPersonas.class, "lista");
				
				xS.toXML(lP, new FileOutputStream(Utilidades.getRutadatos()+FICHEROTRABAJO_OUT));
				oIS.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
