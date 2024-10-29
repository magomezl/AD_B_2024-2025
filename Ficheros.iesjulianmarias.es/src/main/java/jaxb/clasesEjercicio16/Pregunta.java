package jaxb.clasesEjercicio16;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
@XmlType(propOrder= {"enunciado", "listaRespuestas"})
public class Pregunta {
	private String dificultad;
	private String autoria;
	private String enunciado;
	private ArrayList<Respuesta> listaRespuestas;
	
	public Pregunta() {
		super();
	}

	public Pregunta(String dificultad, String autoria, String enunciado, ArrayList<Respuesta> listaRespuestas) {
		super();
		this.dificultad = dificultad;
		this.autoria = autoria;
		this.enunciado = enunciado;
		this.listaRespuestas = listaRespuestas;
	}
	@XmlAttribute()
	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	@XmlAttribute()
	public String getAutoria() {
		return autoria;
	}

	public void setAutoria(String autoria) {
		this.autoria = autoria;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	
	public ArrayList<Respuesta> getListaRespuestas() {
		return listaRespuestas;
	}
	@XmlElement(name="respuesta")
	public void setListaRespuestas(ArrayList<Respuesta> listaRespuestas) {
		this.listaRespuestas = listaRespuestas;
	}
}
