package dom;

import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import ejercicio6.Persona;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import repaso.Ejercicio3;

public class Ejercicio11 {
	public final static String FICHEROTRABAJO = "FicheroPersonas_15_10_24.dat";
	public final static String FICHEROTRABAJO_OUT = "FicheroPersonas_15_10_24.xml";
	public static void main(String[] args) {
		ObjectInputStream datos = null;
		try {
			datos = new ObjectInputStream(new FileInputStream(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO)));

			DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBf.newDocumentBuilder();
			Document doc = dB.newDocument();
			// creamos el elemento raiz
			Element elementoRaiz = doc.createElement("Personas");
			doc.appendChild(elementoRaiz);

			Persona persona = new Persona();
			try {
				while(true) {
					persona = (Persona) datos.readObject();
					Element elementoPersona = doc.createElement("persona");
					CreaElemento("nombre", persona.getNombre().toString(), elementoPersona, doc);
					CreaElemento("apellido1", persona.getApellido1().toString(), elementoPersona, doc);
					CreaElemento("apellido1", persona.getApellido2().toString(), elementoPersona, doc);
					CreaElemento("nacimiento", persona.getNacimiento().toString(), elementoPersona, doc);
					elementoRaiz.appendChild(elementoPersona);
				}
			}catch (EOFException e) {
				
			}
			// Pasamos el documento DOM de memoria a fichero físico (persistente)
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO_OUT)));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				datos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void escribeXML() {
	
	}
	public static void leerXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			//Cargamos en memoria el doc XML        
			Document doc = builder.parse(new File(Ejercicio3.RUTADATOS + FICHEROTRABAJO_OUT));

			leeNodo(doc.getDocumentElement());

		} catch (Exception e) {e.printStackTrace();}
	}

	public static void leeNodo(Node nodo) {


	}
	

	
	private static void CreaElemento(String etiqueta, String valor, Element padre, Document doc) {
		Element elemento = doc.createElement(etiqueta);
		Text texto = doc.createTextNode(valor);
		elemento.appendChild(texto);
		padre.appendChild(elemento);
	}
}
