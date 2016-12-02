package ServidorV2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.Charset;
import java.util.logging.Level;

import javax.swing.JButton;

/*
 * Clase encargada de enviar la ip del servidor cada 4 segundos
 */
public class Hilo_EnviarIp extends Thread {

	
	public void Enviar_Ip() throws IOException{
		
		while(true){
				
		try{
			Thread.sleep(4000);
		}catch (Exception e) {
			Registro.log( Level.SEVERE, "Error en la espera de los 4 segundos. ", e );
		}
		String Ip_Servidor = Inet4Address.getLocalHost().getHostAddress();
		
		
		//Convertimos los char en bytes
		byte[] b = Ip_Servidor.getBytes(Charset.forName("UTF-8"));
				
		MulticastSocket enviador = new MulticastSocket();

		// Usamos la direccion Multicast 230.0.0.1, por poner alguna dentro del rango
		// y el puerto 50000, uno cualquiera que esté libre.
		DatagramPacket dgp = new DatagramPacket(b, b.length, InetAddress.getByName("230.0.0.1"), 50000);

	
		enviador.send(dgp);
		}
	}
	
	
	public void run(){
		
		try {
			Enviar_Ip();
		} catch (IOException e) {
			Registro.log( Level.SEVERE, "Error al iniciar el hilo. ", e );
			e.printStackTrace();
		}
	}
	
}
