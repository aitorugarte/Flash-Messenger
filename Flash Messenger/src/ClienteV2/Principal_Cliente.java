package ClienteV2;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListDataListener;

import ClienteV2.BD.Conexion;
import ClienteV2.LogIn.Login;

import javax.swing.DefaultListModel;
import javax.swing.JButton;

/*
 * Clase principal del cliente
 */
public class Principal_Cliente extends JFrame {

	
	private static final long serialVersionUID = 9107088636470185138L;
	private JScrollPane scrollpane;
	private JList<String> list;
	private static DefaultListModel<String> modelo = new DefaultListModel<String>();
	private JButton btnIngresar;
	public static String Ip_Servidor;
	private static String ip = "";
	public static ArrayList<String> direcciones = new ArrayList<String>();
	
	public Principal_Cliente() throws UnknownHostException, IOException {
		super("Lista");
		setResizable(false);
	    setSize(210, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	   list = new JList<String>(modelo);
	    
	   scrollpane = new JScrollPane(list);
	   scrollpane.setBounds(0, 0, 204, 116);
	   scrollpane.setVisible(true);
	   getContentPane().setLayout(null);
	   getContentPane().add(scrollpane);
	   
	   btnIngresar = new JButton("Ingresar");
	   btnIngresar.addActionListener(new ActionListener() {
	   	public void actionPerformed(ActionEvent arg0) {

	  	dispose();
	  	Ip_Servidor = ""+list.getSelectedValue();
	  	setIp(Ip_Servidor);
	  	
	  	Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");
	  	Login inicio = null;
		try {
			inicio = new Login();
		} catch (IOException e) {
			e.printStackTrace();
		}
		inicio.setIconImage(icon);
	  	inicio.setVisible(true);  	
	   	}
	   });
	   btnIngresar.setBounds(54, 127, 84, 33);
	   getContentPane().add(btnIngresar);
	   

	}
	private static Timer duracion;
	/*
	 * Método que recibe la dirección ip del servidor
	 */
	private void StartSimulacion(ActionEvent evt) {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

	
			}
		};

		duracion = new Timer(20, taskPerformer);
		duracion.start();
	}

	public static void DireccionIp() throws UnknownHostException, IOException{

				//Reseteamos las variables para evitar acumulaciones
				DatagramPacket dgp = null;

				// El mismo puerto que se uso en la parte de enviar.
				MulticastSocket escucha = new MulticastSocket(50000);
				
				// Nos ponemos a la escucha de la misma IP de Multicast que se uso en la parte de enviar.
				escucha.joinGroup(InetAddress.getByName("230.0.0.1"));
									
				// Un array de bytes con tamaño suficiente para recoger el mensaje enviado
				byte [] dato = new byte [15];

				// Se espera la recepción. La llamada a receive() se queda bloqueada hasta que llegue un mesnaje.
				dgp = new DatagramPacket(dato, dato.length);
				escucha.receive(dgp);
				dato = dgp.getData();
				//escucha.close();
				ip = new String(dato, "UTF-8");
				direcciones.add(ip);
	}

	public String getIp() {
		return ip;
	}

	public static void setIp(String ip) {
		Principal_Cliente.ip = ip;
	}

	public static void main(String args[]) throws IOException {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");
	
		BarraProgreso barra = new BarraProgreso();
		barra.setIconImage(icon);
		barra.setVisible(true);

		Conexion conexion = new Conexion();
		
		if(conexion.TestInternet() == true){
			Connection con = conexion.initBD();
			Statement stat = conexion.usarBD(con);
			conexion.servidorObtener(stat);
			
			for (int i = 0; i < conexion.getIps().size(); i++) {
				modelo.addElement(conexion.ips.get(i));
			}
		}else{
			DireccionIp();
			for (int i = 0; i < direcciones.size(); i++) {
				modelo.addElement(direcciones.get(i));
			}
		}

		barra.dispose();

		Principal_Cliente busqueda = new Principal_Cliente();
		busqueda.setIconImage(icon);
		busqueda.setVisible(true);

	}

}
