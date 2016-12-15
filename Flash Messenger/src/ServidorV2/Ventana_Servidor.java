package ServidorV2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ServidorV2.BD.BD_Local;
import ServidorV2.BD.BD_Padre;
import ServidorV2.BD.BD_Remota;
import ServidorV2.BD.Utilidades_BD;
import ServidorV2.Logger.Log_errores;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Level;
import java.awt.event.ActionEvent;

/*
 * Clase principal del Servidor
 */
public class Ventana_Servidor extends JFrame {

 
	private static final long serialVersionUID = 930849024921895057L;
	private JPanel contentPane;
	private JLabel lblPanelDeControl, lblEstado;
	private JButton btnDesconectar, btnUsuarios, btnRegistroDeMensajes, btnBD;
	private String ip; //ip del lciente
	private BD_Remota remota;
	private BD_Local local;
	private static Connection conex = null;
	private static Statement stat = null;
	private static BD_Padre padre = new BD_Padre(conex, stat);


	public Ventana_Servidor() {
		setTitle("Flash Messenger");
		setAutoRequestFocus(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 273, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPanelDeControl = new JLabel("Panel de control del servidor");
		lblPanelDeControl.setBounds(5, 5, 247, 36);
		lblPanelDeControl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPanelDeControl.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblPanelDeControl);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnDesconectar.setBounds(74, 227, 113, 23);
		contentPane.add(btnDesconectar);
		
		btnUsuarios = new JButton("Usuarios activos");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ListaActivos lista = new ListaActivos();
				lista.agregarNombre();
				lista.setVisible(true);
			}
		});
		btnUsuarios.setBounds(10, 75, 143, 48);
		contentPane.add(btnUsuarios);
		
		btnRegistroDeMensajes = new JButton("Log");
		btnRegistroDeMensajes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,"Próximamente...", "Not today", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnRegistroDeMensajes.setBounds(163, 75, 89, 48);
		contentPane.add(btnRegistroDeMensajes);
		
		lblEstado = new JLabel("Conectado a internet => " + Test());
		lblEstado.setBounds(55, 183, 166, 23);
		contentPane.add(lblEstado);
		
		btnBD = new JButton("Base de datos");
		btnBD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Utilidades_BD utilidades = new Utilidades_BD();
				utilidades.setVisible(true);
				utilidades.setDatos(stat, conex);
			}
		});
		btnBD.setBounds(74, 134, 136, 38);
		contentPane.add(btnBD);

	}
	
	//Método que busca el usuario y la contraseña en la BD
	public static boolean buscarUsuario(String usuario){
		boolean hay = padre.existeUsuario(usuario, stat, conex);
		return hay;
	}
	
	/*
	 * Método que divide la cadena de texto para ser insertada
	 */
	public static void dividir(String algo) {

		String nombre = "";
		String contraseña = "";
		String correo = "";

		// Encontar la posición de los espacios
		int espacio = 0;
		int espacio2 = 0;

		for (int i = 0; i < algo.length(); i++) {

			if (algo.charAt(i) == ' ') {
				if (espacio == 0) {
					espacio = i;
				} else {
					espacio2 = i;
				}
			}
		}
		// Formamos las palabras
		int x = 0;
		while (x < espacio) {

			nombre = nombre + algo.charAt(x);
			x++;
		}
		espacio = espacio + 1;

		while (espacio < espacio2) {

			contraseña = contraseña + algo.charAt(espacio);
			espacio++;
		}
		espacio2 = espacio2 + 1;
		while (espacio2 < algo.length()) {

			correo = correo + algo.charAt(espacio2);
			espacio2++;
		}

		padre.clienteInsert(stat, nombre, contraseña, correo);

	}
	
	/*
	 * Mëtodo que corre el servidor
	 */
	public void runServer() {

		ServerSocket servidor1 = null;// para establecer la conexión
		ServerSocket servidor2 = null;// para enviar mensajes
	
		boolean activo = true;
		
		try {

			servidor1 = new ServerSocket(8080);
			servidor2 = new ServerSocket(8083);
			
			
			while (activo) {
			
				Socket socket1 = null;
				Socket socket2 = null;
			
				try {
					
					socket1 = servidor1.accept();
					socket2 = servidor2.accept();				
					
				} catch (IOException e) {
					Log_errores.log( Level.SEVERE, "Error al unirse al servidor: " + e.getMessage(), e );
				    JOptionPane.showInputDialog("Error al unirse al servidor : " + e.getMessage());
					continue;
				}
				
				ip = (((InetSocketAddress)socket1.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
				
				//Activamos el usuario (nombre, puerto1, puerto2, la ventana, su ip);
				H_Servidor user = new H_Servidor(BD_Padre.nomb, socket1, socket2, this, ip);
				user.start();
			}

		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error " + e.getMessage(), e );
			JOptionPane.showInputDialog("Error: " + e.getMessage());
		}
	}
	
	/*
	 * Método que comprueba si hay internet
	 */
	public boolean Test(){
		
		if(padre.TestInternet() == true){
			remota = BD_Remota.getBD();
			stat = remota.getStat();
			conex = remota.getConexion();
			return true;
		}else{
			local = BD_Local.getBD();
			local.crearTablasBD();
			stat = local.getStat();
			conex = local.getConexion();
			return false;
		}
		
	}
	
	/*
	 * Main del programa Servidor
	 */
	public static void main(String[] args) throws IOException {

			try {
					for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(info.getName())) {
							UIManager.setLookAndFeel(info.getClassName());
							break;
						}
					}
				} catch (Exception e) {
					Log_errores.log( Level.SEVERE, "Nimbus no está operativo.", e );
					// If Nimbus is not available, you can set the GUI to another look
					// and feel.
				}
			
		H_EnviarIp enviar = new H_EnviarIp();
		enviar.start();
	
		H_Comunicacion comunicarse = new H_Comunicacion();
		comunicarse.start();
		
		Ventana_Servidor servidor = new Ventana_Servidor();
		servidor.setVisible(true);
		servidor.runServer();
	}
}
