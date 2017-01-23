package ServidorV2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import ServidorV2.Logs.Log_errores;

/*
 * Clase de la base de datos local, en el propio pc
 */
public class BD_Local extends BD_Padre{

	private static BD_Local mybd;
	
	private BD_Local(Connection conexion, Statement stat) {
		super(conexion, stat);
		mybd = this;
	}
	public static BD_Local getBD(){
		if(mybd == null){
			Connection conex = initBD();
			Statement stat = usarBD(conex);
			new BD_Local(conex, stat);
		}
		return mybd;
	}
	
	private static Connection initBD() {
		try {
		    Class.forName("org.sqlite.JDBC");
		    return DriverManager.getConnection("jdbc:sqlite:ProyectoFlash.db");
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null,"No se ha podido establecer la conexión " + e);
			Log_errores.log( Level.SEVERE, "No se ha podido establecer la conexión: " + e.getMessage(), e );
			return null;
		}
	}

	private static Statement usarBD(Connection con) {
		try {
			Statement stat = con.createStatement();
			stat.setQueryTimeout(30);  // poner timeout 30 msg
			return stat;
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return null;
		}
	}
	/** Crea las tablas de la base de datos. Si ya existen, las deja tal cual
	 * @param con	Conexión ya creada y abierta a la base de datos
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public Statement crearTablasBD() {
		try {
			stat = conexion.createStatement();
			stat.setQueryTimeout(30);
			try {
				stat.executeUpdate("create table cliente " +
					"(id_cliente integer primary key autoincrement,"
					+ "usuario text not null, "
					+ "contraseña text not null, "
					+ "correo text not null)");
			} catch (SQLException e) {} // Tabla ya existe. Nada que hacer		
			System.out.println("Conectado con base de datos local.");
			return stat;
		} catch (SQLException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return null;
		}
	}
}
