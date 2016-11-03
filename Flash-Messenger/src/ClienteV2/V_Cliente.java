package ClienteV2;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;


public class V_Cliente extends JFrame implements ActionListener{

	// private String mensajeCliente;
	private JTextArea panMsg; // Panel en el que aparece la conversación
	private JTextField txtMsg;
	private JButton butEnviar;
	private JLabel lblNomUser, lblIngrese;
	private Cliente cliente;

	private JMenuBar barraOp;
	private JMenu JMInicio;
	private JMenuItem ini;
	private JMenu JMAyuda;
	private JMenuItem help;
	private JMenu JMAcerca;
	private JMenuItem acercaD;
//	private V_Ayuda va;

	
	/** Creates a new instance of Cliente */
	public V_Cliente() throws IOException {
		super("GUI cliente");
		setResizable(false);

		// Barra de opciones (parte superior)
		barraOp = new JMenuBar();
		barraOp.setBounds(0, 0, 434, 21);

		JMInicio = new JMenu("Inicio");
		ini = new JMenuItem("Inicio");
		ini.setActionCommand("inicio");
		ini.addActionListener(this);

		JMAyuda = new JMenu("Ayuda");
		help = new JMenuItem("Ayuda");
		help.setActionCommand("help");
		help.addActionListener(this);

		JMInicio = new JMenu("Inicio");
		JMAcerca = new JMenu("Acerca de");
		acercaD = new JMenuItem("Creditos");
		acercaD.setActionCommand("Acerca");
		acercaD.addActionListener(this);

		JMInicio.add(ini);
		JMAyuda.add(help);
		JMAcerca.add(acercaD);
		barraOp.add(JMInicio);
		barraOp.add(JMAcerca);
		barraOp.add(JMAyuda);
		getContentPane().setLayout(null);
		getContentPane().add(barraOp);

		lblNomUser = new JLabel("Usuario ");
		lblNomUser.setFont(new Font("Arial", Font.BOLD, 12));
		lblNomUser.setForeground(Color.BLACK);
		lblNomUser.setBackground(Color.WHITE);
		lblNomUser.setBounds(0, 22, 434, 23);
		getContentPane().add(lblNomUser);
		lblNomUser.setHorizontalAlignment(JLabel.CENTER);
		panMsg = new JTextArea();
		panMsg.setBounds(10, 48, 413, 287);
		getContentPane().add(panMsg);
		panMsg.setColumns(25);

		panMsg.setEditable(false);
		panMsg.setForeground(Color.BLACK);
		panMsg.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(25, 10, 80)));

		lblIngrese = new JLabel("Ingrese mensaje a enviar:");
		lblIngrese.setFont(new Font("Arial", Font.BOLD, 12));
		lblIngrese.setBounds(10, 346, 424, 23);
		getContentPane().add(lblIngrese);
		butEnviar = new JButton("Enviar");
		butEnviar.setBounds(356, 368, 78, 22);
		getContentPane().add(butEnviar);

		txtMsg = new JTextField(30);
		txtMsg.setBounds(10, 369, 345, 20);
		getContentPane().add(txtMsg);
		txtMsg.addActionListener(this);

		txtMsg.requestFocus();// pedir el focus
		butEnviar.addActionListener(this);

	//	cliente = new Cliente(this);
		cliente.conexion();

		setSize(439, 430);
		setLocation(120, 90);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void setNombreUser(String user) {
		lblNomUser.setText("Usuario " + user);
	}

	public void mostrarMsg(String msg) {
		this.panMsg.append(msg + "\n");
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String comand = (String) event.getActionCommand();
		if (comand.compareTo("help") == 0) {
		//	va = new V_Ayuda();
			//va.setVisible(true);
		}
		if (comand.compareTo("Acerca") == 0) {
			JOptionPane.showMessageDialog(this, "Universidad de Deusto", "Desarrollado por", JOptionPane.INFORMATION_MESSAGE);
		}
		if (event.getSource() == this.butEnviar || event.getSource() == this.txtMsg) {
			String mensaje = txtMsg.getText();
			cliente.flujo(mensaje);
			txtMsg.setText("");
		}
	}
	}

