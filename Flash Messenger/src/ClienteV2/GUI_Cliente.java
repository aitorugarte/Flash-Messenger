package ClienteV2;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.codec.binary.Base64;

import ClienteV2.Encriptado.CesarRecursivo;
import ServidorV2.Ventana_Servidor;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI_Cliente extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = 1458864985101903804L;
	private JPanel contentPane;
	private JSplitPane splitPane;
	private JPanel panUsus, panChat;
	private JButton btnEnviar;
	private JTextField textEnviar;
	private JLabel lblUsuariosConectados, lblConver;
	private JList list;
	private DefaultListModel<String> modelo;
	private JMenuBar menuBar;
	private JMenu MenuInicio, MenuAyuda, MenuAcercaD; 
	private JMenuItem mntmInicio, mntmDesarrollador;
	private JMenuItem Ayuda;
	private Cliente cliente;
	private String usuario;
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	private String seFue, entra, cargarTxt = "", cargarImg = "";
	private JFileChooser fileChooser;
	private JButton btnImg;
	private String I_linea = "<br>", F_linea = "</br>";
	private String I_tamanio = "<FONT SIZE="+4+">" , F_tamanio = "</FONT>";
	private String I_cursiva = "<I>", F_cursiva = "</I>";
	private String I_sub = "<SUB>", F_sub = "</SUB>";
	private String texto = "";
	
	
	public GUI_Cliente() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
			}
		});
		setTitle("GUI cliente");
		setResizable(false);
		IniciarFrame();
	
	}
	
	private void IniciarFrame(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 397);
		
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
		splitPane.setBounds(0, 0, 643, 347);
		contentPane.add(splitPane);
		
		panUsus = new JPanel();
		splitPane.setLeftComponent(panUsus);
		panUsus.setLayout(null);
		
		lblUsuariosConectados = new JLabel("Usuarios conectados");
		lblUsuariosConectados.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariosConectados.setBounds(10, 11, 139, 21);
		panUsus.add(lblUsuariosConectados);
		
		/*
		 * TODO Listado de clientes activos
		 */
		list = new JList();
		list.setBounds(10, 43, 139, 288);
		panUsus.add(list);
		
		panChat = new JPanel();
		splitPane.setRightComponent(panChat);
		panChat.setLayout(null);
		
		
		textEnviar = new JTextField();
		textEnviar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.VK_ENTER==e.getKeyCode()){
					String mensaje = textEnviar.getText();
					if(!mensaje.trim().equals("")){
						mostrarMensaje(1, mensaje);
						mensaje = CesarRecursivo.recorrer(1, "", mensaje, 0);
						mensaje = getUsuario() + "_" + mensaje;
						cliente.enviarTexto(mensaje);
						// Limpiamos el cuadro de texto del mensaje
						textEnviar.setText("");
					}
		        }
			}
		});
		textEnviar.setBounds(20, 308, 337, 26);
		panChat.add(textEnviar);
		textEnviar.setColumns(10);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(367, 308, 89, 26);
		btnEnviar.addActionListener(this);
		panChat.add(btnEnviar);
		
		lblConver = new JLabel();
		lblConver.setBounds(10, 11, 354, 17);
		lblConver.setHorizontalAlignment(SwingConstants.CENTER);
		panChat.add(lblConver);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 41, 436, 245);
		panChat.add(scrollPane);
		
		editorPane = new JEditorPane("text/html", "<html>");
		editorPane.setEditable(false);
		scrollPane.setViewportView(editorPane);
		
		btnImg = new JButton("Img");
		btnImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Valor que tomará el fileChooser
				int valor = fileChooser.showOpenDialog(null);
				String direccion = "";
				if (valor == JFileChooser.APPROVE_OPTION) {
					// Crear propiedades para ser utilizadas por fileChooser
					File archivoElegido = fileChooser.getSelectedFile();
					// Obteniendo la direccion del archivo
					direccion = archivoElegido.getPath();
				}
				mostrarImagen(1, direccion); 
				String tipo = direccion.substring(direccion.length() - 3);
				System.out.println(direccion + " " + tipo);
				cliente.enviarImagen(direccion, tipo);
			}
		});
		btnImg.setBounds(367, 11, 89, 23);
		panChat.add(btnImg);
	
		//Creamos el FileChooser
        fileChooser = new JFileChooser();
        //Le añadimos los filtros
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpg", "jpeg", "jpe", "jfif");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("PNG", "png");
        FileNameExtensionFilter filter3 = new FileNameExtensionFilter("TXT", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setFileFilter(filter2);
        fileChooser.setFileFilter(filter3);
        
		try {
			cliente = new Cliente(this);
			cliente.conexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnEnviar)){
			String mensaje = textEnviar.getText();
			if(!mensaje.trim().equals("")){
				mostrarMensaje(1, mensaje);
				mensaje = CesarRecursivo.recorrer(1, "", mensaje, 0);
				mensaje = getUsuario() + "_" + mensaje;
				cliente.enviarTexto(mensaje);
				// Limpiamos el cuadro de texto del mensaje
				textEnviar.setText("");
			}
		}
	}
	/**
	 * Método que muestra el mensaje por pantalla
	 * @param num mensaje enviado = 1, mensaje recibido = 2
	 * @param mensaje
	 */
	public void mostrarMensaje(int num, String mensaje){
		if(num == 1){
			cargarTxt = "<FONT SIZE="+4+"><DIV align=\"right\">" + getUsuario() + " => " + mensaje + "</DIV></FONT></br>";
		}else{
			System.out.println("Se debería de mostrar: "+ mensaje);
			cargarTxt = "<FONT SIZE="+4+"><DIV align=\"left\">" + mensaje + "</DIV></FONT></br>";
		}
		texto = texto + cargarTxt;
		editorPane.setText(texto + "<br>");
	}
	/**TODO método que resize las imagenes dependiendo si son cuadradas u horizontales
	 * Método que muestra la imagen por pantalla
	 * @param num imagen enviada = 1, imagen recibida = 2
	 * @param imagen dirección local de la imagen
	 * @param usuario que envía la imagen
	 */
	public void mostrarImagen(int num, String path){
		
		if(num == 1){
			cargarImg = "<DIV align=\"right\">" + getUsuario() + " => <img src='file:" + path + "' width=60 height=60></DIV></img></br>";
		}else{
           cargarImg = "<DIV align=\"left\"><FONT SIZE="+4+">"+ usuario + "<img src='file:"+ path + "' width=60 height=60></FONT></DIV></img></br>";
		}
		texto = texto + cargarImg;
		editorPane.setText(texto + "<br>");	
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setNombreUser(String user) {
		lblConver.setText("Usuario " + user);
		this.usuario = user;
	}

	
}
