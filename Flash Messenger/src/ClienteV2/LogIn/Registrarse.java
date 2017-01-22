package ClienteV2.LogIn;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ClienteV2.Principal_Cliente;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

/*
 * Clase del registro del usuario
 */
public class Registrarse extends JPanel {


	private static final long serialVersionUID = -6374924705506212721L;
	private JTextField txtNombredeusuario;
	private JPasswordField txtContraseña, txtRepitaContr;
	private JTextField txtCorreoElectronico;
	private JLabel lblNombredeusuario, lblContraseña, lblCorreoElectrnico, lblRepita, lblIntroduzca;
	private JButton btnAceptar;
	private String Ip_Servidor;

	private Comunicador comunicarse;

	public Registrarse() {
		setBackground(Color.BLACK);
		comunicarse = new Comunicador(this);
		Ini();
		Add();
		Comp();
		Actions();

	}

	private void Ini() {
		lblNombredeusuario = new JLabel("Nombre de usuario:");
		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblCorreoElectrnico = new JLabel("Correo electr\u00F3nico:");
		txtNombredeusuario = new JTextField();
		txtContraseña = new JPasswordField();
		txtCorreoElectronico = new JTextField();
		btnAceptar = new JButton("Aceptar");
		lblRepita = new JLabel("Repita contrase\u00F1a:");
		txtRepitaContr = new JPasswordField();
		lblIntroduzca = new JLabel("Introduzca los datos para registrarse: ");

	}

	private void Add() {
		setSize(350, 210);
		setLayout(null);

		setBorder(new EmptyBorder(5, 5, 5, 5));
		add(lblNombredeusuario);
		add(lblContraseña);
		add(lblCorreoElectrnico);
		add(txtNombredeusuario);
		add(txtContraseña);
		add(txtCorreoElectronico);
		add(btnAceptar);
		add(lblRepita);
		add(txtRepitaContr);
		add(lblIntroduzca);
	}

	private void Comp() {
		lblNombredeusuario.setForeground(Color.ORANGE);
		lblContraseña.setForeground(Color.ORANGE);
		lblCorreoElectrnico.setForeground(Color.ORANGE);
		lblRepita.setForeground(Color.ORANGE);
		lblIntroduzca.setForeground(Color.ORANGE);
		lblIntroduzca.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombredeusuario.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombredeusuario.setBounds(11, 27, 116, 23);
		lblContraseña.setHorizontalAlignment(SwingConstants.CENTER);
		lblContraseña.setBounds(11, 61, 116, 23);
		lblCorreoElectrnico.setHorizontalAlignment(SwingConstants.CENTER);
		lblCorreoElectrnico.setBounds(11, 130, 116, 23);
		txtNombredeusuario.setBounds(137, 27, 186, 23);
		txtNombredeusuario.setColumns(10);
		txtContraseña.setBounds(137, 62, 186, 23);
		txtCorreoElectronico.setBounds(137, 132, 186, 23);
		txtCorreoElectronico.setColumns(10);
		btnAceptar.setBounds(11, 176, 330, 23);
		lblRepita.setHorizontalAlignment(SwingConstants.CENTER);
		lblRepita.setBounds(11, 96, 116, 23);
		txtRepitaContr.setBounds(137, 93, 186, 23);
		lblIntroduzca.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzca.setBounds(1, 0, 349, 16);
	}

	private void Actions() {
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAceptarPressed();
			}
		});
	}

	private void btnAceptarPressed() {
		String nombre = txtNombredeusuario.getText();
		char clave[] = txtContraseña.getPassword();
		String contraseña = new String(clave);
		String correo = txtCorreoElectronico.getText();

		if (!nombre.trim().equals("")) {
			if (!correo.trim().equals("")) {
				if (comprobarPass() == true) {
					try {
						comunicarse.conexion(nombre, contraseña, correo);
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Error, las contraseñas no coindicen.", "Error",
							JOptionPane.ERROR_MESSAGE);
					limpiar(2);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Error, debe introducir un  correo electrónico.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error, debe introducir un nombre usuario.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public boolean comprobarPass() {

		char[] uno = txtContraseña.getPassword();
		char[] dos = txtRepitaContr.getPassword();

		if (uno.length == 0) {
			return false;
		} else {
			return Arrays.equals(uno, dos);
		}
	}

	public void limpiar(int a) {

		if (a == 1) {
			txtNombredeusuario.setText("");
			txtContraseña.setText("");
			txtRepitaContr.setText("");
			txtCorreoElectronico.setText("");
		}
		if (a == 2) {
			txtContraseña.setText("");
			txtRepitaContr.setText("");
		}
	}	
}
