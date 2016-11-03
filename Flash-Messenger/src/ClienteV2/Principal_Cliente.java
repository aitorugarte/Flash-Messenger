package ClienteV2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class Principal_Cliente extends JFrame {

	private JScrollPane scrollpane;
	private JList<Object> list;
	private JButton btnIngresar;
	public static String Ip_Servidor = "";
	
	public Principal_Cliente() throws UnknownHostException, IOException {
		super("Lista");
		setResizable(false);
	
	    setSize(210, 200);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);

	    String ips[] = { DireccionIp()}; //Próximamente aparecerá una lista con más de una posibilidad
	    
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
	/*
	 * CUIDADO, si la ip no es la correcta, no salta ningún error
	 * Arreglar eso
	 */
	  	MiFrameCliente frame = new MiFrameCliente();
	  	frame.setVisible(true);
	  	
	   	}
	   });
	   btnIngresar.setBounds(54, 127, 84, 33);
	   getContentPane().add(btnIngresar);
	   

	}
	

	public static String DireccionIp() throws UnknownHostException, IOException{
		// El mismo puerto que se uso en la parte de enviar.
		MulticastSocket escucha = new MulticastSocket(50000);

		// Nos ponemos a la escucha de la misma IP de Multicast que se uso en la parte de enviar.
		escucha.joinGroup(InetAddress.getByName("230.0.0.1"));
							
		// Un array de bytes con tamaño suficiente para recoger el mensaje enviado
		byte [] dato = new byte [15];

		// Se espera la recepción. La llamada a receive() se queda bloqueada hasta que llegue un mesnaje.
		DatagramPacket dgp = new DatagramPacket(dato, dato.length);
		escucha.receive(dgp);
		dato = dgp.getData();
		
		String ip = new String(dato, "UTF-8");
	//	System.out.println("Ip recibida: " + ip);
		
		return ip;
	}
	
	public static void main(String args[]) throws IOException {
	
		BarraProgreso barra = new BarraProgreso();
		barra.setVisible(true);
	
		DireccionIp(); 
		
		barra.dispose();
		
		Principal_Cliente busqueda = new Principal_Cliente();
		busqueda.setVisible(true);

	}

}
