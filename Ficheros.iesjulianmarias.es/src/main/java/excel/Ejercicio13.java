package excel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ficheros.Utilidades;

public class Ejercicio13 {
	private final static String DOCTRABAJO_IN = "vehiculosElectricos.xlsx";
	private static Workbook wb; 
	
	public static void main(String[] args) {
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(Utilidades.getRutadatos()+ DOCTRABAJO_IN)));
			buscarEstacionesPorMarca("Wenea");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void buscarEstacionesPorMarca(String operador) {
		Sheet hoja = wb.getSheetAt(0);
		int numFila = 1;
		Row fila = hoja.getRow(numFila);
		
		// Muestro las localizaciones de los puntos de carga del operador pasado como parámetro
		System.out.println("PUNTOS DE RECARGA " + operador);
		while(fila!=null) {
			Cell celdaBusqueda = fila.getCell(2);
			if (celdaBusqueda!=null) {
				if (celdaBusqueda.getStringCellValue().contains(operador)) {
					System.out.println("----->" + fila.getCell(0).getStringCellValue() + "(" + 
							fila.getCell(1).getStringCellValue());
				}
			}
			fila = hoja.getRow(numFila++);
		} 
	}
	
	
	// Para casa crear un método que muestre los puntos de carga de una determinada ciudad 
	// que se pasará como parámetro
}
