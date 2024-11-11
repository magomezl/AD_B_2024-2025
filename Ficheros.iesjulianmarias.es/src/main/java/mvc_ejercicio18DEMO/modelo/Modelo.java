package mvc_ejercicio18DEMO.modelo;

public class Modelo {
	
	private int ope1;
	private int ope2;
	private int resultado;
	
	
	
	public void setOpe1(int ope1) {
		this.ope1 = ope1;
	}

	public void setOpe2(int ope2) {
		this.ope2 = ope2;
	}
	
	

	public int suma() {
		this.resultado = this.ope1 + this.ope2;
		return this.resultado;
	}
	
	public int resta() {
		this.resultado = this.ope1 - this.ope2;
		return this.resultado;
	}
	

}
