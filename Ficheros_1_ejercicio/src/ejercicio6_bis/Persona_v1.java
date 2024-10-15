package ejercicio6_bis;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Persona_v1 extends Persona implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	private Date nacimiento;
	
	public Persona_v1() {
		super();
	}
	public Persona_v1(StringBuilder nombre, StringBuilder apellido1, StringBuilder apellido2, Date nacimiento) {
		super();
		this.nacimiento = nacimiento;
	}
	@Override
	public String toString() {
		return getApellido1() + " " + getApellido2() + ", " + getNombre() + "(" + new SimpleDateFormat("dd-MM-yyyy").format(nacimiento) + ")";
	}
	
	public Date getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}
}
