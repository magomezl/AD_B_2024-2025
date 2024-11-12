package MVC_ejercicio18.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import MVC_ejercicio18.modelo.Departamento;
import MVC_ejercicio18.modelo.Modelo;
import MVC_ejercicio18.vista.Vista;


public class Controlador  {
	private Modelo modelo;
	private Vista vista;
	
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
		
		vista.btnNuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				limpiaControles();
			}
			
		});
		
		vista.btnGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modelo.anadirDpto(vista.textNombre.getText(), vista.textLocalidad.getText());
				rellenaTabla();
				limpiaControles();
			}
		});
		
		vista.btnListar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rellenaTabla();
			}
		});
		
		vista.btnBorrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int filaS = vista.tableResultados.getSelectedRow();
				modelo.borrarDpto(Integer.valueOf(vista.modeloTbl.getValueAt(filaS, 0).toString()));
				rellenaTabla();
				limpiaControles();
			}
			
		});
		
		vista.btnModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int filaS = vista.tableResultados.getSelectedRow();
				
				modelo.modificarDpto(Integer.valueOf(vista.modeloTbl.getValueAt(filaS, 0).toString()), 
						vista.textNombre.getText(), vista.textLocalidad.getText());
				rellenaTabla();
				limpiaControles();
			}
			
		});
		
		
		
		
		vista.tableResultados.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int filaS = vista.tableResultados.getSelectedRow();
				vista.textNombre.setText(vista.modeloTbl.getValueAt(filaS, 1).toString());
				vista.textLocalidad.setText(vista.modeloTbl.getValueAt(filaS, 2).toString());
			}
		});
		
		
		
	}

	protected void limpiaControles() {
		vista.textNombre.setText(null);
		vista.textLocalidad.setText(null);
	}

	protected void rellenaTabla() {
		vista.modeloTbl.setNumRows(0);
		ArrayList<Departamento> dptosListado = modelo.listarDptos();
		for(Departamento dpto: dptosListado) {
			Object[] fila = {dpto.getDepNum(), dpto.getDepNombre(), dpto.getDepLocalidad()};
			vista.modeloTbl.addRow(fila);
		}
	}
	
	
	
}
