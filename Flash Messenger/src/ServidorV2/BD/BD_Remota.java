package ServidorV2.BD;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/*
 * Clase de la gesti�n de la BD en internet 
 */
public class BD_Remota {

	
	public static void main(String[] args) {
		
		Conectar();
	}
	/*
	 * Detalles de la Base de datos:
	 * 	Host: sql7.freesqldatabase.com
	 * 	Nombre de la BD: sql7143768
	 * 	Usuario: sql7143768
	 *  Contrase�a: edl72lc3Wt
	 *  N�mero de puerto: 3306
	 */
	private String host = "sql7.freesqldatabase.com";
	private String nombre_BD = "sql7143768";
	private String usuario = "sql7143768";
	private String pass = "edl72lc3Wt";

	// Objeto del tipo conexion lo declaramos nulo
	private Connection conexion = null;

	/*
	 * M�todo para conectarse a la BD:
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
			JOptionPane.showMessageDialog(null,"No se ha podido establecer la conexi�n " + e);
			return conexion;
		}
	}

	/*
	 * 
	 * @return
	 */
	public static boolean Conectar() {
		// Declaramos un objeto de la clase Conexion
		BD_Remota con = new BD_Remota();
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
			System.out.println("Conectado");
			return true;
		}
	}
	/**
	 *M�todo que indica si hay disponible una conexi�n a internet
	 * @return true si hay internet
	 * @return false si no hay internet
	 */
	public boolean TestInternet() {

		String web = "www.google.es";
		int puerto = 80;
		boolean internet = false;
		Socket test = null;
		try {
			test = new Socket(web, puerto);
			if (test.isConnected()) {
				// Si hay internet comprobamos el puerto 3306
				if (TestPuerto() == true) {
					internet = true;
				}else{
					JOptionPane.showMessageDialog(null, "Dispone de conexi�n a internet pero el puerto 3306 est� cerrado");	
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No dispone de conexi�n a internet");
		}
		//Cerramos el puerto
		try {
			test.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return internet;
	}

	/*
	 * M�todo que comprueba si el puerto necesario para conectarse a la bd
	 * est� abierto o cerrado
	 * @return true si est� abierto
	 * @return false si est� cerrado
	 */
	private boolean TestPuerto() {
		boolean abierto = false;
		try {
			//Comprobamos con nuestro host
			Socket s = new Socket("www.phpmyadmin.co", 3306);
			if (s.getPort() == 3306) {
				abierto = true;
			}
			//Cerramos el puerto
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return abierto;
	}

}
