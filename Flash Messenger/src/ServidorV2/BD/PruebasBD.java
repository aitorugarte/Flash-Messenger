package ServidorV2.BD;

import java.sql.Connection;
import java.sql.Statement;


public class PruebasBD {

	private static String host = "sql7.freesqldatabase.com"; //+ puerto
	private static String nombre_BD = "sql7143768";
	private static String usuario = "sql7143768";
	private static String pass = "edl72lc3Wt";
	
	
	public static void main(String[] args) {
		
		
		//BD_Local2 local = BD_Local2.getBD().clienteInsert(st, nombre, contraseña, correo);
	
		
		BD_Local local = BD_Local.getBD();
		local.crearTablasBD();
		Connection conex = local.getConexion();
		Statement stat = local.getStat();
		
		BD_Padre padre = new BD_Padre(conex, stat);
		padre.clienteInsert(stat, "Aitor", "segb", "email");
		System.out.println("Cliente introducido");
		padre.mostrarContenido(stat, conex);
	//	BD_Remota2 remota = BD_Remota2.getBD(host, nombre_BD, usuario, pass);

		
	}

}
