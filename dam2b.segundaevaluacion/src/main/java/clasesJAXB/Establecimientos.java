package clasesJAXB;

import java.util.ArrayList;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="establecimientos")
public class Establecimientos {
	private ArrayList<Tipo> Tipos = new ArrayList<Tipo>();

	
	
	public Establecimientos() {
		super();
	}



	public Establecimientos(ArrayList<Tipo> tipos) {
		super();
		Tipos = tipos;
	}


	@XmlElement(name="tipo")
	public ArrayList<Tipo> getTipos() {
		return Tipos;
	}



	public void setTipos(ArrayList<Tipo> tipos) {
		Tipos = tipos;
	}
	
	
	

}
