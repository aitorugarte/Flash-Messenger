package ServidorV2;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Principal_Servidor extends JFrame {

	private JTextArea log;

	public Principal_Servidor() {
		super("Flash Messenger");
		log = new JTextArea();

		this.setContentPane(new JScrollPane(log));
		setSize(350, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void mostrar(String mensaje) {
		log.append(mensaje + "\n");
	}

	public void runServer() {

		ServerSocket servidor1 = null;// para establecer la conexión
		ServerSocket servNick = null; // Para enviar el nombre
		ServerSocket servidor2 = null;// para enviar mensajes
		boolean activo = true;
		
		try {
			
			//Método que detecte puertos abiertos
			servidor1 = new ServerSocket(8080);
			servNick = new ServerSocket(8081);
			servidor2 = new ServerSocket(8083);

			mostrar("\t-------------------------------");
			mostrar("\tSERVIDOR ACTIVADO     ");
			mostrar("\t-------------------------------");

			
			while (activo) {
			
				Socket socket1 = null;
				Socket socketNick = null;
				Socket socket2 = null;
			
				try {
				
					socket1 = servidor1.accept();
					socketNick = servNick.accept();
					socket2 = servidor2.accept();
				} catch (IOException e) {
					mostrar("Error al unirse: " + servidor1 + ", " + e.getMessage());
					continue;
				}
				// Activa el servidor
			//	HiloServidor user = new HiloServidor(socket1, socketNick, socket2, this);
		//		user.start();
			}

		} catch (IOException e) {
			mostrar("Error :" + e);
		}
	}

	public static void main(String[] args) throws IOException {
		Hilo_Enviar hilo = new Hilo_Enviar();
		hilo.start();
		
		Principal_Servidor servidor = new Principal_Servidor();
		servidor.runServer();
	
	}

}
