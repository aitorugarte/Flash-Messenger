package ServidorV2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

/*
 * Clase encargada de recibir los datos del LogIn
 */
public class H_Comunicacion extends Thread {

	public void recibirDatos() throws IOException {

		Ventana_Servidor servidor = new Ventana_Servidor();
		
		while (true) {
			ServerSocket sc = null;
			Socket recibir = null;
			Socket enviar = null;

			sc = new ServerSocket(5000);
			recibir = new Socket();

			recibir = sc.accept();

			DataInputStream entrada = new DataInputStream(recibir.getInputStream());

			String datosRecibidos = entrada.readUTF();
			System.out.println("Datos recibidos: " + datosRecibidos);
			String respuesta = " ";
			if(servidor.buscarUsuario(datosRecibidos)){
				respuesta = "ok";
			}else{
				respuesta = "no";
			}
			
			enviar = new Socket("localhost", 5001);
			DataOutputStream salida = new DataOutputStream(enviar.getOutputStream());
			
			salida.writeUTF(respuesta);
			salida.flush();

			//Cerramos los cokets
			sc.close();
			recibir.close();
			enviar.close();

		}

	}

	public void run() {

		try {
			recibirDatos();
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}
