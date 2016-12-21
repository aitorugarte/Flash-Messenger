package ClienteV2.BD;

import java.sql.Connection;
import java.sql.Statement;

public class Prueba {

	public static void main(String[] args) {
		
		Connection con = Conexion.initBD();
		Statement stat = Conexion.usarBD(con);
		
		Conexion.servidorMostrar(stat);
		System.out.println(Conexion.ips);
	}

}
