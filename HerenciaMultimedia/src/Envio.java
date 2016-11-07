
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Envio {

		protected Socket p_entrada;
		protected Socket p_salida;
		protected DataInputStream entrada;
		protected DataOutputStream salida;
		
		public void prueba(){
			
			try {
				entrada = new DataInputStream(p_entrada.getInputStream());
				salida = new DataOutputStream(p_salida.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
