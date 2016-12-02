package ServidorV2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/*
 * Clase encargada de recibir el re
 */
public class Hilo_Recibir extends Thread {

	private Ventana_Servidor servidor = new Ventana_Servidor();

	public void recibirDatos() throws SocketException, UnknownHostException {

		while(true){
		byte[] b = new byte[15];
		DatagramSocket socket = new DatagramSocket(5000, InetAddress.getByName("localhost"));
		DatagramPacket dato = new DatagramPacket(b, b.length);

		try {
			System.out.println("Esperando dato...");
			socket.receive(dato);
			System.out.println("Dato recibido");
		} catch (IOException e) {
			e.printStackTrace();
		}
		socket.close();
		String usuario = " ";
		try {
			usuario = new String(b, "UTF-8");
			System.out.println(usuario);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		boolean existe = servidor.buscarUsuario(usuario);
		servidor.responder(existe);
		}
	}

	public void run() {

		try {
			recibirDatos();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}

	}
}
