package ClaseActualizadaYElTest;

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
import java.awt.Font;

/*
 * Clase del registro del usuario
 */
public class Registrarse extends JFrame {

	private static final long serialVersionUID = -6374924705506212721L;
	private JPanel contentPane;
	private JTextField txtNombredeusuario;
	private JPasswordField txtContraseña, txtRepitaContr;
	private JTextField txtCorreoElectronico;
	private JLabel lblNombredeusuario, lblContraseña, lblCorreoElectrnico, lblRepita, lblIntroduzca;
	private JButton btnAceptar, btnCancelar;
	private String Ip_Servidor;
	public char[] uno;
	public char[] dos;

	// getters de los dos arrays char

	public char[] getUno() {
		return uno;
	}

	public void setUno(char c) {
		this.uno = uno;
	}

	public char[] getDos() {
		return dos;
	}

	public void setDos(char c) {
		this.dos = dos;
	}

	public Registrarse() {
		Comunicador comunicarse = new Comunicador(this);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 347, 281);

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
		txtNombredeusuario.setBounds(168, 45, 123, 23);
		contentPane.add(txtNombredeusuario);
		txtNombredeusuario.setColumns(10);

		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(168, 76, 123, 23);
		contentPane.add(txtContraseña);

		txtCorreoElectronico = new JTextField();
		txtCorreoElectronico.setBounds(168, 147, 154, 25);
		contentPane.add(txtCorreoElectronico);
		txtCorreoElectronico.setColumns(10);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
						JOptionPane.showMessageDialog(null, "Error, debe introducir un nombre usuario.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Error, debe introducir un correo electrónico.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(45, 200, 97, 31);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(179, 200, 97, 31);
		contentPane.add(btnCancelar);

		lblRepita = new JLabel("Repita contrase\u00F1a:");
		lblRepita.setHorizontalAlignment(SwingConstants.LEFT);
		lblRepita.setBounds(45, 109, 113, 23);
		contentPane.add(lblRepita);

		txtRepitaContr = new JPasswordField();
		txtRepitaContr.setBounds(168, 106, 123, 23);
		contentPane.add(txtRepitaContr);

		lblIntroduzca = new JLabel("Introduzca los datos para registrarse: ");
		lblIntroduzca.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzca.setBounds(27, 11, 304, 19);
		contentPane.add(lblIntroduzca);
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
