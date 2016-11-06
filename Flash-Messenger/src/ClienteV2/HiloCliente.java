package ClienteV2;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

/*
 * Hilo del cliente encargado de los mensajes
 */
public class HiloCliente extends Thread{
	
	   private DataInputStream entrada;
	   private MiFrameCliente frame;
	   
	   public HiloCliente (DataInputStream entrada, MiFrameCliente frame) throws IOException{
	      this.entrada = entrada;
	      this.frame = frame;
	   }
	   
	   public void run(){
		   
	      String mensaje = "";
	      int opcion = 0;
	      
	      while(true){         
	         try{
	        	
	            opcion = entrada.readInt();
	            switch(opcion){
	            
	               case 1://mensage enviado
	            	   mensaje = entrada.readUTF();
	          //       System.out.println("ECO del servidor:" + mensaje);
	            	   frame.mostrarMsg(mensaje);            
	                  break;
	               case 2://se agrega
	            	   mensaje = entrada.readUTF();                
	                  break;
	            }
	         }
	         catch (IOException e){
	        	 JOptionPane.showMessageDialog(frame,"El servidor ha sido desconectado", "Desconexión", JOptionPane.ERROR_MESSAGE);
	        	 frame.dispose(); //Se cierra la ventana del cliente
	            break;
	         }
	      }
	   }
}
	   
