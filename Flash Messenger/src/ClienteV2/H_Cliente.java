package ClienteV2;

import java.awt.image.BufferedImage;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import ClienteV2.Encriptado.CesarRecursivo;
import ServidorV2.Logger.Log_chat;

/*
 * Hilo del cliente encargado de los mensajes
 */
public class H_Cliente extends Thread{
	
	   private DataInputStream entrada_txt;
	   private DataInputStream entrada_imagen;
	   private GUI_Cliente frame;
	   private Socket Sentrada;
	   private Socket Simagen;
	   
	   public H_Cliente (DataInputStream entrada_txt, Socket Sentrada, DataInputStream entrada_imagen, Socket Simagen, GUI_Cliente frame) throws IOException{
	      this.entrada_txt = entrada_txt;
	      this.Sentrada = Sentrada;
	      this.entrada_imagen = entrada_imagen;
	      this.Simagen = Simagen;
	      this.frame = frame;
	   }
	   
	   public void run(){
		   
	      String mensaje = "";
	      String usuario = "";
	      String path = "C:/Flash-Messenger/Server/Images/" + getNombAleatorio();
	      int opcion = 0;
	      int i = 0;
	      //!interrupted
	      while(true){         
	         try{
	            opcion = entrada_txt.readInt();
	            switch(opcion){
	            
				case 1:// mensage recibido
					mensaje = entrada_txt.readUTF(); 
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
					mensaje = entrada_txt.readUTF();
					break;
				case 3: // Recibe imagen
					try {
						BufferedImage bufferedImage = ImageIO.read(entrada_imagen);
						System.out.println("Procesando imagen..");
						ImageIO.write(bufferedImage, "png", new FileOutputStream(path + ".png"));
						System.out.println("Imagen recibida en el cliente.");
						//entrada_imagen.close();
	
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.mostrarImagen(2, path + ".png");
					break;
				case 4:
					try {
						BufferedImage bufferedImage = ImageIO.read(entrada_imagen);
						System.out.println("Procesando imagen..");
						ImageIO.write(bufferedImage, "jpg", new FileOutputStream(path + ".jpg"));
						System.out.println("Imagen recibida en el cliente.");
						//entrada_imagen.close();
	
					} catch (IOException e) {
						e.printStackTrace();
					}   
					frame.mostrarImagen(2, path + ".jpg");
					break;
				}  
	         }catch (IOException e){
	        	 System.out.println(e);
	        	 JOptionPane.showMessageDialog(frame,"El servidor ha sido desconectado", "Desconexión", JOptionPane.ERROR_MESSAGE);
	        	 frame.dispose(); //Se cierra la ventana del cliente
	            break;
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
	   
