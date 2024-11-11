package mvc_ejercicio18DEMO;

import mvc_ejercicio18DEMO.controlador.Controlador;
import mvc_ejercicio18DEMO.modelo.Modelo;
import mvc_ejercicio18DEMO.vista.Vista;

public class App {

	public static void main(String[] args) {
		Controlador controlador = new Controlador(new Modelo(), new Vista());

	}

}
