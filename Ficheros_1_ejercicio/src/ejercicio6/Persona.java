package ejercicio6;

public class Persona {
	protected StringBuilder  nombre;
	private StringBuilder  apellido1;
	private StringBuilder  apellido2;
	
	public Persona() {
		super();
	}
	public Persona(StringBuilder nombre, StringBuilder apellido1, StringBuilder apellido2) {
		super();
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
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
}
