package dom;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import repaso.Ejercicio3;

public class Ejercicio9 {
	public final static String FICHEROTRABAJO = "ejercicio9.xml";
	public static void main(String[] args) {
		
		try {
			DocumentBuilderFactory dBf = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dBf.newDocumentBuilder();
			Document doc = dB.parse(new File(Ejercicio3.RUTADATOS+FICHEROTRABAJO));
			
			URL url =  new URI("https://acortar.link/dO4FNx").toURL();
			Document docWeb = dB.parse(url.openStream());
			
			
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			t.transform(new DOMSource(doc), new StreamResult(System.out));
			System.out.println("DOCUMENTO WEB");
			t.transform(new DOMSource(docWeb), new StreamResult(System.out));
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
