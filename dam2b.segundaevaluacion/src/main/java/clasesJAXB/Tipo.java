package clasesJAXB;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Tipo {
	private String denominacion;
	private ArrayList<Caso> Casos = new ArrayList<Caso>();
	
	
	
	
	public Tipo() {
		super();
	}




	public Tipo(String denominacion, ArrayList<Caso> casos) {
		super();
		this.denominacion = denominacion;
		Casos = casos;
	}



	@XmlAttribute()
	public String getDenominacion() {
		return denominacion;
	}




	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}



	@XmlElement(name="casos")
	public ArrayList<Caso> getCasos() {
		return Casos;
	}




	public void setCasos(ArrayList<Caso> casos) {
		Casos = casos;
	}
	
	
	

}
