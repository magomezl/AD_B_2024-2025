package transformacion_xslt;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import repaso.Ejercicio3;

public class Ejercicio14 {
	private final static String FICHEROTRABAJO_XSL = "ejercicio14.xsl";
	private final static String FICHEROTRABAJO_XML = "ejercicio14.xml";
	
	private final static String FICHEROSALIDA_HTML = "index.html";
	
	public static void main(String[] args) {
		try {
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer(new StreamSource(Ejercicio3.RUTADATOS+FICHEROTRABAJO_XSL));
			t.transform(new StreamSource(Ejercicio3.RUTADATOS+FICHEROTRABAJO_XML), 
					new StreamResult(Ejercicio3.RUTADATOS+FICHEROSALIDA_HTML));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
