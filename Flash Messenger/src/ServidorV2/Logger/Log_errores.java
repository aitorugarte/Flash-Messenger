package ServidorV2.Logger;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Clase que recoge los errores en un fichero xml
 */
public class Log_errores {

	private static Logger logger = null;

	public static void setLogger(Logger logger) {
		Log_errores.logger = logger;
	}

	public static void log(Level level, String msg, Throwable excepcion) {
		if (logger == null) { 
			logger = Logger.getLogger(Log_errores.class.getName()); 
			logger.setLevel(Level.SEVERE); // Loguea el nivel de las excepciones
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
