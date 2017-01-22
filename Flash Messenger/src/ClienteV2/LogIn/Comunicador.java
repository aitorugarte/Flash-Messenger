package ClienteV2.LogIn;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import ClienteV2.Cliente;
import ClienteV2.GUI_Cliente;
import ClienteV2.H_Cliente;
import ClienteV2.Principal_Cliente;
import ClienteV2.Ficheros.Almacenamiento;

public class Comunicador {
	
	   private DataOutputStream salida;
	   private Login login; 
	   private Registrarse registro;
	   
	   //Crea una nueva instancia del Login
	   public Comunicador(Login login) throws IOException{      
		   this.login = login;
	   }
	   public Comunicador(Registrarse registro){
		   this.registro = registro;
	   }
	   /**
	    * @param emails como un array
	    */
	public void conexion(String usuario, String contraseña, String ...emails) throws IOException {
		
		String credenciales;
		String tipo;

		if(emails.length == 0){
			credenciales = usuario + " " + contraseña;
			tipo = "login";
		}else{
			credenciales = usuario + " " + contraseña + " " + emails[0];
			tipo = "registro";
		}
		
		try {
			Socket Senviar = new Socket("localhost", 5000);
			ServerSocket sc = null;
			Socket Srecibir = null;

			sc = new ServerSocket(5001);
			salida = new DataOutputStream(Senviar.getOutputStream());

			//Avisamos de qué tipo van a ser las credenciales
			salida.writeUTF(tipo);
			salida.flush();
			//Enviamos las credenciales
			salida.writeUTF(credenciales);
			salida.flush();

			Srecibir = sc.accept();
			
			DataInputStream entrada = new DataInputStream(Srecibir.getInputStream());
			String respuesta = entrada.readUTF();
			
			if(respuesta.equals("ok")){
				login.dispose();
				Almacenamiento.crearCarpetas();
				Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");
				GUI_Cliente gui = new GUI_Cliente();
				gui.setIconImage(icon);
				gui.setNombreUser(usuario);
				gui.setVisible(true);
			}else if(respuesta.equals("no")){
				login.textPassword.setText("");
				JOptionPane.showMessageDialog(login, "Error, usuario y/o contraseña incorrectos.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			else if(respuesta.equals("done")){
			//	registro.dispose();
				JOptionPane.showMessageDialog(registro, "Registro completado exitósamente.");
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
