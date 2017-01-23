package ServidorV2.BD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

import ServidorV2.Hilos.H_Servidor;
import ServidorV2.Logs.Log_errores;

/*
 * Clase padre de las bases de datos
 */
public class BD_Padre {

	protected Connection conexion;
	protected Statement stat;
	public static String nomb;
	public static ArrayList<String[]> contenido = new ArrayList<String[]>();

	
	public BD_Padre(Connection conexion, Statement stat) {
		this.conexion = conexion;
		this.stat = stat;
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public Statement getStat() {
		return stat;
	}

	public void setStat(Statement stat) {
		this.stat = stat;
	}
	/** Añade un cliente a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param st Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente a la habitación)
	 * @param nombre nombre del usuario
	 * @param contraseña	contaseña a añadir en la base de datos
	 * @param correo correo a añadir en la base de datos
	 * @returntrue si la inserción es correcta, false en caso contrario
	 */
	public boolean clienteInsert(Statement st, String nombre, String contraseña, String correo) {
		String sentSQL = "";
		try {
			sentSQL = "insert into cliente (usuario, contraseña, correo) values(" +
					"'" + nombre + "'," +
					"'" + contraseña + "'," +
					"'" + correo +  "')";
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


	/*
	 * Método que muestra el contenido de la base de datos
	 */
	public void obtenerContenido() {
		if(contenido != null){
			contenido.removeAll(contenido);
		}
		try {
			ResultSet rs = stat.executeQuery("select * from cliente");
			while (rs.next()) {
				String nombre = rs.getString("usuario");
				String contraseña = rs.getString("contraseña");
				String correo = rs.getString("correo");
				String cadena[] = {nombre, contraseña, correo};

				contenido.add(cadena);
			}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
	
	}
	/**
	 * Método que busca si existe el usuario
	 * @param usuario nombre de usuario
	 * @param st
	 * @param con
	 * @return true si existe, falte si no
	 */
	 
	public boolean existeUsuario(String usuario, Statement st, Connection con){
		
		try {
			ResultSet rs = st.executeQuery("select usuario, contraseña from cliente");
			while (rs.next()) {
				  String nombre = rs.getString("usuario");
				  String contraseña = rs.getString("contraseña");
				  String juntado = nombre + " " + contraseña;
				  
				  if(usuario.equals(juntado)){
					  for (int i = 0; i < H_Servidor.clientesActivos.size(); i++) {
						 if(nombre.equals(H_Servidor.clientesActivos.get(i).getNombre())){
							return false;
						 }
					}
					  nomb = nombre;
					  return true;
				  }
				}
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
		return false;
		
	}
	
}
