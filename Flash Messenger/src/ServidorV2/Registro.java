package ServidorV2;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Clase que guarda los datos en un archivo xml
 */
public class Registro {

	private static Logger logger = null;


	public static void setLogger(Logger logger) {
		Registro.logger = logger;
	}


	public static void log(Level level, String msg, Throwable excepcion) {
		if (logger == null) { 
			logger = Logger.getLogger(Registro.class.getName()); 
			logger.setLevel(Level.ALL); // Loguea todos los niveles
			try {
				//Saca el log a fichero xml
				logger.addHandler(new FileHandler("Servidor.log.xml", true)); 
			} catch (Exception e) {
				logger.log(Level.SEVERE, "No se pudo crear fichero de log", e);
			}
		}
		if (excepcion == null){
			logger.log(level, msg);
		}else{
			logger.log(level, msg, excepcion);
		}
	}
}
