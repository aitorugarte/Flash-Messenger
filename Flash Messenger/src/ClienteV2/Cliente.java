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
	   private DataInputStream entrada;
	   private Socket comunicacion; //para la conectarse
	   private Socket comunicacion2;//para recibir el mensaje
	   private H_Cliente hilo;
	
	   //Crea una nueva instancia del cliente
	   public Cliente(GUI_Cliente frame) throws IOException{      
		   this.frame = frame;
	   }
	   
	   public void conexion() throws IOException{
		   Ip_Servidor = Principal_Cliente.Ip_Servidor;
		   
	      try {
	         comunicacion = new Socket(Cliente.Ip_Servidor, 8080);
	         comunicacion2 = new Socket(Cliente.Ip_Servidor, 8083);
	         
	         salida = new DataOutputStream(comunicacion.getOutputStream());
	         entrada = new DataInputStream(comunicacion2.getInputStream());     
	   
	      }catch (IOException e) {
	    	  JOptionPane.showMessageDialog(frame,"Ningún servidor activado", "Error de conexión", JOptionPane.ERROR_MESSAGE);
	      }
	     
	      hilo =  new H_Cliente(entrada, comunicacion2, frame);
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
	//	ImageIO.write(bufferedImage, tipo, comunicacion.getOutputStream());
		ImageIO.write(bufferedImage, tipo, salida);
		comunicacion.getOutputStream().flush();
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
