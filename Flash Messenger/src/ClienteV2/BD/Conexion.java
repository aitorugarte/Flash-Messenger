package ClienteV2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import ServidorV2.Logger.Log_errores;

public class Conexion {

	private static String host = "sql7.freesqldatabase.com";
	private static String nombre_BD = "sql7143768";
	private static String usuario = "sql7143768";
	private static String pass = "edl72lc3Wt";
	public static ArrayList<String> ips = new ArrayList<String>();


	public static Connection initBD() {
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

	public static Statement usarBD(Connection con) {
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
	public static void servidorMostrar(Statement stat){
		try {
			ResultSet rs = stat.executeQuery("select * from servidor");
			while (rs.next()) {
				String ip = rs.getString("ip");
				ips.add(ip);
			}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
	}


}
