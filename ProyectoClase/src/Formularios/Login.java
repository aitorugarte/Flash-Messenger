package Formularios;

import java.awt.BorderLayout;
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

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private JLabel lblUsuario;
	private JLabel lblContraseña;
	private JButton btnCancelar;
	private JButton btnIngresar;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(36, 40, 82, 23);
		contentPane.add(lblUsuario);

		textUsuario = new JTextField();
		textUsuario.setBounds(175, 37, 166, 29);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);

		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setBounds(17, 116, 101, 23);
		contentPane.add(lblContraseña);

		textPassword = new JPasswordField();
		textPassword.setBounds(175, 110, 166, 29);
		contentPane.add(textPassword);


		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(357, 196, 131, 31);
		contentPane.add(btnCancelar);

		btnIngresar = new JButton("Entrar");
		btnIngresar.addActionListener(new ActionListener() {
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
		btnIngresar.setBounds(36, 196, 131, 31);
		contentPane.add(btnIngresar);
		
		JButton btnIngresar_1 = new JButton("Ingresar");
		btnIngresar_1.setBounds(195, 196, 131, 31);
		contentPane.add(btnIngresar_1);
	}
}
