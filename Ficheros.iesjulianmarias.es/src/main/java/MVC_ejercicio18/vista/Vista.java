package MVC_ejercicio18.vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JTextField textUser;
	public JPasswordField textPassword;
	public JPanel panelSesion;
	public JPanel panelCRUD;
	public JTextField textNombre;
	public JTextField textLocalidad;
	public JTable tableResultados;
	public DefaultTableModel modeloTbl = new DefaultTableModel(); 
	public JButton btnNuevo;
	public JButton btnListar;
	public JButton btnModificar;
	public JButton btnBorrar;
	public JButton btnGuardar;
	public JMenuItem mntmOracle;
	public JMenuItem mntmMySQL;
	public JMenuItem mntmSQLite;
	public JMenuItem mntmIniciarSesion;
	public JMenuItem mntmNewMenuItem;
	public JMenuItem mntmSalir;

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
		
		mntmOracle = new JMenuItem("Oracle");
		mnConectarDB.add(mntmOracle);
		
		mntmMySQL = new JMenuItem("MySQL");
		mnConectarDB.add(mntmMySQL);
		
		mntmSQLite = new JMenuItem("SQLite");
		mnConectarDB.add(mntmSQLite);
		
		mntmIniciarSesion = new JMenuItem("Iniciar Sesión");
		mnConectar.add(mntmIniciarSesion);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		mntmNewMenuItem = new JMenuItem("Desconectar");
		mnSalir.add(mntmNewMenuItem);
		
		mntmSalir = new JMenuItem("Salir");
		mnSalir.add(mntmSalir);
		
		panelSesion = new JPanel();
		panelSesion.setBounds(21, 33, 184, 189);
		contentPane.add(panelSesion);
		panelSesion.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(10, 25, 63, 14);
		panelSesion.add(lblNewLabel);
		
		textUser = new JTextField();
		textUser.setBounds(83, 22, 91, 20);
		panelSesion.add(textUser);
		textUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 57, 63, 14);
		panelSesion.add(lblPassword);
		
		textPassword = new JPasswordField();
		textPassword.setColumns(10);
		textPassword.setBounds(83, 54, 91, 20);
		panelSesion.add(textPassword);
		
		JButton btnSesion = new JButton("Iniciar Sesión");
		btnSesion.setBounds(27, 101, 119, 23);
		panelSesion.add(btnSesion);
		
		panelCRUD = new JPanel();
		panelCRUD.setBounds(215, 33, 298, 404);
		contentPane.add(panelCRUD);
		panelCRUD.setLayout(null);
		
		textNombre = new JTextField();
		textNombre.setColumns(10);
		textNombre.setBounds(94, 28, 72, 20);
		panelCRUD.add(textNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 31, 66, 14);
		panelCRUD.add(lblNombre);
		
		textLocalidad = new JTextField();
		textLocalidad.setColumns(10);
		textLocalidad.setBounds(94, 59, 72, 20);
		panelCRUD.add(textLocalidad);
		
		JLabel lblNewLabel_1_1 = new JLabel("Localidad");
		lblNewLabel_1_1.setBounds(10, 62, 66, 14);
		panelCRUD.add(lblNewLabel_1_1);
		
		btnNuevo = new JButton("Nuevo");
		
		btnNuevo.setBounds(176, 27, 95, 23);
		panelCRUD.add(btnNuevo);
		
		btnListar = new JButton("Listar");
		btnListar.setBounds(176, 55, 95, 23);
		panelCRUD.add(btnListar);
		
		btnModificar = new JButton("Modificar");
		btnModificar.setBounds(176, 83, 95, 23);
		panelCRUD.add(btnModificar);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(176, 114, 95, 23);
		panelCRUD.add(btnBorrar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(176, 142, 95, 23);
		panelCRUD.add(btnGuardar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 189, 244, 161);
		
		
		tableResultados = new JTable();
		tableResultados.setModel(modeloTbl);
		Object[] identificadores = {"Id", "Nombre", "Localidad"};
		modeloTbl.setColumnIdentifiers(identificadores);
		scrollPane.setViewportView(tableResultados);
		tableResultados.setFillsViewportHeight(true);
		
		
		tableResultados.setBounds(27, 189, 244, 161);
	
		panelCRUD.add(scrollPane);
		setTitle("CRUB DB");
		setVisible(true);
		
	}
}
