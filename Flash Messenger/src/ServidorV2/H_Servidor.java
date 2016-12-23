package ServidorV2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import ServidorV2.BD.BD_Padre;
import ServidorV2.Logger.Log_chat;
import ServidorV2.Logger.Log_errores;

/*
 * Clase del hilo del servidor
 */
public class H_Servidor extends Thread {

	private Socket socket; // Socket de entrada de mensajes
	private ObjectOutputStream out;
	private ObjectInputStream in;
	public static Vector<H_Servidor> clientesActivos = new Vector<H_Servidor>();
	private String nombre;
	private Ventana_Servidor servi;
	private String direccion;
	private Calendar calendario = null;
	private SimpleDateFormat hora = null;
  
    
	public H_Servidor(String nombre, Socket socket, Ventana_Servidor servi, String ip) {
		
		this.nombre = nombre;
		this.servi = servi;
		this.socket = socket;
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
			socket.close();
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error." + e.getMessage(), e );
			e.printStackTrace();
		}
	}
	
	public void run() {

		try {
		
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error en los Streams. ", e );
			e.printStackTrace();
		}
	
		Object obj;
		Object obj2;
		Object imagen;
		String opc = "";
		int opcion = 0;
		String mensaje = "";
		
		while (true) {
			
			String path = "C:/Flash-Messenger/Server/Images/" + getNombAleatorio();
			try {

				try {
					obj = in.readObject();
					opc = obj.toString();
					opcion = Integer.parseInt(opc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				switch (opcion) {

				case 1:// envio de mensaje a todos
					obj2 = in.readObject();
					mensaje = obj2.toString();
					
					System.out.println("Mensaje recibido: " + mensaje);
					calendario = GregorianCalendar.getInstance();
					hora = new SimpleDateFormat("hh:mm");
					Log_chat.EscribirDatos(mensaje, calendario, hora);
					enviaMsg(mensaje);
					break;
				case 2:
					try {
						FileOutputStream crear = new FileOutputStream(path + ".png");
						
						imagen = in.readObject();
						System.out.println("Imagen recibida.");
						
						ImageIcon icono = (ImageIcon) imagen;
						BufferedImage buff = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics g = buff.createGraphics();
						icono.paintIcon(null, g, 0, 0);
						g.dispose();

						ImageIO.write(buff, "png", crear);
						System.out.println("Imagen creada correctamente");
						crear.close();
						
						calendario = GregorianCalendar.getInstance();
						hora = new SimpleDateFormat("hh:mm");
						Log_chat.EscribirDatos("Imagen", calendario, hora);
						enviarImg(path+".png", 1);
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						FileOutputStream crear = new FileOutputStream(path + ".jpg");
						
						imagen = in.readObject();
						System.out.println("Imagen recibida.");
						
						ImageIcon icono = (ImageIcon) imagen;
						BufferedImage buff = new BufferedImage(icono.getIconWidth(), icono.getIconHeight(), BufferedImage.TYPE_INT_RGB);
						Graphics g = buff.createGraphics();
						icono.paintIcon(null, g, 0, 0);
						g.dispose();

						ImageIO.write(buff, "jpg", crear);
						System.out.println("Imagen creada correctamente");
						crear.close();
						
						calendario = GregorianCalendar.getInstance();
						hora = new SimpleDateFormat("hh:mm");
						Log_chat.EscribirDatos("Imagen", calendario, hora);
						System.out.println(path + ".jpg");
						enviarImg(path+".jpg", 2);
						
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				
				}
			} catch (IOException e) {
				break; //Cuando salta la excepción es que el usuario se ha ido
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	//	Log_errores.log(Level.CONFIG, "El usuario " + getNombre() + " se fue.", null);
		desconectar();

		try {
			socket.close();
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
					user.out.writeObject("1");
					user.out.writeObject(txt);
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
		String num = "";
		
		if(tipo == 1){
			num = "3";
			clase = "png";
		}else{
			num = "4";
			clase = "jpg";
		}
		for (int i = 0; i < clientesActivos.size(); i++) {
			try {

				user = clientesActivos.get(i);
				//if (!user.getNombre().equals(nombre)) {
					user.out.writeObject(num);
					BufferedImage imagen = ImageIO.read(new File(path));
					out.writeObject(new ImageIcon(imagen));
					System.out.println("Imagen enviada con éxito a " + user.getNombre());
			//	}
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