package ServidorV2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;

/*
 * Clase del hilo del servidor
 */
public class H_Servidor extends Thread {

	private Socket Scli; // Socket de entrada de mensajes
	private Socket Scli2; // Socket de salida de mensajes
	private DataInputStream entrada;
	private DataOutputStream salida2;
	public static Vector<H_Servidor> clientesActivos = new Vector<H_Servidor>();
	private String nombre;
	private Ventana_Servidor servi;
	private String direccion;

	public H_Servidor(Socket Scliente, Socket Scliente2, Ventana_Servidor servi, String ip) {

		Scli = Scliente;
		Scli2 = Scliente2;
		this.servi = servi;
		nombre = "";
		direccion = ip;
		clientesActivos.add(this);
	}

	public String getNombUser() {
		return nombre;
	}

	public void setNombUser(String name) {
		nombre = name;
	}
	public String getIp(){
		return direccion;
	}
	public void setIp(String ip){
		direccion = ip;
	}
	public void desconectar(){
		clientesActivos.removeElement(this);
		try {
			Scli.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void run() {

		try {
		
			entrada = new DataInputStream(Scli.getInputStream());
			salida2 = new DataOutputStream(Scli2.getOutputStream());
			this.setNombUser(entrada.readUTF()); // Escribe el nombre del usuario
		
			LoggerServi.log(Level.CONFIG, "Cliente agregado: " + getNombUser(), null);
			LoggerServi.log(Level.CONFIG, "Número de clientes actualmente: " + clientesActivos.size(), null);

		
		} catch (IOException e) {
			LoggerServi.log( Level.SEVERE, "Error en los Streams. ", e );
			e.printStackTrace();
		}

		int opcion = 0;
		String msmCli;

		while (true) {
			try {
				opcion = entrada.readInt();
				switch (opcion) {

				case 1:// envio de mensaje a todos
					msmCli = entrada.readUTF();
					enviaMsg(msmCli);
					break;
				case 2:
				//TODO En un futuro se añadirán más casos
				}
			} catch (IOException e) {
				LoggerServi.log( Level.SEVERE, "Error en la lectura de la opción. ", e );
				break;
			}
		}
		LoggerServi.log(Level.CONFIG, "El usuario " + getNombUser() + " se fue.", null);
		desconectar();
		LoggerServi.log(Level.CONFIG, "Número de clientes actualmente: " + clientesActivos.size(), null);
		
		try {
			Scli.close();
			LoggerServi.log(Level.CONFIG, "Socket del cliente cerrado.", null);
		} catch (Exception et) {
			LoggerServi.log( Level.SEVERE, "No se puede cerrar el socket del cliente. ", et );
		}
	}

	private void enviaMsg(String txt) {

		H_Servidor user = null;

		for (int i = 0; i < clientesActivos.size(); i++) {
			
			try {

				user = clientesActivos.get(i);
				user.salida2.writeInt(1);// opción de mensaje
				user.salida2.writeUTF("" + this.getNombUser() + " :" + txt);
				LoggerServi.log(Level.INFO, "" + this.getNombUser() + " :" + txt, null);

			} catch (IOException e) {
				LoggerServi.log( Level.SEVERE, "Error al enviar el mensaje. ", e );
				e.printStackTrace();
			}
		}
	}

}