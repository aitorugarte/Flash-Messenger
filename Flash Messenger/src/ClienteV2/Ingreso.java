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
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Ingreso extends JFrame {

	
	private static final long serialVersionUID = -6374924705506212721L;
	private JPanel contentPane;
	private JTextField txtNombredeusuario;
	private JPasswordField txtContraseña;
	private JTextField txtCorreoElectronico;
	private JLabel lblNombredeusuario, lblContraseña, lblCorreoElectrnico, lblRepitaContrasea, lblIntroduzcaLosDatos;
	private JButton btnAceptar, btnCancelar;

	// creamos el fichero
	File fichero = new File("c:\\temp\\lineadecodigo\\fichero.txt");
	private JPasswordField passwordField;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ingreso frame = new Ingreso();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Ingreso() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 347, 281);
		// ajustar la imagen a la ventana

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNombredeusuario = new JLabel("Nombre de usuario:");
		lblNombredeusuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblNombredeusuario.setBounds(45, 41, 113, 23);
		contentPane.add(lblNombredeusuario);

		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setHorizontalAlignment(SwingConstants.LEFT);
		lblContraseña.setBounds(45, 75, 113, 23);
		contentPane.add(lblContraseña);

		lblCorreoElectrnico = new JLabel("Correo electr\u00F3nico:");
		lblCorreoElectrnico.setHorizontalAlignment(SwingConstants.LEFT);
		lblCorreoElectrnico.setBounds(45, 143, 113, 29);
		contentPane.add(lblCorreoElectrnico);

		txtNombredeusuario = new JTextField();
		txtNombredeusuario.setBounds(168, 41, 113, 23);
		contentPane.add(txtNombredeusuario);
		txtNombredeusuario.setColumns(10);

		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(168, 76, 123, 19);
		contentPane.add(txtContraseña);

		txtCorreoElectronico = new JTextField();
		txtCorreoElectronico.setBounds(168, 147, 154, 19);
		contentPane.add(txtCorreoElectronico);
		txtCorreoElectronico.setColumns(10);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Enhorabuena te has registrado");
			}
		});
		btnAceptar.setBounds(45, 200, 97, 31);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(179, 200, 97, 31);
		contentPane.add(btnCancelar);

		lblRepitaContrasea = new JLabel("Repita contrase\u00F1a:");
		lblRepitaContrasea.setHorizontalAlignment(SwingConstants.LEFT);
		lblRepitaContrasea.setBounds(45, 109, 113, 23);
		contentPane.add(lblRepitaContrasea);

		passwordField = new JPasswordField();
		passwordField.setBounds(168, 106, 123, 19);
		contentPane.add(passwordField);
		
		lblIntroduzcaLosDatos = new JLabel("Introduzca los datos para registrarse: ");
		lblIntroduzcaLosDatos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzcaLosDatos.setBounds(27, 11, 321, 19);
		contentPane.add(lblIntroduzcaLosDatos);
	}
}
