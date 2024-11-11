package MVC_ejercicio18.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField textPassword;
	private JPanel panelSesion;
	private JPanel panelCRUD;
	private JTextField textNombre;
	private JTextField textLocalidad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 453);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 101, 22);
		contentPane.add(menuBar);
		
		JMenu mnConectar = new JMenu("Conectar");
		menuBar.add(mnConectar);
		
		JMenu mnConectarDB = new JMenu("Conectar bbdd");
		mnConectar.add(mnConectarDB);
		
		JMenuItem mntmOracle = new JMenuItem("Oracle");
		mnConectarDB.add(mntmOracle);
		
		JMenuItem mntmMySQL = new JMenuItem("MySQL");
		mnConectarDB.add(mntmMySQL);
		
		JMenuItem mntmSQLite = new JMenuItem("SQLite");
		mnConectarDB.add(mntmSQLite);
		
		JMenuItem mntmIniciarSesion = new JMenuItem("Iniciar Sesión");
		mnConectar.add(mntmIniciarSesion);
		
		JMenu mnSalir = new JMenu("Salir");
		mnSalir.setBounds(73, -4, 115, 26);
		contentPane.add(mnSalir);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Desconectar");
		mnSalir.add(mntmNewMenuItem);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSalir.add(mntmSalir);
		
		panelSesion = new JPanel();
		panelSesion.setBounds(21, 33, 184, 189);
		contentPane.add(panelSesion);
		panelSesion.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 25, 46, 14);
		panelSesion.add(lblNewLabel);
		
		textUser = new JTextField();
		textUser.setBounds(60, 22, 86, 20);
		panelSesion.add(textUser);
		textUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 57, 46, 14);
		panelSesion.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(10);
		textPassword.setBounds(60, 54, 86, 20);
		panelSesion.add(textPassword);
		
		JButton btnSesion = new JButton("Iniciar Sesión");
		btnSesion.setBounds(27, 101, 119, 23);
		panelSesion.add(btnSesion);
		
		panelCRUD = new JPanel();
		panelCRUD.setBounds(215, 33, 298, 333);
		contentPane.add(panelCRUD);
		panelCRUD.setLayout(null);
		
		textNombre = new JTextField();
		textNombre.setColumns(10);
		textNombre.setBounds(80, 28, 86, 20);
		panelCRUD.add(textNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 31, 46, 14);
		panelCRUD.add(lblNombre);
		
		textLocalidad = new JTextField();
		textLocalidad.setColumns(10);
		textLocalidad.setBounds(80, 59, 86, 20);
		panelCRUD.add(textLocalidad);
		
		JLabel lblNewLabel_1_1 = new JLabel("Localidad");
		lblNewLabel_1_1.setBounds(30, 62, 46, 14);
		panelCRUD.add(lblNewLabel_1_1);
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setBounds(176, 27, 95, 23);
		panelCRUD.add(btnNuevo);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(176, 55, 95, 23);
		panelCRUD.add(btnListar);
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setBounds(176, 83, 95, 23);
		panelCRUD.add(btnModificar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(176, 114, 95, 23);
		panelCRUD.add(btnBorrar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(176, 142, 95, 23);
		panelCRUD.add(btnGuardar);
	}
}
