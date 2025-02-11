package examen;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import clasesJAXB.Caso;
import clasesJAXB.Establecimientos;
import clasesJAXB.Tipo;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

public class Ejercicio3 {

	public static void creaDocumentoExcel_JAXB() {
		try {
			Workbook wb = new XSSFWorkbook(new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/alojamientosCyL.xlsx"));
			Sheet hoja = wb.getSheetAt(0);
			int numFila = 1;
			Row fila = hoja.getRow(numFila);
			
			Set<String> denominaciones = new HashSet<String>();
			while(fila!=null) {
				denominaciones.add(fila.getCell(1).getStringCellValue());
				fila = hoja.getRow(++numFila);
			}
			
			Establecimientos esttos = new Establecimientos();
			ArrayList<Tipo> tpos = new ArrayList<Tipo>();
			for (String denom: denominaciones) {
				Tipo tpo = new Tipo();
				tpo.setDenominacion(denom);
				ArrayList<Caso> csos = new ArrayList<Caso>();
				//Recorro el excel buscando los casos de esa denominación
				numFila = 1;
				fila = hoja.getRow(numFila);
				while (fila !=null) {
					if (fila.getCell(1).getStringCellValue().equals(denom)) {
						Caso cso = new Caso();
						cso.setProvincia(fila.getCell(0).getStringCellValue());
						cso.setNumero(fila.getCell(2).getNumericCellValue());
						csos.add(cso);
					}
					fila = hoja.getRow(++numFila);
				}
				tpo.setCasos(csos);
				tpos.add(tpo);
				esttos.getTipos().add(tpo);
			}
			JAXBContext jC = JAXBContext.newInstance(Establecimientos.class);
			Marshaller Ms = jC.createMarshaller();
			Ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Ms.marshal(esttos, new File(System.getProperty("user.dir") + "/src/main/resources/alojamientosCyL.xml"));
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
