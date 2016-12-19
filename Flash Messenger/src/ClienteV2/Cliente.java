package ClienteV2;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.codec.binary.Base64;

import ClienteV2.Encriptado.CesarRecursivo;

/*
 * Clase del cliente que gestiona la conexión
 */ 
public class Cliente {

	   public static String Ip_Servidor; //Dirección ip del servidor
	   private String nombre; //Nombre del usuario
	   private GUI_Cliente frame; //GUI del cliente
	   private DataOutputStream salida;
	   private DataInputStream entrada_txt;
	   private DataInputStream entrada_imagen;
	   private Socket Senviar; //pare enviar
	   private Socket Srecibir;//para recibir el mensaje
	   private Socket Simagen;
	   private H_Cliente hilo;
	
	   //Crea una nueva instancia del cliente
	   public Cliente(GUI_Cliente frame) throws IOException{      
		   this.frame = frame;
	   }
	   
	   public void conexion() throws IOException{
		   Ip_Servidor = Principal_Cliente.Ip_Servidor;
		   
	      try {
	    	 Senviar = new Socket(Cliente.Ip_Servidor, 8080);
	         Srecibir = new Socket(Cliente.Ip_Servidor, 8081);
	         Simagen = new Socket(Cliente.Ip_Servidor, 8082);
	         
	         salida = new DataOutputStream(Senviar.getOutputStream());
	         entrada_txt = new DataInputStream(Srecibir.getInputStream());  
	         entrada_imagen = new DataInputStream(Simagen.getInputStream());
	   
	      }catch (IOException e) {
	    	  JOptionPane.showMessageDialog(frame,"Ningún servidor activado", "Error de conexión", JOptionPane.ERROR_MESSAGE);
	      }
	     
	      hilo =  new H_Cliente(entrada_txt, Srecibir, entrada_imagen, Simagen, frame);
	      hilo.start();
	   }
	
	/*
	 * Método que envía los mensajes al servidor
	 */
	public void enviarTexto(String txt) {
		try {
			salida.writeInt(1);
			salida.writeUTF(txt);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Has sido expulsado del servidor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println(e);
			frame.dispose();

		}
	}

	/**
	 * Método que envía una imagen
	 * @param path dirección en la que se encuentra la fotografía
	 * @param tipo 1 si jpg, 2 si png
	 */
	public void enviarImagen(String path, String tipo){
		try {
		if(tipo.equals("png")){
			salida.writeInt(2);
		}else{
			salida.writeInt(3);
		}
		BufferedImage bufferedImage = ImageIO.read(new File(path));
		ImageIO.write(bufferedImage, tipo, salida);
		salida.flush();
		salida.close(); //Para que funcione se tiene que cerrar!!! Pero entonces salta la excepción..
		System.out.println("Has enviado la imagen con éxito!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Has sido expulsado del servidor.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.out.println(e);
			frame.dispose();
		}
	}
}
