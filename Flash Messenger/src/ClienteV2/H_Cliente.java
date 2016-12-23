package ClienteV2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ClienteV2.Encriptado.CesarRecursivo;
import ServidorV2.Logger.Log_chat;

/*
 * Hilo del cliente encargado de los mensajes
 */
public class H_Cliente extends Thread{
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private GUI_Cliente frame;

	public H_Cliente(Socket socket, ObjectInputStream in, ObjectOutputStream out, GUI_Cliente frame) throws IOException {
			
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.frame = frame;
	}

	   public void run(){
		
		  //Declaramos los objetos que recibimos
		  Object obj; //la opción 
		  Object obj2; //el mensaje de texto
		  Object imagen; //la imagen
		  //Declaramos las variables que manejaremos
		  int opcion = 0;
		  int i = 0;
		  String opc = "";
	      String mensaje = "";
	      String usuario = "";

	      //!interrupted
	      while(true){ 
	    	  String path = "C:/Flash-Messenger/Client/Images/" + getNombAleatorio();
	    	  try{
	 	        
	            try {
					obj = in.readObject();
					opc = obj.toString();
					opcion = Integer.parseInt(opc);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

	            switch(opcion){
	            
				case 1:// mensage recibido
					obj2 = in.readObject();
					mensaje = obj2.toString();
					
					System.out.println("Mensaje recibido: " + mensaje);
					for (i = 0; i < mensaje.length(); i++) {
						if (mensaje.charAt(i) == '_') {
							usuario = mensaje.substring(0, i);
							break;
						}
					}
					mensaje = mensaje.substring(i);
					mensaje = CesarRecursivo.recorrer(2, mensaje, "", 0);
					mensaje = usuario + mensaje;
					frame.mostrarMensaje(2, mensaje);
					break;
				case 2:// se agrega
					obj2 = in.readObject();
					mensaje = obj2.toString();
					break;
				case 3: // Recibe imagen
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
		

					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					frame.mostrarImagen(2, path + ".png");
					break;
				case 4:
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
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}   
					frame.mostrarImagen(2, path + ".jpg");
					break;
				}  

	         }catch (IOException e){
	        	 //TODO reabrir entrada_imagen antes de que salte la excepción
	        	 System.out.println(e);
	        	 JOptionPane.showMessageDialog(frame,"El servidor ha sido desconectado", "Desconexión", JOptionPane.ERROR_MESSAGE);
	        	 frame.dispose(); //Se cierra la ventana del cliente
	            break;
	         } catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
	   
