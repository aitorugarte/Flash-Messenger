package ServidorV2.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*
 * Clase que guarda la conversación de chat
 */
public class Log_chat {
	
	private static String ruta = "C:/Users/aitor/Desktop/prueba.txt";

	public static void main(String[] args) throws IOException {
		
		EscribirDatos();
		//LeerDatos();
	}

	/*
	 * Método para escribir los datos del usuario en el fichero Si no existe,
	 * crea el fichero
	 */
	public static void EscribirDatos() throws IOException {
		String datos = "Holaa";
		File registro = new File(ruta);
		BufferedWriter escritor;
		if (registro.exists()) {

			// Para que no se sobreescriba el texto del archivo, descomentar el
			// true
			escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(registro/* , true */)));
			escritor.write(datos);
			escritor.write("\n");
		} else { // Sino, crea el archivo
			escritor = new BufferedWriter(new FileWriter(registro));
			escritor.write(datos);
			escritor.write("\n");
		}
		escritor.close();

	}

	/*
	 * Método para leer un fichero (no binario, sólo txt)
	 */
	public static void LeerDatos() throws FileNotFoundException {

		BufferedReader lector = null;
		File archivo = new File(ruta);
		FileReader registro = null;
		
		if(archivo.exists()){
	
			try{
			registro = new FileReader(ruta);
			lector = new BufferedReader(registro);
			
			String linea;
			// Mostramos línea a línea el contenido del fichero
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}

		} catch (IOException e2) {
			e2.printStackTrace();
		} finally {
			// En el finally cerramos el fichero, así nos aseguramos de que se cierra siempre.
			try {
				if (null != lector) {
					lector.close();
				}
			} catch (IOException e3) {
				e3.printStackTrace();
			}
		}
		}else{
			System.out.println("El archivo no existe.");
		}
	}

}
