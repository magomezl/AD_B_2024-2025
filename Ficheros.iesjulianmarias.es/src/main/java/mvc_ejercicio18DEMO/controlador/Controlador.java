package mvc_ejercicio18DEMO.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mvc_ejercicio18DEMO.modelo.Modelo;
import mvc_ejercicio18DEMO.vista.Vista;

public class Controlador implements ActionListener {
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) {
		super();
		this.modelo = modelo;
		this.vista = vista;
		vista.btnSumar.addActionListener(this);
		vista.btnRestar.addActionListener(this);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		modelo.setOpe1(Integer.parseInt(vista.textOp1.getText()));
		modelo.setOpe2(Integer.parseInt(vista.textOp2.getText()));
		if (e.getSource()==vista.btnSumar) {
			vista.textResult.setText(String.valueOf(modelo.suma()));
		}else if (e.getSource()==vista.btnRestar) {
			vista.textResult.setText(String.valueOf(modelo.resta()));
		}
	}
}
