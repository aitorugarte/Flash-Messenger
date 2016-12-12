package ServidorV2.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class BD_Remota extends BD_Padre{

	private static String host;
	private static String nombre_BD;
	private static String usuario;
	private static String pass;
	private static BD_Remota mybd;
	
	public BD_Remota(Connection conexion, Statement stat) {
		super(conexion, stat);
		mybd = this;
	}

	public static BD_Remota getBD(String host, String nombre, String usuario, String pass){
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
			return null;
		}
	}

	public static Statement usarBD(Connection con) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);  // poner timeout 30 msg
			return statement;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

}
