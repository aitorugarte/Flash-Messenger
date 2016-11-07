
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
public class Mensaje extends Envio{

	public static void main(String[] args) {
		
	}
	
	public void Enviar() throws IOException{
		Scanner sc = new Scanner(System.in);
		String mensaje;
		mensaje = sc.nextLine();
		try {
			salida = new DataOutputStream(p_salida.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		salida.flush();
}

}
