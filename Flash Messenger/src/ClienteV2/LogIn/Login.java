package ClienteV2.LogIn;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClienteV2.Cliente;
import ClienteV2.GUI_Cliente;
import ClienteV2.Principal_Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
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
import java.awt.Font;
import java.awt.HeadlessException;

/*
 * Clase que se encarga de iniciar sesión en la cuenta del cliente
 */
public class Login extends JFrame {

	private static final long serialVersionUID = 951887658616701680L;
	private JPanel contentPane;
	private JTextField textUsuario;
	protected JPasswordField textPassword;
	private JLabel lblUsuario, lblContraseña, lblLogin;
	private JButton btnSalir, btnIniciar, btnIngresar;
	public static String Ip_Servidor; //Dirección ip del servidor

	
	public static void main(String[] args) {

		Login frame = null;
		try {
			frame = new Login();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setVisible(true);

	}

	public Login() throws IOException {
		Comunicador comunicarse = new Comunicador(this);
		
		setTitle("Inicio sesi\u00F3n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 259, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(36, 40, 55, 23);
		contentPane.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setBounds(95, 41, 101, 23);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);

		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setBounds(20, 74, 73, 23);
		contentPane.add(lblContraseña);

		textPassword = new JPasswordField();
		textPassword.setBounds(95, 74, 101, 23);
		contentPane.add(textPassword);


		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setBounds(132, 137, 89, 31);
		contentPane.add(btnSalir);

		btnIniciar = new JButton("Entrar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String nombre = textUsuario.getText();
				char clave[] = textPassword.getPassword();
				String contraseña = new String(clave);
				
				try {
					comunicarse.conexion(nombre, contraseña);
					System.out.println("LogIn sin fallos");
				} catch (IOException e2) {
				System.out.println(e2);
				}			
			}
		});
		btnIniciar.setBounds(36, 137, 86, 31);
		contentPane.add(btnIniciar);
		
		btnIngresar = new JButton("Registrarse");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Registrarse registro = new Registrarse();
				registro.setVisible(true);
				textUsuario.setText("");
				textPassword.setText("");
			}
		});
		btnIngresar.setBounds(36, 179, 185, 23);
		contentPane.add(btnIngresar);
		
		lblLogin = new JLabel("LogIn");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setBounds(20, 11, 201, 18);
		contentPane.add(lblLogin);

	}
}
