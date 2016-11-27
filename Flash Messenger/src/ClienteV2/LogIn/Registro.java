package ClienteV2.LogIn;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class Registro extends JFrame {

	
	private static final long serialVersionUID = -6374924705506212721L;
	private JPanel contentPane;
	private JTextField txtNombredeusuario;
	private JPasswordField txtContraseña, txtRepitaContr;
	private JTextField txtCorreoElectronico;
	private JLabel lblNombredeusuario, lblContraseña, lblCorreoElectrnico, lblRepita, lblIntroduzca;
	private JButton btnAceptar, btnCancelar;
	private static BD_Local local = new BD_Local();
	private static Statement stat = null;
	private static Connection con = null;

	
	public static void main(String[] args) {
		//Iniciamos la base de datos
			con = local.initBD();
			stat = local.usarCrearTablasBD(con);
			
			Registro frame = new Registro();
			frame.setVisible(true);
	}

	
	public Registro() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		txtNombredeusuario.setBounds(168, 45, 123, 19);
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

				if (txtNombredeusuario.getText() != null) {
					if (txtCorreoElectronico.getText() != null) {
						if (comprobarPass() == true) {
							String nombre = txtNombredeusuario.getText();
							char clave[] = txtContraseña.getPassword();
							String contraseña = new String(clave);
							String correo = txtCorreoElectronico.getText();

							local.clienteInsert(stat, nombre, contraseña, correo);
							local.mostrarContenido(stat, con);
							JOptionPane.showMessageDialog(null, "Registro completo.");
							limpiar(1);

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
				System.exit(0);
			}
		});
		btnCancelar.setBounds(179, 200, 97, 31);
		contentPane.add(btnCancelar);

		lblRepita = new JLabel("Repita contrase\u00F1a:");
		lblRepita.setHorizontalAlignment(SwingConstants.LEFT);
		lblRepita.setBounds(45, 109, 113, 23);
		contentPane.add(lblRepita);

		txtRepitaContr = new JPasswordField();
		txtRepitaContr.setBounds(168, 106, 123, 19);
		contentPane.add(txtRepitaContr);
		
		lblIntroduzca = new JLabel("Introduzca los datos para registrarse: ");
		lblIntroduzca.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIntroduzca.setBounds(27, 11, 304, 19);
		contentPane.add(lblIntroduzca);
	}
	
	public boolean comprobarPass(){
		
		char[] uno = txtContraseña.getPassword();
		char[] dos = txtRepitaContr.getPassword();
		
		if(uno.length == 0){
			return false;
		}else{
			return Arrays.equals(uno, dos);
		}	
	}
	
	public void limpiar(int a){
		
		if(a == 1){
			txtNombredeusuario.setText("");
			txtContraseña.setText("");
			txtRepitaContr.setText("");
			txtCorreoElectronico.setText("");
		}
		if(a == 2){
			txtContraseña.setText("");
			txtRepitaContr.setText("");
		}
	}
}
