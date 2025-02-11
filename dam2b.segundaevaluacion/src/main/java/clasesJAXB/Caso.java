package clasesJAXB;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;

public class Caso {
	private String provincia;
	private double numero;
	
	
	
	
	public Caso() {
		super();
	}




	public Caso(String provincia, double numero) {
		super();
		this.provincia = provincia;
		this.numero = numero;
	}



	@XmlAttribute()
	public String getProvincia() {
		return provincia;
	}




	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}



	@XmlValue()
	public double getNumero() {
		return numero;
	}




	public void setNumero(double numero) {
		this.numero = numero;
	}

	
}
