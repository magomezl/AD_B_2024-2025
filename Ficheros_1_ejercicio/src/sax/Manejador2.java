package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Manejador2 extends DefaultHandler {
	
	private String receta;
	private static boolean encontrado = false;

	public Manejador2(String receta) {
		super();
		this.receta = receta.toLowerCase();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("receta") &&  attributes.getValue("nombre").toLowerCase().contains(receta)) {
			System.out.println("Ingredientes de " + receta);
			encontrado = true;
		}
		if (qName.equalsIgnoreCase("ingrediente") && encontrado ) {
			System.out.println(attributes.getValue("nombre"));
		}
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equalsIgnoreCase("receta") && encontrado) {
			encontrado=false;
		}
	}
	

}
