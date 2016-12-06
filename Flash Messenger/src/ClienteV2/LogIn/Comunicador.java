package ClienteV2.LogIn;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import ClienteV2.Cliente;
import ClienteV2.GUI_Cliente;
import ClienteV2.HiloCliente;
import ClienteV2.Principal_Cliente;

public class Comunicador {
	
	   private DataOutputStream salida;
	   private Login login; 
	   private Registrarse registro;
	   
	   //Crea una nueva instancia del Login
	   public Comunicador(Login login) throws IOException{      
		   this.login = login;
	   }
	   
	public void conexion(String usuario, String contraseña) throws IOException {
		
		try {
			Socket Senviar = new Socket("localhost", 5000);
			ServerSocket sc = null;
			Socket Srecibir = null;

			sc = new ServerSocket(5001);
			salida = new DataOutputStream(Senviar.getOutputStream());

			String credenciales = usuario + " " + contraseña;
			salida.writeUTF(credenciales);
			salida.flush();
			System.out.println("Enviado correctamente");

			Srecibir = new Socket();
			Srecibir = sc.accept();
			
			DataInputStream entrada = new DataInputStream(Srecibir.getInputStream());
			String respuesta = entrada.readUTF();
			System.out.println(respuesta);
			if(respuesta.equals("ok")){
				login.dispose();
				GUI_Cliente gui = new GUI_Cliente();
				gui.setNombreUser(usuario);
				gui.setVisible(true);
			}else{
				login.textPassword.setText("");
				JOptionPane.showMessageDialog(null, "Error, usuario y/o contraseña incorrectos.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			
			Senviar.close();
			sc.close();
			Srecibir.close();

		} catch (IOException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Error", "Error de conexión", JOptionPane.ERROR_MESSAGE);
		}

	}
	 
}
