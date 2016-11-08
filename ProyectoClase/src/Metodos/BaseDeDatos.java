package Metodos;

import java.sql.*;

public class BaseDeDatos {

	private static Connection conexion = null;
	private static String bd = "Base de Datos de chat messenger";
	private static String usuario = "joseba";
	private static String contraseña = "garcia";

	// driver para el sql
	private static String driver = "com.mysql.jdbc.Driver";
	// ruta del servidor
	private static String server = "jdbc:mysql://localhost/" + bd;

	// metodo necesario para conectarse al driver y utilizar sql
	public static void conectar() {
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(server, usuario, contraseña);
		} catch (Exception e) {
			System.out.println("Error: Imposible realizar la conexion a BD.");
			e.printStackTrace();
		}
	}

	// metodo para establecer la conexion con la base de datos

	private static Statement conexion() {
		Statement st = null;
		try {
			st = conexion.createStatement();
		} catch (SQLException e) {
			System.out.println("Error: Conexión incorrecta.");
			e.printStackTrace();
		}
		return st;
	}

	// metodo para realizar consultas de tipo: SELECT * fROM tabla
	private static ResultSet consultaQuery(Statement st, String cadena) {
		ResultSet rs = null;
		try {
			rs = st.executeQuery(cadena);
		} catch (SQLException e) {
			System.out.println("Error con: " + cadena);
			System.out.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return rs;
	}

	// Método para realizar consultas de actualización, creación o eliminación.
	private static int consultaActualiza(Statement st, String cadena) {
		int rs = -1;
		try {
			rs = st.executeUpdate(cadena);
		} catch (SQLException e) {
			System.out.println("Error con: " + cadena);
			System.out.println("SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return rs;
	}

	// metodo para cerrar la consulta
	private static void cerrar(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				System.out.print("Error: No es posible cerrar la conexión.");
			}
		}
	}

	public static void main(String[] args) throws SQLException {

		System.out.println("Inicialización de la ejecución");
		conectar();
		// realiza la conexion
		Statement st = conexion();

		// si existe una tabla cliente la eliminamos
		String cadena = "DROP TABLE IF EXIST CLIENTE";
		consultaActualiza(st, cadena);

		// creamos la tabla
		cadena = "CREATE TABLE CLIENTE ('Identificador' int (10) NOT NULL AUTO_INCREMENT, 'Usuario' VARCHAR (15) NOT NULL, 'Contraseña' VARCHAR (20) NOT NULL, 'Correo' VARCHAR (30) NOT NULL, 'Telefono' int (9), PRIMARY KEY (Identificador, Usuario, Contraseña, correo))";
		consultaActualiza(st, cadena);

		// insertamos una fila en la tabla cliente
		cadena = "INSERT INTO CLIENTE ('Identificador', 'Usuario', 'Contraseña', 'Correo', 'Telefono') VALUES (1,'joseba','garcia', 'joseba.garcia@opendeusto.es', 639846928)";
		consultaActualiza(st, cadena);

		// Se sacan los datos de la tabla personal
		cadena = "SELECT * FROM CLIENTE;";
		ResultSet rs = consultaQuery(st, cadena);
		if (rs != null) {
			System.out.println("El listado de clientes es el siguiente:");

			while (rs.next()) {
				System.out.println("  ID: " + rs.getObject("Identificador"));
				System.out.println("  El usuario y la contraseña son: " + rs.getObject("Usuario") + " "
						+ rs.getObject("Contraseña"));

				System.out.println("  Contacto: " + rs.getObject("Telefono") + " " + rs.getObject("Correo"));

				System.out.println("- ");
			}
			cerrar(st);
		}
		cerrar(st);
		System.out.println("FIN DE EJECUCIÓN.");
	}

}
