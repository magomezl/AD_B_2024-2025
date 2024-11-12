package MVC_ejercicio18;

import MVC_ejercicio18.controlador.Controlador;
import MVC_ejercicio18.modelo.Modelo;
import MVC_ejercicio18.vista.Vista;

public class App {

	public static void main(String[] args) {
		Controlador controlador = new Controlador(new Modelo(), new Vista());

	}

}
