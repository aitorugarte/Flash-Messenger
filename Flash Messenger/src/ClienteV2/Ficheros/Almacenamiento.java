package ClienteV2.Ficheros;

import java.io.File;
import java.io.IOException;

/*
 * Clase encargada de crear las carpetas necesarias para 
 * almacenar videos, fotos..
 */
public class Almacenamiento {

	private static String ruta = "C:/Flash-Messenger/Images";
	private static String ruta2 = "C:/Flash-Messenger/Video";
	
	/*
	 * Método que crea las carpetas
	 */
	public static void crearCarpetas() throws IOException {

		// Primero miramos si existe la carpeta
		File carpeta = new File(ruta);
		if (!carpeta.exists()) { // Si no existe, creamos la carpeta
			carpeta.mkdirs();
		}
		File carpeta2 = new File(ruta2);
		if (!carpeta2.exists()) { // Si no existe, creamos la carpeta
			carpeta2.mkdirs();
		}
	}
	
}
