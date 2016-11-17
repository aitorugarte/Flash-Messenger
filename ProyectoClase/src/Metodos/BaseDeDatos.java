package Metodos;

import java.sql.*;

//Prueba
public class BaseDeDatos {

	// atributos conexion y sentencia
	private static Connection conexion;
	private static Statement sentencia;

	// main
	public static void main(String[] args) {

		BaseDeDatos prueba = new BaseDeDatos();
		prueba.init();

		System.out.println("La base de datos se ha conectado");

		// creamos la tabla cliente
		String sql = "CREATE TABLE CLIENTE (ID_CLIENTE INT NOT NULL PRIMARY KEY, Usuario TEXT NOT NULL, Contraseña text NOT NULL, Correo text NOT NULL);";

		try {
			prueba.getStatement().executeUpdate(sql);
			System.out.println("Execute query successfully");

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		System.out.println("Table created successfully");

	}

	// metodo para iniciar la conexion
	public void init() {

		try {
			Class.forName("org.sqlite.JDBC");
			conexion = DriverManager.getConnection("jdbc:sqlite:ProyectoFlash.db");
			sentencia = conexion.createStatement();
			sentencia.setQueryTimeout(30);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// metodo para cerrar la conexion
	public void close() {

		try {
			if (sentencia != null)
				sentencia.close();
			if (conexion != null)
				conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// metodo que nos devuelve la sentencia
	public Statement getStatement() {
		return sentencia;

	}

	/*
	 * Host: sql7.freesqldatabase.com Database name: sql7143766 Database user:
	 * sql7143766 Database password: sNeuAEMbIX
	 */
	private String host = "";
	private String nombre = "";
	private String usuario = "";
	private String pass = "";

	public Connection getConexionMYSQL() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String servidor = "jdbc:mysql://" + host + "/" + nombre;
			conexion = DriverManager.getConnection(servidor, usuario, pass);
			return conexion;
		} catch (Exception e) {
			return conexion;
		}
	}

	// metodo que crea el usuario y lo introduce en la base de datos
	public int IngresarUsuario(String Usuario, String Contraseña, String Correo) {

		int resultado = 0;

		Connection con = null;

		String SSQL = "INSERT INTO CLIENTE (Usuario, Contraseña, CorreoElectronico)" + "VALUES (?, ?, ?)";
		try {

			con = getConexionMYSQL();

			PreparedStatement psql = (PreparedStatement) con.prepareStatement(SSQL);
			psql.setString(1, Usuario);
			psql.setString(2, Correo);
			psql.setString(3, Contraseña);

			resultado = psql.executeUpdate();

			psql.close();

		} catch (SQLException e) {
		} finally {

			try {

				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {

			}

		}
		return resultado;
	}

}
