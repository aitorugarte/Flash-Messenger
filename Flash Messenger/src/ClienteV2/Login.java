package ClienteV2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Login extends JFrame {

	private static final long serialVersionUID = 951887658616701680L;
	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private JLabel lblUsuario, lblContraseña, lblLogin;
	private JButton btnSalir, btnIniciar, btnIngresar;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Login() {
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
		textUsuario.setBounds(95, 41, 101, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);

		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setBounds(20, 88, 73, 23);
		contentPane.add(lblContraseña);

		textPassword = new JPasswordField();
		textPassword.setBounds(95, 91, 101, 20);
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
				char clave[] = textPassword.getPassword();
				String clavedef = new String(clave);

				if (textUsuario.getText().equals("Administrador") && clavedef.equals("12345")) {

					Ingreso frame = new Ingreso();
					frame.setVisible(true);
					dispose();

					JOptionPane.showMessageDialog(null, "Bienvenido\n" + "Has ingresado satisfactoriamente al sistema",
							"Mensaje de bienvenida", JOptionPane.INFORMATION_MESSAGE);

				} else {

					JOptionPane.showMessageDialog(null,
							"Acceso denegado:\n" + "Por favor ingrese un usuario y/o contraseña correctos",
							"Acceso denegado", JOptionPane.ERROR_MESSAGE);

				}
			}
		});
		btnIniciar.setBounds(36, 137, 86, 31);
		contentPane.add(btnIniciar);
		
		btnIngresar = new JButton("Registrarse");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
