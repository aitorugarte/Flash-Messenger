package ServidorV2.BD;

import java.sql.*;
import java.util.*; 

import ServidorV2.HiloServidor;

/*
 * Clase de la gestión de la BD local 
 */
public class BD {

	private static Exception lastError = null;  // Información de último error SQL ocurrido
	
	
	public static void main(String[] args) {
		
	
		Connection con = BD.initBD();
		Statement stat = BD.usarCrearTablasBD( con );
	}
	/** Inicializa una BD SQLITE y devuelve una conexión con ella
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public static Connection initBD() {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:ProyectoFlash.db");
		    return con;
		} catch (ClassNotFoundException | SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Devuelve statement para usar la base de datos
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public static Statement usarCrearTablasBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			try {
				statement.executeUpdate("create table cliente " +
					"(id_cliente int not null primary key, usuario text not null,"
					+ " contraseña text not null, correo text not null)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer		
			return statement;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Reinicia en blanco las tablas de la base de datos. 
	 * UTILIZAR ESTE MËTODO CON PRECAUCIÓN. Borra todos los datos que hubiera ya en las tablas
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se borra correctamente, null si hay cualquier error
	 */
	public static Statement reiniciarBD( Connection con ) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			statement.executeUpdate("drop table if exists cliente");
			return usarCrearTablasBD( con );
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 * @param con	Conexión abierta de la BD
	 * @param st	Sentencia abierta de la BD
	 */
	public static void cerrarBD( Connection con, Statement st ) {
		try {
			if (st!=null) st.close();
			if (con!=null) con.close();
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
		}
	}
	
	/** Devuelve la información de excepción del último error producido por cualquiera 
	 * de los métodos de gestión de base de datos
	 */
	public static Exception getLastError() {
		return lastError;
	}
	

	/** Añade un cliente a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la habitación)
	 * @param h	cliente
	 * @param contraseña	contaseña a añadir en la base de datos
	 * @param correo correo a añadir en la base de datos
	 * @return	true si la inserción es correcta, false en caso contrario
	 */
	public static boolean clienteInsert( Statement st, HiloServidor h, String contraseña, String correo ) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente values(" +
					"1," +
					"'" + secu(h.getNombUser()) + "'," +
					"'" + secu(contraseña) + "'," +
					"'" + secu(correo) +  "')";
			int val = st.executeUpdate( sentSQL );
			if (val!=1) {  // Se tiene que añadir 1 - error si no
				return false;  
			}
			return true;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return false;
		}
	}

	/** Realiza una consulta a la tabla abierta de clientes de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario)
	 * @param h	cliente (no null)
	 * @param contraseña contraseña del cliente
	 * @param correo email del cliente
	 * @param codigoSelect	Sentencia correcta de WHERE (sin incluirlo) para filtrar la búsqueda (vacía si no se usa)
	 * @return	lista de clientes cargados desde la base de datos, null si hay cualquier error
	 */
	public static ArrayList<String> clienteSelect( Statement st, HiloServidor h, String contraseña, String correo, String codigoSelect ) {
		if (h==null) return null;
		String sentSQL = "";
		ArrayList<String> ret = new ArrayList<>();
		try {
			sentSQL = "select * from cliente";
			if (h!=null) {
				String where = "cliente_id=" + 1 
						+ "cliente_nombre='" + h.getNombUser() + "'"
						+ "cliente_contraseña='" + contraseña + "'"
						+ "cliente_correo='" + correo + "'";
				if (codigoSelect!=null && !codigoSelect.equals(""))
					sentSQL = sentSQL + " where " + where + " AND " + codigoSelect;
				else
					sentSQL = sentSQL + " where " + where;
			}
			if (codigoSelect!=null && !codigoSelect.equals(""))
				sentSQL = sentSQL + " where " + codigoSelect;
			// System.out.println( sentSQL );  // Para ver lo que se hace en consola
			ResultSet rs = st.executeQuery( sentSQL );
			while (rs.next()) {
				ret.add( rs.getString( "id_cliente" ) );
				ret.add( rs.getString( "usuario"));
				ret.add( rs.getString( "contraseña"));
				ret.add( rs.getString( "correo"));
			}
			rs.close();
			return ret;
		} catch (SQLException e) {
			lastError = e;
			e.printStackTrace();
			return null;
		}
	}

	// Devuelve el string "securizado" para volcarlo en SQL
	// (Implementación 1) Sustituye ' por '' y quita saltos de línea
	// (Implementación 2) Mantiene solo los caracteres seguros en español
	private static String secu( String string ) {
		// Implementación (1)
		// return string.replaceAll( "'",  "''" ).replaceAll( "\\n", "" );
		// Implementación (2)
		StringBuffer ret = new StringBuffer();
		for (char c : string.toCharArray()) {
			if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZñÑáéíóúüÁÉÍÓÚÚ.,:;-_(){}[]-+*=<>'\"¿?¡!&%$@#/\\0123456789".indexOf(c)>=0) ret.append(c);
		}
		return ret.toString();
	}	
}