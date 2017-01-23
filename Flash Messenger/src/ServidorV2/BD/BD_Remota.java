package ServidorV2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import ServidorV2.Logs.Datos;
import ServidorV2.Logs.Log_errores;

/*
 * Clase de la base de datos remota
 */
public class BD_Remota extends BD_Padre{

	private static Datos datos = new Datos();
	private static String host = datos.getProperties("resources/Flash.properties", "host");
	private static String nombre_BD = datos.getProperties("resources/Flash.properties", "nombre");
	private static String usuario = datos.getProperties("resources/Flash.properties", "usuario");
	private static String pass = datos.getProperties("resources/Flash.properties", "contrasenia");
	private static BD_Remota mybd;
	
	public BD_Remota(Connection conexion, Statement stat) {
		super(conexion, stat);
		mybd = this;
	}

	public static BD_Remota getBD(){
		if(mybd == null){
			Connection conex = initBD();
			Statement stat = usarBD(conex);
			new BD_Remota(conex, stat);
		}
		return mybd;
	}
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
	/** Añade un cliente a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la habitación)
	 * @param ip dirección ip del servidor activo
	 */
	public boolean servidorInsert(Statement st, String ip) {
		String sentSQL = "";
		try {
			sentSQL = "insert into servidor (ip) values(" +
					"'" + ip + "')";
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Método que borra la ip del servidor de la tabla servidor
	 * @param st
	 * @param ip del servidor
	 * @return
	 */
	public boolean servidorDelete(Statement st, String ip){
		String sentSQL = "";
		try {
			sentSQL = "delete from servidor where ip = '" + ip + "'";
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Método que compara la versión del servidor actual con la última versión
	 * @param st
	 * @return true si está actualizado, false si no
	 */
	public boolean hayNuevaVersion(Statement st){
		String version = "0";
		try {
			ResultSet rs = stat.executeQuery("select * from actualizaciones");
			while (rs.next()) {
				version = rs.getString("vServidor");

			}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
		
		if(version.equals(datos.getProperties("resources/Flash.properties", "version"))){
			return true;
		}else{
			return false;
		}
	}
}
