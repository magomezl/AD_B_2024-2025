package jaxb.clasesEjercicio16;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name="examen")
public class Examen {
	private ArrayList<Autor> listaAutores;
	private ArrayList<Pregunta> listaPreguntas;
	
	public Examen() {
		super();
	}

	public Examen(ArrayList<Autor> listaAutores, ArrayList<Pregunta> listaPreguntas) {
		super();
		this.listaAutores = listaAutores;
		this.listaPreguntas = listaPreguntas;
	}
	@XmlElementWrapper(name="autores")
	@XmlElement(name="autor")
	public ArrayList<Autor> getListaAutores() {
		return listaAutores;
	}

	public void setListaAutores(ArrayList<Autor> listaAutores) {
		this.listaAutores = listaAutores;
	}
	@XmlElementWrapper(name="preguntas")
	@XmlElement(name="pregunta")
	public ArrayList<Pregunta> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(ArrayList<Pregunta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}
	
	
	
}
