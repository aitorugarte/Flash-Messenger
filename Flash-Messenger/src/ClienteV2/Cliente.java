package ClienteV2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Cliente {

	   public static String IP_SERVIDOR;
	   private MiFrameCliente frame;
	   private DataInputStream entrada;
	   private DataOutputStream salida;
	   private DataOutputStream salidaNick;
	   private DataInputStream entrada2;
	   private Socket comunicacion;//para la conectarse
	   private Socket Snick; //Para mandar el nick
	   private Socket comunicacion2;//para recibir el mensaje
	   private String nombre;
	   
	   //Crea una nueva instancia del cliente
	   public Cliente(MiFrameCliente frame) throws IOException{      
	     
		   this.frame = frame;
	   }
	   
	   public void conexion() throws IOException{
		   IP_SERVIDOR = Principal_Cliente.Ip_Servidor;
		   
	      try {
	         comunicacion = new Socket(Cliente.IP_SERVIDOR, 8080);
	         Snick = new Socket(Cliente.IP_SERVIDOR, 8081);
	         comunicacion2 = new Socket(Cliente.IP_SERVIDOR, 8083);
	         
	         entrada = new DataInputStream(comunicacion.getInputStream());
	         salidaNick = new DataOutputStream(Snick.getOutputStream());
	         salida = new DataOutputStream(comunicacion.getOutputStream());
	         entrada2 = new DataInputStream(comunicacion2.getInputStream());
	         
	         //Pide el nombre del usuario
	         nombre = JOptionPane.showInputDialog("Introduzca su nombre :");
	         frame.setNombreUser(nombre);         
	         salidaNick.writeUTF(nombre);
	         salidaNick.flush();
	       //Se cierra la petición de conexión llamada socket
	         try{
	          Snick.close();
	          //Mirar si hay más usuarios
	         } catch (Exception ex){}
	         
	   
	      } catch (IOException e) {
	         System.out.println("		----------------------------");
	         System.out.println("		El servidor no está activado");
	         System.out.println("		----------------------------");
	      }
	      
	      new HiloCliente(entrada2, frame).start();
	   }
	   
	   public String getNombre(){
	      return nombre;
	   }
	   
	   public void flujo(String txt){
	      try {             
	         System.out.println("El mensaje enviado desde el cliente es: "
	             + txt);
	         salida.writeInt(1);
	         salida.writeUTF(txt);
	      } catch (IOException e) {
	         System.out.println("Error...." + e);
	      }
	   }
	  
}
