package dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import repaso.Ejercicio3;

public class Ejercicio10 {
	public final static String FICHEROTRABAJO = "ejercicio10.xml";
	public static void main(String[] args) {
		try {
			DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBf.newDocumentBuilder();
			Document doc = dB.newDocument();
			
			// Creamos los nodos
			Element raiz = doc.createElement("raiz");
			Element hijo_1 = doc.createElement("hijo");
			Element hijo_2 = doc.createElement("hijo");
			Text contenidoH1 = doc.createTextNode("Soy el contenido del primer hijo");
			Text contenidoH2 = doc.createTextNode("Soy el contenido del segundo hijo");
			
			// Construimos el árbol 
			doc.appendChild(raiz);
			raiz.appendChild(hijo_1);
			raiz.appendChild(hijo_2);
			hijo_1.appendChild(contenidoH1);
			hijo_1.setAttribute("name", "alfonso");
			hijo_2.appendChild(contenidoH2);
			
			// Pasamos el documento DOM de memoria a fichero físico (persistente)
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO)));
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
