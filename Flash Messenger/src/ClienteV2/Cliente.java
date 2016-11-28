package ClienteV2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/*
 * Clase del cliente que gestiona la conexión
 */ 
public class Cliente {

	   public static String Ip_Servidor; //Dirección ip del servidor
	   private String nombre; //Nombre del usuario
	   private String contraseña; //Contraseña del usuario
	   private GUI_Cliente frame; //GUI del cliente
	   private DataOutputStream salida;
	   private DataInputStream entrada;
	   private Socket comunicacion; //para la conectarse
	   private Socket comunicacion2;//para recibir el mensaje
	   private HiloCliente hilo;
	
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

			// Pide el nombre del usuario evitando que no escriba nada
			/*try {
				do {
					nombre = JOptionPane.showInputDialog("Introduzca su nombre :");

				} while (nombre.trim().equals(""));
			} catch (NullPointerException e) {

			}*/
	         
			// frame.setNombreUser(nombre); 
	         nombre = frame.getName();
			 salida.writeUTF(nombre);
	         salida.flush();
	        /* 
	         //Se cierra la petición de conexión llamada socket
	         try{
	          salida.close();
	         
	         }catch (Exception ex){}*/
	         
	   
	      }catch (IOException e) {
	    	
	    	  JOptionPane.showMessageDialog(frame,"Ningún servidor activado", "Error de conexión", JOptionPane.ERROR_MESSAGE);
	      }
	     
	      hilo =  new HiloCliente(entrada, frame);
	      hilo.start();
	   }
	   
	   public String getNombre(){
	      return nombre;
	   }
	   
	   public void flujo(String txt){
	      try {             
	     //    System.out.println("El mensaje enviado desde el cliente es: " + txt);
	         salida.writeInt(1);
	         salida.writeUTF(txt);
	      } catch (IOException e) {
	    	  JOptionPane.showMessageDialog(frame,"Has sido expulsado del servidor.", "Error", JOptionPane.ERROR_MESSAGE);
	    	  frame.dispose();
	    	  hilo.interrupt();
	    	 
	      }
	   }
	  public void expulsado(){
		  
		 
	  }
}
