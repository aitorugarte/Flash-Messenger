package ClienteV2.BD;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import ServidorV2.Logs.Datos;
import ServidorV2.Logs.Log_errores;

/*
 * Clase que realiza la conexión a la base de datos remota
 */
public class Conexion {

	private static Datos datos = new Datos();
	private static String host = datos.getProperties("resources/Flash.properties", "host");
	private static String nombre_BD = datos.getProperties("resources/Flash.properties", "nombre");
	private static String usuario = datos.getProperties("resources/Flash.properties", "usuario");
	private static String pass = datos.getProperties("resources/Flash.properties", "contrasenia");
	public ArrayList<String> ips = new ArrayList<String>();


	public Connection initBD() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String servidor = "jdbc:mysql://" + host + "/" + nombre_BD;
			return DriverManager.getConnection(servidor, usuario, pass);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"No se ha podido establecer la conexión " + e);
			Log_errores.log( Level.SEVERE, "No se ha podido establecer la conexión: " + e.getMessage(), e );
			return null;
		}
	}

	public Statement usarBD(Connection con) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return null;
		}

	}
	
	/*
	 * Método que muestra el contenido de la tabla del servidor
	 */
	public void servidorObtener(Statement stat){
		String ip = "";

		try {
			ResultSet rs = stat.executeQuery("select * from servidor");
			while (rs.next()) {
				ip = rs.getString("ip");
				if(ip != null){
					ips.add(ip);
				}
			}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
	}
	
	/**
	 *Método que indica si hay disponible una conexión a internet
	 * @return true si hay internet
	 * @return false si no hay internet
	 */
	public boolean TestInternet() {

		String web = "www.google.es";
		int puerto = 80;
		boolean hayinternet = false;
		Socket test = null;
		try {
			test = new Socket(web, puerto);
			if (test.isConnected()) {
				// Si hay internet comprobamos el puerto 3306
				if (TestPuerto() == true) {
					hayinternet = true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		//Cerramos el puerto
		try {
			test.close();
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
		return hayinternet;
	}

	/*
	 * Método que comprueba si el puerto necesario para conectarse a la bd
	 * está abierto o cerrado
	 * @return true si está abierto
	 * @return false si está cerrado
	 */
	private boolean TestPuerto() {
		boolean abierto = true;
		try {
			Socket s = null;
			try{
			//Comprobamos con nuestro host
			s = new Socket("www.phpmyadmin.co", 3306);
			}catch(ConnectException e){
				Log_errores.log( Level.SEVERE, "Puerto cerrado: " + e.getMessage(), e );
				abierto = false;
			}
			//Cerramos el puerto
			s.close();
		} catch (UnknownHostException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		}
		return abierto;
	}

	public ArrayList<String> getIps() {
		return ips;
	}

	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}
	/**
	 * Método que compara la versión del cliente actual con la última versión
	 * @param st
	 * @return true si está actualizado, false si no
	 */
	public boolean hayNuevaVersion(Statement st){
		String version = "0";
		try {
			ResultSet rs = st.executeQuery("select * from actualizaciones");
			while (rs.next()) {
				version = rs.getString("vCliente");
			}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
		
		if(version.equals(datos.getProperties("resources/Flash.properties", "vCliente"))){
			return true;
		}else{
			return false;
		}
	}
}
