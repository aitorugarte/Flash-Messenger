package ClienteV2;

import java.io.DataInputStream;
import java.io.IOException;

import javax.swing.JEditorPane;

class HiloCliente extends Thread{
	

	   private DataInputStream entrada;
	  // private V_Cliente Vcli;
	   private MiFrameCliente frame;
	   
	   public HiloCliente (DataInputStream entrada, MiFrameCliente frame) throws IOException{
	      this.entrada = entrada;
	      this.frame = frame;
	   }
	   public void run(){
		   
	      String mensaje = " ";
	      int opcion = 0;
	      
	      while(true){         
	         try{
	        	
	            opcion = entrada.readInt();
	            switch(opcion){
	            
	               case 1://mensage enviado
	            	   mensaje = entrada.readUTF();
	                 System.out.println("ECO del servidor:" + mensaje);
	                 frame.mostrarMsg(mensaje);            
	                  break;
	               case 2://se agrega
	            	   mensaje = entrada.readUTF();                
	                  break;
	            }
	         }
	         catch (IOException e){
	            System.out.println("Error en la comunicación");
	            break;
	         }
	      }
	      System.out.println("Se desconectó el servidor");
	   }
	   
}
	   
