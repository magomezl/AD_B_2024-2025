package clasesHibernate;
// Generated 10 feb 2025 18:53:06 by Hibernate Tools 6.5.1.Final

import java.util.HashSet;
import java.util.Set;

/**
 * Tipos generated by hbm2java
 */
public class Tipos implements java.io.Serializable {

	private int id;
	private String descripcion;
	private Set alojamientosturismorurals = new HashSet(0);

	public Tipos() {
	}

	public Tipos(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Tipos(int id, String descripcion, Set alojamientosturismorurals) {
		this.id = id;
		this.descripcion = descripcion;
		this.alojamientosturismorurals = alojamientosturismorurals;
	}

	public Tipos(String descripcion) {
		this.descripcion = descripcion;
	}

	public Tipos(String descripcion, Set alojamientosturismorurals) {
		this.descripcion = descripcion;
		this.alojamientosturismorurals = alojamientosturismorurals;
	}

	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set getAlojamientosturismorurals() {
		return this.alojamientosturismorurals;
	}

	public void setAlojamientosturismorurals(Set alojamientosturismorurals) {
		this.alojamientosturismorurals = alojamientosturismorurals;
	}

}
