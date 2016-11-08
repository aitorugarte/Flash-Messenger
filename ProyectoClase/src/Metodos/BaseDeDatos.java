package Metodos;

import java.sql.*;

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
		String sql = "CREATE TABLE CLIENTE (ID_CLIENTE INT NOT NULL PRIMARY KEY, Usuario TEXT NOT NULL, Contraseña text NOT NULL, Correo text NOT NULL, Telefono int NOT NULL);";

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

}
