package ejercicio6;

import java.io.Serializable;
import java.util.Date;

public class Persona implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected StringBuilder  nombre;
	private StringBuilder  apellido1;
	private StringBuilder  apellido2;
	private Date nacimiento;
	
	public Persona() {
		super();
	}
	
	public Persona(StringBuilder nombre, StringBuilder apellido1, StringBuilder apellido2, Date nacimiento) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nacimiento = nacimiento;
	}

	public StringBuilder getNombre() {
		return nombre;
	}
	public void setNombre(StringBuilder nombre) {
		this.nombre = nombre;
	}
	public StringBuilder getApellido1() {
		return apellido1;
	}
	public void setApellido1(StringBuilder apellido1) {
		this.apellido1 = apellido1;
	}
	public StringBuilder getApellido2() {
		return apellido2;
	}
	public void setApellido2(StringBuilder apellido2) {
		this.apellido2 = apellido2;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", nacimiento="
				+ nacimiento + "]";
	}
	
}
