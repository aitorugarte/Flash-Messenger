package ServidorV2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;

/*
 * Clase del hilo del servidor
 */
public class HiloServidor extends Thread {

	private Socket Scli; // Socket de entrada de mensajes
	private Socket Suser; // Socket por donde se envían los nombres de los usuarios
	private Socket Scli2; // Socket de salida de mensajes
	private DataInputStream entradaNick;
	private DataInputStream entrada;
	private DataOutputStream salida2;
	public static Vector<HiloServidor> clientesActivos = new Vector<HiloServidor>();
	private String nombre;
	private Ventana_Servidor servi;
	private String direccion;

	public HiloServidor(Socket Scliente, Socket SNick, Socket Scliente2, Ventana_Servidor servi, String ip) {

		Scli = Scliente;
		Suser = SNick;
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
	}
	
	public void run() {

		try {
		
			entradaNick = new DataInputStream(Suser.getInputStream());
			entrada = new DataInputStream(Scli.getInputStream());
			salida2 = new DataOutputStream(Scli2.getOutputStream());
			this.setNombUser(entradaNick.readUTF()); // Escribe el nombre del usuario
														 
			// servi.mostrar("Cliente agregado: " + getNombUser());
			System.out.println("Número de clientes actualmente: " + clientesActivos.size());
		
		} catch (IOException e) {
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
				// En un futuro se añadirán más casos
				}
			} catch (IOException e) {
			
				break;
			}
		}
		System.out.println("El usuario " + getNombUser() + " se fue.");
		desconectar();
		System.out.println("Número de clientes actualmente: " + clientesActivos.size());
		
		try {
			System.out.println("Socket del cliente cerrado.");
			Scli.close();
		} catch (Exception et) {
			System.out.println("No se puede cerrar el socket del cliente.");
		}
	}

	private void enviaMsg(String txt) {

		HiloServidor user = null;

		for (int i = 0; i < clientesActivos.size(); i++) {
			
			try {

				user = clientesActivos.get(i);
				user.salida2.writeInt(1);// opción de mensaje
				user.salida2.writeUTF("" + this.getNombUser() + " :" + txt);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}