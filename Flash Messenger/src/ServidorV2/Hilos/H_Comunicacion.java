package ServidorV2.Hilos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import ServidorV2.Ventana_Servidor;
import ServidorV2.Logs.Log_errores;

/*
 * Clase encargada de recibir los datos del LogIn y del registro
 */
public class H_Comunicacion extends Thread {

	/*
	 * Método que recibe los datos del cliente
	 */
	public void recibirDatos() throws IOException {
		
		while (true) {
			ServerSocket sc = null;
			Socket recibir = null;
			Socket enviar = null;

			sc = new ServerSocket(5000);
			recibir = sc.accept();

			DataInputStream entrada = new DataInputStream(recibir.getInputStream());
		
			String tipoRecibido = entrada.readUTF();
			
			DataInputStream entrada2 = new DataInputStream(recibir.getInputStream());
			String datosRecibidos = entrada2.readUTF();
			String respuesta = " ";
			if(tipoRecibido.equals("login")){
			
				if(Ventana_Servidor.buscarUsuario(datosRecibidos)){
					respuesta = "ok";
				}else{
					respuesta = "no";
				}
			}else{
				Ventana_Servidor.dividir(datosRecibidos);
				respuesta = "done";
			}
	
			enviar = new Socket("localhost", 5001);
			DataOutputStream salida = new DataOutputStream(enviar.getOutputStream());
			
			salida.writeUTF(respuesta);
			salida.flush();

			//Cerramos los scokets
			sc.close();
			recibir.close();
			enviar.close();

		}

	}

	public void run() {

		try {
			recibirDatos();
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error al iniciar el hilo H_Comunicación: " + e.getMessage(), e );
			System.out.println(e);
		}

	}
}
