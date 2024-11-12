package MVC_ejercicio18.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MVC_ejercicio18.modelo.Modelo;
import MVC_ejercicio18.vista.Vista;


public class Controlador implements ActionListener {
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		vista.btnGuardar.addActionListener(this);
		
		
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==vista.btnGuardar) {
			modelo.anadirDpto(vista.textNombre.getText(), vista.textLocalidad.getText());
			vista.textNombre.setText(null);
			vista.textLocalidad.setText(null);
		}
	}
}
