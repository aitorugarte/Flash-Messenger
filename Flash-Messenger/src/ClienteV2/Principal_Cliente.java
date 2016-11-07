package ClienteV2;

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

import ServidorV2.HiloServidor;

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
	
	public Principal_Cliente() throws UnknownHostException, IOException {
		super("Lista");
		setResizable(false);
	
	    setSize(210, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    String ips[] = {DireccionIp()}; //TODO (listado de + de 1 ip)
	    
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
	
	  	MiFrameCliente frame = new MiFrameCliente();
	  	frame.setVisible(true);
	  	
	   	}
	   });
	   btnIngresar.setBounds(54, 127, 84, 33);
	   getContentPane().add(btnIngresar);
	   

	}

	public static String DireccionIp() throws UnknownHostException, IOException{
		//Reseteamos las variables para evitar acumulaciones
		String ip = "";
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
		
		ip = new String(dato, "UTF-8");
		
		return ip;
	}
	
	public static void main(String args[]) throws IOException {
		// https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html
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
		BarraProgreso barra = new BarraProgreso();
		barra.setVisible(true);

		DireccionIp();

		barra.dispose();

		Principal_Cliente busqueda = new Principal_Cliente();
		busqueda.setVisible(true);

	}

}
