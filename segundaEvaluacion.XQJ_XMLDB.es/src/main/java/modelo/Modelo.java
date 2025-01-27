package modelo;

import java.lang.reflect.InvocationTargetException;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

public class Modelo {
	
	private static void listarContenido(Collection col) {
		try {

			if (col==null) {
				System.out.println("La colección no existe o ha habido un error");
				return;
			}
			Service servicios[] = col.getServices();
			System.out.println("Servicios proporcionados por la colección:" + servicios.length);
			for(Service s: servicios) {
				System.out.println("\t" + s.getName() + "(" + s.getVersion() + ")");
			}
			
//			CollectionManagementService serCMS = (CollectionManagementService) col.getService("CollectionManagementService", "1.0");
			System.out.println("Nombre de la colección:" + col.getName());
			System.out.println("Colecciones hijas:" + col.getChildCollectionCount());
			String nameColHijas[] = col.listChildCollections();
			for(String nameColH: nameColHijas) {
				System.out.println("\t" + nameColH);
			}
			
			System.out.println("Documentos o recursos: " + col.getResourceCount());
			String nameColResources[] = col.listResources();
			for(String nameColR: nameColResources) {
				System.out.println("\t" + nameColR);
			}
			
			
			
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	

	public static void main(String[] args) {
		//listarContenido(ConexionXMLDB.getInstancia("admin", "toor", "/db/misDocumentosXML").getCol());
//		creaBorraColeccion(ConexionXMLDB.getInstancia("admin", "toor", "/db/misDocumentosXML").getCol(), "coleccion1", 'd');
//		creaBorraColeccion(ConexionXMLDB.getInstancia("admin", "toor", "/db/misDocumentosXML").getCol(), "coleccionCreada", 'c');
		creaDocumentoString(ConexionXMLDB.getInstancia("admin", "toor", "/db/misDocumentosXML/coleccionCreada/").getCol(), 
				"mascotas.xml", "<mascotas><mascota nombre='lolo' especie='perro'/><mascota nombre='mico' especie='gato'/></mascotas>");
		
	}



	/**
	 * 
	 * @param colPadre
	 * @param nameResource
	 * @param contenido
	 */
	
	
	private static void creaDocumentoString(Collection colPadre, String nameResource, String contenido) {
		//TODO resolver crear en carpeta
		try {
			if (colPadre==null) {
				System.out.println("La colección padre no existe");
				return;
			}
//			CollectionManagementService serCMS = (CollectionManagementService) 
//					colPadre.getService("CollectionManagementService", "1.0");
			Resource recurso = colPadre.createResource(nameResource, XMLResource.RESOURCE_TYPE);
			recurso.setContent(contenido);
			colPadre.storeResource(recurso);
		} catch (XMLDBException e) {
			e.printStackTrace();
		}

		
	}




	private static void creaBorraColeccion(Collection colPadre, String nameCol, char creaBorra) {
		try {
			if (colPadre==null) {
				System.out.println("La colección padre no existe");
				return;
			}
			CollectionManagementService serCMS = (CollectionManagementService) 
					colPadre.getService("CollectionManagementService", "1.0");
			switch(creaBorra) {
			case 'c':
				serCMS.createCollection(nameCol);
				break;
			case 'd':
				serCMS.removeCollection(nameCol);
				break;
			}
			
			
			
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
