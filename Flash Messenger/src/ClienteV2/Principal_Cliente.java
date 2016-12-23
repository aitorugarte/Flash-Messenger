package ClienteV2;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ClienteV2.LogIn.Login;

import javax.swing.JButton;

/*
 * Clase principal del cliente
 */
public class Principal_Cliente extends JFrame {

	
	private static final long serialVersionUID = 9107088636470185138L;
	private JScrollPane scrollpane;
	private JList<Object> list;
	private JButton btnIngresar;
	public static String Ip_Servidor;
	private static String ip = "";
	
	public Principal_Cliente() throws UnknownHostException, IOException {
		super("Lista");
		setResizable(false);
	    setSize(210, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    String ips[] = {DireccionIp()};
	    
	   list = new JList<Object>(ips);
	    
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

	public static String DireccionIp() throws UnknownHostException, IOException{
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
		setIp(ip);
			
		return ip;
		
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

		DireccionIp();

		barra.dispose();

		Principal_Cliente busqueda = new Principal_Cliente();
		busqueda.setIconImage(icon);
		busqueda.setVisible(true);

	}

}
