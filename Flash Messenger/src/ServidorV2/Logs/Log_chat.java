package ServidorV2.Logs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;

/*
 * Clase que guarda la conversación de chat
 */
public class Log_chat {
	
	private static String ruta = "C:/Flash-Messenger/Server/Log";
	private static String ruta2 = "C:/Flash-Messenger/Server/Images";
	private static String ruta3 = "C:/Flash-Messenger/Server/Video";
	private static String nombre = "/chat.txt";

	/*
	 * Método que se ejecuta al iniciar el servidor, escribe el día/mes/año
	 * al principio del fichero
	 */
	public static void empezarLog(Calendar calendario) throws IOException{
		SimpleDateFormat dia = new SimpleDateFormat("dd/M/yyyy");
		EscribirDatos("",calendario,  dia);
	}
	
	/*
	 * Método que crea las carpetas necesarias del servidor
	 */
	public static void crearCarpetas(){
		
		File carpeta = new File(ruta);
		if (!carpeta.exists()) { // Si no existe, creamos la carpeta
			carpeta.mkdirs();
		}
		File carpeta2 = new File(ruta2);
		if (!carpeta2.exists()) { 
			carpeta2.mkdirs();
		}
		File carpeta3 = new File(ruta3);
		if (!carpeta3.exists()) { 
			carpeta3.mkdirs();
		}
	}
	/*
	 * Método para escribir el chat en el fichero
	 */
	public static void EscribirDatos(String texto, Calendar calendario, SimpleDateFormat hora) throws IOException {

		crearCarpetas();
		// Ahora manejamos el txt
		File registro = new File(ruta + nombre);
		BufferedWriter escritor;
		if (registro.exists()) { // Si exite lo modificamos
			escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(registro, true)));
		} else { // Sino, creamos el archivo
			escritor = new BufferedWriter(new FileWriter(registro));
		}
    
		escritor.write(texto + " (" + hora.format(calendario.getTime()) + ")");
		escritor.newLine();
		escritor.close();
	}

	/*
	 * Método para leer el fichero que contiene los chats
	 */
	public static void LeerFichero() throws FileNotFoundException {

		BufferedReader lector = null;
		File archivo = new File(ruta + nombre);
		FileReader registro = null;
		
		if(archivo.exists()){
	
			try{
			registro = new FileReader(ruta + nombre);
			lector = new BufferedReader(registro);
			
			String linea;
			// Mostramos línea a línea el contenido del fichero
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}

		} catch (IOException e2) {
			Log_errores.log( Level.SEVERE, "Error.", e2 );
			e2.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, así nos aseguramos de que se cierra siempre.
			try {
				if (null != lector) {
					lector.close();
				}
			} catch (IOException e3) {
				Log_errores.log( Level.SEVERE, "Error.", e3 );
				e3.printStackTrace();
			}
		}
		}else{
			Log_errores.log( Level.SEVERE, "El archivo a leer no existe.", null );
			System.out.println("El archivo no existe.");
		}
	}

}
