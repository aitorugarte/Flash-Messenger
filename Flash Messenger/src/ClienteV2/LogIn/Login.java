package ClienteV2.LogIn;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClienteV2.GUI_Cliente;
import ClienteV2.Principal_Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;

/*
 * Clase que se encarga de iniciar sesión en la cuenta del cliente
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 951887658616701680L;
	private JPanel contentPane;
	private JTextField textUsuario;
	protected JPasswordField textPassword;
	private JLabel lblUsuario, lblContraseña, lblLogin;
	private JButton btnSalir, btnIniciar;
	private JToggleButton btnIngresar;
	public static String Ip_Servidor; // Dirección ip del servidor

	private Comunicador comunicarse;
	private JScrollPane scrollPane_Registro;
	private Registrarse registro;

	int x[] = { 20, 330, 350, 350, 330, 20, 0, 0, 20 };
	int y[] = { 0, 0, 20, 170, 190, 190, 170, 20, 0 };
	private PanelForm PF = new PanelForm(352, 202, x, y, "images/fondoo.jpg");

	public Login() throws IOException {
		comunicarse = new Comunicador(this);
		Ini();
		Add();
		Comp();
		Actions();
	}

	private void Ini() {
		contentPane = new JPanel();
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textUsuario = new JTextField();
		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textPassword = new JPasswordField();
		btnSalir = new JButton("Salir");
		btnIniciar = new JButton("Entrar");
		btnIngresar = new JToggleButton("Registrarse");
		lblLogin = new JLabel("LogIn");
		
	}

	private void Add() {
		setTitle("Inicio sesi\u00F3n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(352, 202);
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		setUndecorated(true);

//		 contentPane.setLayout(null);
//		 contentPane.add(lblUsuario);
//		 contentPane.add(textUsuario);
//		 contentPane.add(lblContraseña);
//		 contentPane.add(textPassword);
//		 contentPane.add(btnSalir);
//		 contentPane.add(btnIniciar);
//		 contentPane.add(btnIngresar);
//		 contentPane.add(lblLogin);

		setBackground(new Color(0, 0, 0, 0));
		PF.setLayout(null);
		PF.add(lblUsuario);
		PF.add(textUsuario);
		PF.add(lblContraseña);
		PF.add(textPassword);
		PF.add(btnSalir);
		PF.add(btnIniciar);
		PF.add(btnIngresar);
		PF.add(lblLogin);
		contentPane.add(PF);

	}

	private void Comp() {
		lblUsuario.setForeground(Color.BLACK);
		lblContraseña.setForeground(Color.BLACK);
		lblLogin.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(20, 11, 320, 18);
		lblUsuario.setBounds(36, 40, 55, 23);
		textUsuario.setBounds(95, 40, 215, 23);
		textUsuario.setColumns(10);
		lblContraseña.setBounds(20, 74, 73, 23);
		textPassword.setBounds(95, 74, 215, 23);
		btnSalir.setBounds(184, 108, 89, 31);
		btnIniciar.setBounds(88, 108, 89, 31);
		btnIngresar.setBounds(10, 150, 330, 29);
	}

	private void Actions() {
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String nombre = textUsuario.getText();
				char clave[] = textPassword.getPassword();
				String contraseña = new String(clave);

				try {
					comunicarse.conexion(nombre, contraseña);
				} catch (IOException e2) {
					System.out.println(e2);
				}
			}
		});

		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Cuando se pulse el botón aumentará el size del login y se
				// mostrará el panel del registro:

				if (btnIngresar.isSelected()) {

					// INI:
					registro = new Registrarse();
					scrollPane_Registro = new JScrollPane();

					// ADD:
					scrollPane_Registro.setBounds(1, 20, 349, 210);
					scrollPane_Registro.setViewportView(registro);
					scrollPane_Registro.setBorder(null);

					// Cambiamos texto del botón:
					btnIngresar.setText("Cancelar registro");

					// Aumentamos size:
					int x[] = { 20, 330, 350, 350, 330, 20, 0, 0, 20 };
					int y[] = { 0, 0, 20, 230, 250, 250, 230, 20, 0 };
					PF = new PanelForm(352, 262, x, y, "images/fondoo.jpg");
					PF.setLayout(null);
					PF.add(scrollPane_Registro);					
					contentPane.add(PF);
					setSize(352, 472);
				} else {
					// Cambiamos texto del botón:
					btnIngresar.setText("Registrarse");
					setSize(352, 202);
				}
			}
		});
	}
}
