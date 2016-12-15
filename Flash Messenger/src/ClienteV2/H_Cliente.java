package ClienteV2;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import ClienteV2.Encriptado.CesarRecursivo;

/*
 * Hilo del cliente encargado de los mensajes
 */
public class H_Cliente extends Thread{
	
	   private DataInputStream entrada;
	   private GUI_Cliente frame;
	   
	   public H_Cliente (DataInputStream entrada, GUI_Cliente frame) throws IOException{
	      this.entrada = entrada;
	      this.frame = frame;
	   }
	   
	   public void run(){
		   
	      String mensaje = "";
	      int opcion = 0;
	      //!interrupted
	      while(true){         
	         try{
	            opcion = entrada.readInt();
	            switch(opcion){
	            
	               case 1://mensage recibido
	            	   mensaje = entrada.readUTF(); //TODO encontrar el nombre del usuario
	            	   String usuario = "";
	            	   int i = 0;
	            	   for (i = 0; i < mensaje.length(); i++) {
						if(mensaje.charAt(i) == '='){
							usuario = mensaje.substring(0, i - 1);
							break;
						}
					}  
	            	   mensaje = mensaje.substring(i);
	            	   mensaje = CesarRecursivo.recorrer(2, mensaje, "", 0);
	            	   mensaje = usuario + mensaje;
	            	   frame.mostrarMsg(mensaje);            
	                  break;
	               case 2://se agrega
	            	   mensaje = entrada.readUTF();                
	                  break;
	               case 3: //TODO
	            }
	         }catch (IOException e){
	        	 JOptionPane.showMessageDialog(frame,"El servidor ha sido desconectado", "Desconexión", JOptionPane.ERROR_MESSAGE);
	        	 frame.dispose(); //Se cierra la ventana del cliente
	            break;
	         }
	      }
	   }
}
	   
