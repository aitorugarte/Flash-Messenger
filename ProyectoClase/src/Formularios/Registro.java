package Formularios;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Metodos.BaseDeDatos;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class Registro extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombredeusuario;
	private JPasswordField txtContraseña;
	private JTextField txtCorreoElectronico;
	private BaseDeDatos BDD = new BaseDeDatos();
	private JLabel lblNombredeusuario;
	private JLabel lblContraseña;
	private JLabel lblRepitaContrasea;
	private JLabel lblCorreoElectrnico;
	private JButton btnAceptar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Registro frame = new Registro();
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
	public Registro() {

		Botones();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 447);
		// ajustar la imagen a la ventana

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblNombredeusuario = new JLabel("Nombre de usuario:");
		lblNombredeusuario.setBounds(45, 41, 168, 29);
		contentPane.add(lblNombredeusuario);

		lblContraseña = new JLabel("Contrase\u00F1a:");
		lblContraseña.setBounds(45, 116, 136, 23);
		contentPane.add(lblContraseña);

		lblCorreoElectrnico = new JLabel("Correo electr\u00F3nico:");
		lblCorreoElectrnico.setBounds(45, 264, 158, 29);
		contentPane.add(lblCorreoElectrnico);

		txtNombredeusuario = new JTextField();
		txtNombredeusuario.setBounds(273, 41, 166, 29);
		contentPane.add(txtNombredeusuario);
		txtNombredeusuario.setColumns(10);

		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(271, 116, 168, 29);
		contentPane.add(txtContraseña);

		txtCorreoElectronico = new JTextField();
		txtCorreoElectronico.setBounds(273, 264, 166, 29);
		contentPane.add(txtCorreoElectronico);
		txtCorreoElectronico.setColumns(10);

		btnAceptar = new JButton("Aceptar");

		btnAceptar.setBounds(72, 343, 131, 31);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setBounds(319, 343, 131, 31);
		contentPane.add(btnCancelar);

		lblRepitaContrasea = new JLabel("Repita contrase\u00F1a:");
		lblRepitaContrasea.setBounds(45, 199, 158, 23);
		contentPane.add(lblRepitaContrasea);

		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(271, 196, 168, 29);
		contentPane.add(txtContraseña);
	}

	// comprobamos si se ha registrado el usuario
	private boolean IngresarUsuario() {
		if (BDD.IngresarUsuario(txtNombredeusuario.getText(), txtContraseña.getText(),
				txtCorreoElectronico.getText()) > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void Botones() {

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (IngresarUsuario() == true) {
					JOptionPane.showMessageDialog(null, "Enhorabuena te has registrado");
				}
			}
		});

	}

}
