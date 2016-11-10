package ServidorV2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Conexion {

	/*
	 * Detalles de la Base de datos:
	 * 	Host: sql7.freesqldatabase.com
	 * 	Nombre de la BD: sql7143768
	 * 	Usuario: sql7143768
	 *  Contraseña: edl72lc3Wt
	 *  Número de puerto: 3306
	 */
	private String host = "sql7.freesqldatabase.com";
	private String nombre_BD = "sql7143768";
	private String usuario = "sql7143768";
	private String pass = "edl72lc3Wt";

	// Objeto del tipo conexion lo declaramos nulo
	private Connection conexion = null;

	/*
	 * Método para conectarse a la BD:
	 * @return null si no se puede conectar
	 * @return conexion si se puede conectar
	 */
	public Connection getConexionMYSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			String servidor = "jdbc:mysql://" + host + "/" + nombre_BD;
			conexion = DriverManager.getConnection(servidor, usuario, pass);
			return conexion;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"No se ha podido establecer la conexión");
			return conexion;
		}
	}

	/*
	 * 
	 * @return
	 */
	public boolean Conectar() {
		// Declaramos un objeto de la clase Conexion
		Conexion con = new Conexion();
		// Llamamos al metodo que nos crea la conexion
		Connection conexion = con.getConexionMYSQL();
		// Comprobamos que la conexion no sea nula
		if (conexion == null) {
			return false;
		} else {
				try {
					conexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			
			return true;
		}
	}

}

