package ServidorV2;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;

import javax.imageio.ImageIO;

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
		String path = "C:/Flash-Messenger/Server/Images/" + getNombAleatorio();

		while (true) {
			try {

				opcion = entrada.readInt();

				switch (opcion) {

				case 1:// envio de mensaje a todos
					msmCli = entrada.readUTF();
					System.out.println("Mensaje recibido: " + msmCli);
					calendario = GregorianCalendar.getInstance();
					hora = new SimpleDateFormat("hh:mm");
					Log_chat.EscribirDatos(msmCli, calendario, hora);
					enviaMsg(msmCli);
					break;
				case 2:
					try {
						BufferedImage bufferedImage = ImageIO.read(entrada);
						System.out.println("Procesando imagen..");
						ImageIO.write(bufferedImage, "png", new FileOutputStream(path + ".png"));
						
						System.out.println("Imagen recibida en el servidor.");
						calendario = GregorianCalendar.getInstance();
						hora = new SimpleDateFormat("hh:mm");
						Log_chat.EscribirDatos("Imagen", calendario, hora);
						enviarImg(path+".png", 1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						BufferedImage bufferedImage = ImageIO.read(entrada);
						System.out.println("Procesando imagen..");
						ImageIO.write(bufferedImage, "jpg", new FileOutputStream(path + ".jpg"));
						System.out.println("Imagen recibida en el servidor.");
						calendario = GregorianCalendar.getInstance();
						hora = new SimpleDateFormat("hh:mm");
						Log_chat.EscribirDatos("Imagen", calendario, hora);
						enviarImg(path+".jpg", 2);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				
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
	/**
	 * TODO encontrar el nombre del que envía el mensaje
	 * @param txt texto a enviar
	 */
	private void enviaMsg(String txt) {

		H_Servidor user = null;
		String nombre = " ";
		int i = 0;
		// Buscamos el nombre del usuario que ha enviado el mensaje
		for (i = 0; i < txt.length(); i++) {
			if (txt.charAt(i) == '_') {
				nombre = txt.substring(0, i);
			}
		}
		for (i = 0; i < clientesActivos.size(); i++) {
			try {
				user = clientesActivos.get(i);
				if (!user.getNombre().equals(nombre)) {

					user.salida2.writeInt(1);// opción de mensaje
					user.salida2.writeUTF(txt);
					System.out.println("Mensaje enviado a cliente " + nombre);

				}
			} catch (IOException e) {
				Log_errores.log(Level.SEVERE, "Error al enviar el mensaje. ", e);
				e.printStackTrace();
			}
		}
	}
	
	private void enviarImg(String path, int tipo){

		H_Servidor user = null;
		String clase = null;
		int num = 0;
		
		if(tipo == 1){
			num = 3;
			clase = "png";
		}else{
			num = 4;
			clase = "jpg";
		}
		for (int i = 0; i < clientesActivos.size(); i++) {
			try {
				user = clientesActivos.get(i);
			//	if (!user.getNombre().equals(nombre)) {
					user.salida2.writeInt(num);// opción de mensaje
					BufferedImage imagen = ImageIO.read(new File(path));
					System.out.println(path);
					//ImageIO.write(imagen, clase, Scli2.getOutputStream());
					ImageIO.write(imagen, clase, salida2);
					salida2.flush();
					salida2.close();
					System.out.println("Imagen enviada con éxito a " + user.getNombre());
				//}
			} catch (IOException e) {
				Log_errores.log(Level.SEVERE, "Error al enviar el mensaje. ", e);
				e.printStackTrace();
			}
		}
	}
	/*
	 * Método que genera un nombre aleatorio para la imagen
	 */
	public String getNombAleatorio() {
		String cadenaAleatoria = "";
		long milis = new GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < 15) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}

}