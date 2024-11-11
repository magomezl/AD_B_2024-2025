package mvc_ejercicio18DEMO.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textOp1;
	public JTextField textOp2;
	public JTextField textResult;
	public JButton btnSumar;
	public JButton btnRestar;

	
	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 137);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textOp1 = new JTextField();
		textOp1.setBounds(50, 51, 86, 20);
		contentPane.add(textOp1);
		textOp1.setColumns(10);
		
		textOp2 = new JTextField();
		textOp2.setBounds(197, 51, 86, 20);
		contentPane.add(textOp2);
		textOp2.setColumns(10);
		
		textResult = new JTextField();
		textResult.setBounds(309, 51, 86, 20);
		contentPane.add(textResult);
		textResult.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("=");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(294, 52, 45, 19);
		contentPane.add(lblNewLabel);
		
		btnSumar = new JButton("+");
		
		
		btnSumar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSumar.setBounds(142, 30, 45, 29);
		contentPane.add(btnSumar);
		
		btnRestar = new JButton("-");
		btnRestar.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRestar.setBounds(142, 64, 45, 23);
		contentPane.add(btnRestar);
		
		setVisible(true);
		setTitle("MiniCalculadora");
	}
}
