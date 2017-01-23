package ClienteV2.Ficheros;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
 * Clase que lee el fichero properties
 */
public class Datos {

	/**
	 * Método para obtener las propiedades de un archivo properties
	 * @param ruta
	 * @param key
	 * @return
	 */
	public String getProperties(String ruta, String key) {
		Properties propiedades = new Properties();
		InputStream entrada = null;
		String prop = "";
		try {

			entrada = new FileInputStream(ruta);

			// Cargamos el archivo de propiedades:
			propiedades.load(entrada);
			// Sacamos variable:
			prop = propiedades.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
