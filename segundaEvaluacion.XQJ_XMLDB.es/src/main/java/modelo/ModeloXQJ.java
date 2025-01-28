package modelo;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQMetaData;
import javax.xml.xquery.XQResultSequence;

public class ModeloXQJ {

	public static void main(String[] args) {
//		metaInformacion();
		consultaModulosCiclo("SMR");
		// Esto así es un poco cutre sería mejor pasar los valores de los elementos y atributos y componer el elemento modulo
		// dentro del método
		anadirModulo("<modulo codigo='0000'>" +
				"<nombre>Programacion</nombre>" +
				"<duracion unidad='horas'>280</duracion>" +
				"<ects>28</ects>" +
				"<curso>1</curso>" +
				"<ciclos>" +
				"<ciclo siglas='DAM'/>"+
				"</ciclos>" +
				"</modulo>");

	}

	
	private static void anadirModulo(String elemento) {
		try {
			XQExpression xqe = ConexionXQJ.getInstancia("admin", "toor").getCon().createExpression();
			xqe.executeCommand(
					"update insert " + elemento + " into doc('/db/misDocumentosXML/ciclos.xml')/fp/modulos");
			
			
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private static void consultaModulosCiclo(String siglas) {
		String consultaNombreCiclo = "doc('/db/misDocumentosXML/ciclos.xml')/fp/ciclos/ciclo[@siglas='" +
				siglas + "']/nombre";
		
		String consultaModulosCiclo = "for $nM in doc('/db/misDocumentosXML/ciclos.xml')/fp/modulos/modulo " +
		"where $nM/ciclos/ciclo/@siglas='" + siglas + "' return $nM/nombre";

		muestraResultados(consultaNombreCiclo);
		muestraResultados(consultaModulosCiclo);
	}

	private static void muestraResultados(String consulta) {
		try {
			XQExpression xqe = ConexionXQJ.getInstancia("admin", "toor").getCon().createExpression();
			XQResultSequence xqRS = xqe.executeQuery(consulta);
			while(xqRS.next()) {
				
				XMLStreamReader xSR = xqRS.getItemAsStream();
				while(xSR.hasNext()) {
					if (xSR.getEventType() == XMLStreamConstants.END_ELEMENT) {
						System.out.println("Final del elemento");
					}
					
					if (xSR.getEventType() == XMLStreamConstants.CHARACTERS) {
						System.out.println(xSR.getText());
					}
					xSR.next();
				}
				
			}
			
			
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private static void metaInformacion() {
		try {
			XQMetaData xqmd = ConexionXQJ.getInstancia("admin", "toor").getCon().getMetaData();
			System.out.println("nombre de usuario: " + xqmd.getUserName() +
					"\n\t Soporte para transacciones: " + xqmd.isTransactionSupported() +
					"\n\t Soporte para XQuery: " + xqmd.isXQueryXSupported());
			System.out.println("TODA LA INFORMACIÍN : " + xqmd.toString());
		} catch (XQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
