package ServidorV2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Level;

import ServidorV2.BD.BD_Padre;
import ServidorV2.Logger.Log_chat;
import ServidorV2.Logger.Log_errores;

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
	Calendar calendario = null;
	SimpleDateFormat hora = null;
  
    
	public H_Servidor(String nombre, Socket Scliente, Socket Scliente2, Ventana_Servidor servi, String ip) {

		Scli = Scliente;
		Scli2 = Scliente2;
		this.servi = servi;
		this.nombre = nombre;
		direccion = ip;
		clientesActivos.add(this);
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String name) {
		nombre = name;
	}
	public String getIp(){
		return direccion;
	}
	public void setIp(String ip){
		this.direccion = ip;
	}
	public void desconectar(){
		clientesActivos.removeElement(this);
		try {
			Scli.close();
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error." + e.getMessage(), e );
			e.printStackTrace();
		}
	}
	
	public void run() {

		try {
		
			entrada = new DataInputStream(Scli.getInputStream());
			salida2 = new DataOutputStream(Scli2.getOutputStream());
			
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error en los Streams. ", e );
			e.printStackTrace();
		}

		int opcion = 0;
		String msmCli = null;

		while (true) {
			try {

				opcion = entrada.readInt();

				switch (opcion) {

				case 1:// envio de mensaje a todos
					msmCli = entrada.readUTF();
					calendario = GregorianCalendar.getInstance();
					hora = new SimpleDateFormat("hh:mm");
					Log_chat.EscribirDatos(msmCli, calendario, hora);
					enviaMsg(msmCli);
					break;
				case 2:
					// TODO En un futuro se añadirán más casos
				}
			} catch (IOException e) {
				break; //Cuando salta la excepción es que el usuario se ha ido
			}
		}
	//	Log_errores.log(Level.CONFIG, "El usuario " + getNombre() + " se fue.", null);
		desconectar();

		try {
			Scli.close();
			Log_errores.log(Level.CONFIG, "Socket del cliente cerrado.", null);
		} catch (Exception et) {
			Log_errores.log( Level.SEVERE, "No se puede cerrar el socket del cliente. ", et );
		}
	}
	/*
	 * TODO encontrar el nombre del que envía el mensaje
	 */
	private void enviaMsg(String txt) {

		H_Servidor user = null;
		String nombre = " ";
		int i = 0;
		//Buscamos el nombre del usuario que ha enviado el mensaje
		for (i = 0; i < txt.length(); i++) {
			if(txt.charAt(i) == '='){
				nombre = txt.substring(0, i - 1);
			}
		}
		for (i = 0; i < clientesActivos.size(); i++) {
			
			try {
				user = clientesActivos.get(i);
				if(!user.getNombre().equals(nombre)){
					user.salida2.writeInt(1);// opción de mensaje
					user.salida2.writeUTF(txt);
				}
			} catch (IOException e) {
				Log_errores.log( Level.SEVERE, "Error al enviar el mensaje. ", e );
				e.printStackTrace();
			}
		}
	}

}