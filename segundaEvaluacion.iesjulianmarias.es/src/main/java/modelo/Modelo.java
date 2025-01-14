package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
//		anadirDpto("LL","LLLLL");
		
//		System.out.println("Este pregunta");
//		anadirEmpleado("Pascual", "García", "Gómez", "Cualquiera2" );
//		anadirEmpleado("Pascualon", "García", "Gómez", "Marketing" );
//		System.out.println("\n\nListar Empleados TODOS");
//		listarEmpleados();
//		
//		eliminarEmpleado("Pascualina", "García", "Gómez");
//		System.out.println("\n\nListar Empleados TODOS");
//		listarEmpleados();
//		
//		System.out.println("\n\nListar Empleados DPTO ");
//		listarEmpleados("Contabilidad");
		
		modificarEmpleado("Sofia", "Gómez", "Gil", "Propaganda");
		
//		System.out.println("Este se almacena tb el dpto");
//		anadirEmpleado("Sandra", "Pérez", "Arnau", "Finanzas" );
//		System.out.println("Este lo almacena en el único dpto");
//		anadirEmpleado("Sonia", "Pérez", "Arnau", "Finanzas" );
	}
	
	
	
	
	 
	private static void modificarEmpleado(String nombre, String apellido1, String apellido2, String dptoNew) {
		Scanner sc = new Scanner(System.in);
		Departamentos dptoObj= new Departamentos();
		Empleados empObj;
		Integer idGenerado = 0;
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		Integer idEmp; 
		if ((idEmp=existeEmpleado(nombre, apellido1, apellido2))==0) {
			sesion.close();
			System.out.println("No existe el empleado que pretende modificar");
		}else {
			List<Departamentos> dptos = new ArrayList<Departamentos>();
			dptos = listarDptosNombre(dptoNew);
			if (dptos.isEmpty()) {
				System.out.println("No existe el departamento indicado, indique la localidad del mismo para incluirlo:");
				String localidad = sc.next();
				dptoObj.setDnombre(dptoNew);
				dptoObj.setLoc(localidad);
				int generatedId = (int) sesion.save(dptoObj);
				empObj = sesion.get(Empleados.class, idEmp);
				empObj.setDepartamentos(sesion.get(Departamentos.class, generatedId));
				sesion.merge(empObj);
				t.commit();
				sesion.close();
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
				empObj = sesion.get(Empleados.class, idEmp);
				empObj.setDepartamentos(dptoObj);
				sesion.merge(empObj);
				t.commit();
				sesion.close();
			}else if (dptos.size()==1) {
				System.out.println("Hay un departamento con ese nombre");
				dptoObj = dptos.getFirst();
				System.out.println(dptoObj);
				empObj = sesion.get(Empleados.class, idEmp);
				empObj.setDepartamentos(dptoObj);
				sesion.merge(empObj);
				t.commit();
				sesion.close();
			}
		}
		
	}





	private static ArrayList<Empleados> listarEmpleados() {
		sesion = sf.openSession();
		List<Empleados> empleados = new ArrayList<Empleados>();
		empleados = sesion.createQuery("FROM Empleados", Empleados.class)
				.getResultList();
		
		for(Empleados emp: empleados) {
			System.out.println(emp);
		}
		sesion.close();
		return (ArrayList<Empleados>) empleados;
	}

	private static ArrayList<Empleados> listarEmpleados(String dpto) {
		sesion = sf.openSession();
		List<Empleados> empleados = new ArrayList<Empleados>();
		empleados = sesion.createQuery("FROM Empleados where departamentos.dnombre = :dptoName ", Empleados.class)
				.setParameter("dptoName", dpto)
				.getResultList();
		
		for(Empleados emp: empleados) {
			System.out.println(emp);
		}
		sesion.close();
		return (ArrayList<Empleados>) empleados;
	}




	private static void eliminarEmpleado(String name, String sname1, String sname2) {
		Scanner sc = new Scanner(System.in);
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		
		String hql = "FROM Empleados e WHERE e.nombre = :nombre AND e.apellido1 = :apellido1 AND "
				+ "e.apellido2 = :apellido2";
		List<Empleados> empleados = sesion.createQuery(hql, Empleados.class)
				.setParameter("nombre", name)
				.setParameter("apellido1", sname1)
				.setParameter("apellido2", sname2)
				.getResultList();
		
		if (empleados.isEmpty()) {
			System.out.println("No existe el empleado que desea borrar");
			
		}else {
			Departamentos dptoEmpleado = empleados.get(0).getDepartamentos();
			
			String hql2 = "SELECT count(e) FROM Empleados e WHERE e.departamentos = :dpto";
			Long empleadosDpto = (Long) sesion.createQuery(hql2, Long.class)
					.setParameter("dpto", dptoEmpleado)
					.getSingleResult();
			if (empleadosDpto==1) {
				System.out.println("El departamento " +  dptoEmpleado.getDnombre() + " no tiene más empleados.");
                System.out.print("¿Quieres eliminar ese departamento? (S/N): ");
                sc = new Scanner(System.in);
                String respuesta = sc.nextLine().trim().toLowerCase();
                if (respuesta.equals("s")) {
                	sesion.remove(dptoEmpleado);
                }
			}
			sesion.remove(empleados.getFirst());
			t.commit();
			sesion.close();
		}
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
		Empleados empObj = null;
		Integer idGenerado = 0;
		sesion = sf.openSession();
		Transaction t = sesion.beginTransaction();
		if (existeEmpleado(nombre, apellido1, apellido2)!=0) {
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
				empObj = new Empleados();
				empObj.setDepartamentos(sesion.get(Departamentos.class, generatedId));
				empObj.setNombre(nombre);
				empObj.setApellido1(apellido1);
				empObj.setApellido2(apellido2);
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
			}else if (dptos.size()==1) {
				System.out.println("Hay un departamento con ese nombre");
				dptoObj = dptos.getFirst();
				empObj = new Empleados(dptoObj, nombre, apellido1, apellido2);
			}
			// Añadir al empleado 
			sesion.save(empObj);
			t.commit();
			sesion.close();
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
	
	private static Integer existeEmpleado(String nombre, String apellido1, String apellido2) {
		
		try {
		Empleados emp = null;
		emp = sesion.createQuery("from Empleados where nombre= :nombre and "
				+ " apellido1= :apellido1 and apellido2 = :apellido2", Empleados.class)
			.setParameter("nombre", nombre)
			.setParameter("apellido1", apellido1)
			.setParameter("apellido2", apellido2)
			.getResultList()
			.get(0);
		int id = emp.getId();
		
		System.out.println(id);
		return id;
		
		}catch (IndexOutOfBoundsException e) {
			return 0;
		}
		
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
