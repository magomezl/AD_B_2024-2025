package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import clasesHibernate.Departamentos;
import clasesHibernate.Empleados;
import jakarta.persistence.TypedQuery;

public class Modelo {
	
	private static final Configuration cfg = new Configuration().configure();
	private static final SessionFactory sf = cfg.buildSessionFactory();
	private static Session sesion;
	
	
	
	public static void main(String[] args) {
		
//		anadirEmpleado("Aurora", "Gómez", "López", "Contabilidad");
		//anadirDpto("Salud", "Huelva");
//		borrarDpto(9);
		
//		List<Departamentos> dptos = new ArrayList<Departamentos>();
//		dptos = listarDptos();
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		sesion.close();
//		
//		modificarDpto(4, null, "Madrid");
////		System.out.println(listarDptos(2));
//		System.out.println("____________________________");
//		dptos = listarDptos();
//		for(Departamentos dpto: dptos) {
//			System.out.println(dpto);
//		}
//		sesion.close();
//		System.out.println("Este no se almacena");
//		anadirEmpleado("Elena", "García", "Gómez", "Marketing" );
		anadirDpto("LL","LLLLL");
		
		System.out.println("Este pregunta");
		anadirEmpleado("Pascual", "García", "Gómez", "Cualquiera2" );
//		System.out.println("Este se almacena tb el dpto");
//		anadirEmpleado("Sandra", "Pérez", "Arnau", "Finanzas" );
//		System.out.println("Este lo almacena en el único dpto");
//		anadirEmpleado("Sonia", "Pérez", "Arnau", "Finanzas" );
	}
	
	
	/**
	 * Comprobar si existe un empleado con el mismo nombre, apellido1 y apellido2, si es así no hace nada
	 * si no, comprobar existencia del dpto, si el departamento no existe, lo almacenamos pidiendo los datos que sean necesarios,
	 * Pueden existir varios departamentos con el mismo nombre, los mostramos y pedimos al usuario que seleccione uno 
	 * y se almacena el empleado en ese departamento 
	  
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @param dpto
	 */
	private static void anadirEmpleado(String nombre, String apellido1, String apellido2, String dpto) {
		Scanner sc = new Scanner(System.in);
		Departamentos dptoObj= new Departamentos();
		Empleados empObj = new Empleados();
		Integer idGenerado = 0;
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		if (existeEmpleado(nombre, apellido1, apellido2)) {
			sesion.close();
			System.out.println("Ya existe el empleado");
		}else {
			List<Departamentos> dptos = new ArrayList<Departamentos>();
			dptos = listarDptosNombre(dpto);
			if (dptos.isEmpty()) {
				System.out.println("No existe el departamento indicado, indique la localidad del mismo para incluirlo:");
				String localidad = sc.next();
				dptoObj.setDnombre(dpto);
				dptoObj.setLoc(localidad);
				int generatedId = (int) sesion.save(dptoObj);
				//Quizá no se necesite
				empObj.setDepartamentos(sesion.get(Departamentos.class, generatedId));
				empObj.setNombre(nombre);
				empObj.setApellido1(apellido1);
				empObj.setApellido2(apellido2);
				
				sesion.save(empObj);
				t.commit();
				sesion.close();
				System.out.println("Cierro sesion");
			}else if (dptos.size()>1) {
				//Elegir departamento
				System.out.println("Departamentos con el nombre seleccionado:");
				for(Departamentos dptoObjbis: dptos) {
					System.out.println(dptoObjbis);
				}
				System.out.println("Indique el código del departamento al que desea añadir al empleado:");
				String idDpto = sc.next();
				//Recuperamos el departamento
				dptoObj = sesion.get(Departamentos.class, Integer.valueOf(idDpto));
				System.out.println("Departamento:");
				System.out.println(dptoObj);
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);
				sesion.persist(empObj);
				t.commit();
				sesion.close();
				
			}else if (dptos.size()==1) {
				System.out.println("Hay un departamento con ese nombre");
				dptoObj = dptos.getFirst();
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);
				System.out.println("Departamento:");
				System.out.println(dptoObj);
			}
			// Añadir al empleado 
			
		}
	}

	
	



	private static void modificarDpto(int idDpto, String nombreNew, String localidadNew) {
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, idDpto);
		if (nombreNew!=null) {
			dpto.setDnombre(nombreNew);
		}
		if (localidadNew!=null) {
			dpto.setLoc(localidadNew);
		}
		sesion.merge(dpto);
		t.commit();
		sesion.close();
	}



	private static List<Departamentos> listarDptos() {
		sesion = sf.openSession();
		TypedQuery<Departamentos> consulta =  sesion.createQuery("from Departamentos", Departamentos.class);
		return consulta.getResultList();
	}
	
	private static Departamentos listarDptos(int dptoNum) {
		sesion = sf.openSession();
		TypedQuery<Departamentos> consulta =  sesion.createQuery("from Departamentos where deptNo= :dpto", Departamentos.class)
				.setParameter("dpto", dptoNum);
		Departamentos dptoObj = consulta.getSingleResult();
		sesion.close();
		return dptoObj;
	}
	//TODO cerrar sesiones
	private static List<Departamentos> listarDptos(String localidad) {
		sesion = sf.openSession();
		TypedQuery<Departamentos> consulta =  sesion.createQuery("from Departamentos where loc='" + localidad + "'", Departamentos.class);
		return consulta.getResultList();
	}
	
	private static List<Departamentos> listarDptosNombre(String nombre) {
		sesion = sf.openSession();
		TypedQuery<Departamentos> consulta =  sesion.createQuery("from Departamentos where dnombre='" + nombre + "'", Departamentos.class);
		return consulta.getResultList();
	}

	
	


	/**
	 * Comprueba si el empleado está en la db 
	 * @param nombre
	 * @param apellido1
	 * @param apellido2
	 * @return true si existe el empleado y false en caso contrario
	 */
	
	private static boolean existeEmpleado(String nombre, String apellido1, String apellido2) {
		 
		TypedQuery<Empleados> consulta =  sesion.createQuery("from Empleados where nombre='" + nombre + "' and apellido1='" + apellido1 +"' and apellido2='" + apellido2 + "'", Empleados.class);
		if (!consulta.getResultList().isEmpty()) {
			return true;
		}
		return false;
	}








	private static void anadirDpto(String nombre, String localidad) {
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = new Departamentos(nombre, localidad, null);
		sesion.persist(dpto);
		t.commit();
		sesion.close();
	}
	
	private static boolean borrarDpto(int dptoNum) {
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Departamentos dpto = sesion.get(Departamentos.class, dptoNum);
		if (JOptionPane.showConfirmDialog(null,"¿Esta seguro de querer eliminar el dpto " + dpto.getDnombre() + "?")==JOptionPane.YES_OPTION) {
			sesion.remove(dpto);
			t.commit();
			sesion.close();
			return true;
		}
		sesion.close();
		return false;
	}
	

}
