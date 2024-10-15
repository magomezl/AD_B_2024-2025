package ejercicio6_bis;

import java.io.Serializable;

public class Persona_v2 extends Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 22L;
	private int edad;
	
	public Persona_v2() {
		super();
	}
	
	public Persona_v2(StringBuilder nombre, StringBuilder apellido1, StringBuilder apellido2, int edad) {
		super();
		this.edad = edad;
	}

	@Override
	public String toString() {
		return getApellido1() + " " + getApellido2() + ", " + getNombre() + "(" + edad + " años)";
	}
	
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
}
