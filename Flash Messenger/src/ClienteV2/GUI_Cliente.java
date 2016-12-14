package ClienteV2;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ServidorV2.Ventana_Servidor;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;

public class GUI_Cliente extends JFrame implements KeyListener, ActionListener {

	
	private static final long serialVersionUID = 1458864985101903804L;
	private JPanel contentPane;
	private JSplitPane splitPane;
	private JPanel panUsus, panChat;
	private JButton btnEnviar;
	private JTextField textEnviar;
	private JTextArea PanMsg;
	private JLabel lblUsuariosConectados, lblConver;
	private JList list;
	private DefaultListModel<String> modelo;
	private JMenuBar menuBar;
	private JMenu MenuInicio, MenuAyuda, MenuAcercaD; 
	private JMenuItem mntmInicio, mntmDesarrollador;
	private JMenuItem Ayuda;
	private Cliente cliente;
	private String usuario;
	
	
	public GUI_Cliente() {
		setTitle("GUI cliente");
		setResizable(false);
		IniciarFrame();
	
	}
	
	private void IniciarFrame(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 397);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		MenuInicio = new JMenu("Inicio");
		menuBar.add(MenuInicio);
		
		mntmInicio = new JMenuItem("Inicio");
		MenuInicio.add(mntmInicio);
		
		MenuAyuda = new JMenu("Ayuda");
		menuBar.add(MenuAyuda);
		
		Ayuda = new JMenuItem("Instrucciones");
		MenuAyuda.add(Ayuda);
		
		MenuAcercaD = new JMenu("Acerca de");
		menuBar.add(MenuAcercaD);
		
		mntmDesarrollador = new JMenuItem("Desarrollador");
		MenuAcercaD.add(mntmDesarrollador);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.25);
		splitPane.setBounds(0, 0, 704, 358);
		contentPane.add(splitPane);
		
		panUsus = new JPanel();
		splitPane.setLeftComponent(panUsus);
		panUsus.setLayout(null);
		
		lblUsuariosConectados = new JLabel("Usuarios conectados");
		lblUsuariosConectados.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariosConectados.setBounds(10, 11, 154, 21);
		panUsus.add(lblUsuariosConectados);
		
		/*
		 * TODO Listado de clientes activos
		 */
		list = new JList();
		list.setBounds(10, 43, 154, 288);
		panUsus.add(list);
		
		panChat = new JPanel();
		splitPane.setRightComponent(panChat);
		panChat.setLayout(null);
		
		
		textEnviar = new JTextField();
		textEnviar.setBounds(20, 308, 387, 23);
		panChat.add(textEnviar);
		textEnviar.setColumns(10);
		textEnviar.addKeyListener(this);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(417, 308, 89, 23);
		btnEnviar.addActionListener(this);
		panChat.add(btnEnviar);
		
		lblConver = new JLabel();
		lblConver.setBounds(10, 11, 496, 17);
		lblConver.setHorizontalAlignment(SwingConstants.CENTER);
		panChat.add(lblConver);
		
		PanMsg = new JTextArea();
		PanMsg.setForeground(Color.BLACK);
		PanMsg.setEditable(false);
		PanMsg.setBounds(20, 39, 478, 247);
		panChat.add(PanMsg);
	
		try {
			cliente = new Cliente(this);
			cliente.conexion();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	public void mostrarMsg(String msg) {	
		this.PanMsg.append(getUsuario() + " => " + msg + "\n"); //TODO if getUsuario == thisUsuario => Tú
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setNombreUser(String user) {
		lblConver.setText("Usuario " + user);
		this.usuario = user;
	}

	@Override
	public void keyReleased(KeyEvent e2) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	
		
	}

	@Override
	public void keyPressed(KeyEvent key) {
		if (key.VK_ENTER==key.getKeyCode()){
			String mensaje = textEnviar.getText();
			if(!mensaje.trim().equals("")){
				cliente.flujo(mensaje);
				mostrarMsg(mensaje);
				// Limpiamos el cuadro de texto del mensaje
				textEnviar.setText("");
			}
        }
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnEnviar)){
			String mensaje = textEnviar.getText();
			if(!mensaje.trim().equals("")){
				cliente.flujo(mensaje);
				mostrarMsg(mensaje);
				// Limpiamos el cuadro de texto del mensaje
				textEnviar.setText("");
			}
		}
	}
}
