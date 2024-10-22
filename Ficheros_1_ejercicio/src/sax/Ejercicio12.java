package sax;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import repaso.Ejercicio3;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import org.xml.sax.InputSource;

public class Ejercicio12 {
	public final static String FICHEROTRABAJO = "ejercicio12.xml";
	public static void main(String[] args) {
		
		try {
			SAXParserFactory sPF = SAXParserFactory.newInstance();
			SAXParser sP = sPF.newSAXParser();
			sP.parse(new InputSource(Ejercicio3.RUTADATOS+FICHEROTRABAJO), new Manejador());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
