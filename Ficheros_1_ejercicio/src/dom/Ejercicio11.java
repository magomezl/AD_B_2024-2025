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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	private static DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder dB; 
	
	public static void escribirXML() {
		ObjectInputStream datos = null;
		Document doc = null;
		try {
			datos = new ObjectInputStream(new FileInputStream(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO)));
			dB = dBf.newDocumentBuilder();
			doc = dB.newDocument();
			// creamos el elemento raiz
			Element elementoRaiz = doc.createElement("Personas");
			doc.appendChild(elementoRaiz);

			Persona persona = new Persona();

			while(true) {
				persona = (Persona) datos.readObject();
				Element elementoPersona = doc.createElement("persona");

//				CreaAtributo("nombre", persona.getNombre().toString(), elementoPersona);
//				CreaAtributo("apellido1", persona.getApellido1().toString(), elementoPersona);
//				CreaAtributo("apellido2", persona.getApellido1().toString(), elementoPersona);
				CreaAtributo("nacimiento", persona.getNacimiento().toString(), elementoPersona);
				CreaTexto(persona.getNombre().toString() + " " + persona.getApellido1().toString() + " " +
						persona.getApellido2().toString(), elementoPersona, doc);
			
//				CreaAtributo("nacimiento", persona.getNacimiento().toString(), elementoPersona);
//				CreaElemento("nombre", persona.getNombre().toString(), elementoPersona, doc);
//				CreaElemento("apellido1", persona.getApellido1().toString(), elementoPersona, doc);
//				CreaElemento("apellido1", persona.getApellido2().toString(), elementoPersona, doc);
//				CreaElemento("nacimiento", persona.getNacimiento().toString(), elementoPersona, doc);
				elementoRaiz.appendChild(elementoPersona);
				
			}
		}catch (EOFException e) {
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}finally {
			try {
				// Pasamos el documento DOM de memoria a fichero físico (persistente)
				TransformerFactory tF = TransformerFactory.newInstance();
				Transformer t = tF.newTransformer();
				t.setOutputProperty(OutputKeys.INDENT, "yes");
				t.transform(new DOMSource(doc), new StreamResult(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO_OUT)));
				datos.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				e.printStackTrace();
			} catch (TransformerException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		escribirXML();
		leerXML();
	}
//	public static void escribeXML() {
//		CreaAtributo("nombre", persona.getNombre().toString(), elementoPersona, doc);
//		
//	}
	public static void leerXML() {
		try {
			Document doc = dB.parse(new File(Ejercicio3.RUTADATOS + FICHEROTRABAJO_OUT));
			leeNodo(doc.getDocumentElement());
		} catch (Exception e) {e.printStackTrace();}
	}

	public static void leeNodo(Node nodo) {
		if ( nodo.getNodeType()==Node.ELEMENT_NODE) {
			System.out.print("<" + nodo.getNodeName());
			//Obtengo los atributos
			NamedNodeMap nNM = nodo.getAttributes();
			if (nNM.getLength()>0) {
				for (int i=0; i<nNM.getLength(); i++) {
					System.out.print(" " + nNM.item(i).getNodeName() + "=" + nNM.item(i).getNodeValue());
				}
			}
			System.out.print(">");
			NodeList nodosHijos = nodo.getChildNodes();
			if (nodosHijos.getLength()>0) {
				if (nodosHijos.item(0).getNodeType()==Node.ELEMENT_NODE) { 
					System.out.println("\t");
				}
				for (int i=0; i<nodosHijos.getLength(); i++) {
					leeNodo(nodosHijos.item(i));
				}
				System.out.println("</" + nodo.getNodeName() + ">");
			}
		}else if (nodo.getNodeType()==Node.TEXT_NODE) {
			System.out.print(nodo.getNodeValue());
		}
	}
	
	private static void CreaElemento(String etiqueta, String valor, Element padre, Document doc) {
		Element elemento = doc.createElement(etiqueta);
		Text texto = doc.createTextNode(valor);
		elemento.appendChild(texto);
		padre.appendChild(elemento);
	}
	
	private static void CreaAtributo(String etiqueta, String valor, Element padre){
		padre.setAttribute(etiqueta, valor);
	}
	
	private static void CreaTexto(String valor, Element padre, Document doc) {
		Text texto = doc.createTextNode(valor);
		padre.appendChild(texto);
	}
	
}
