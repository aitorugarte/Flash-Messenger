package ServidorV2.BD;

import java.sql.Connection;
import java.sql.Statement;


public class PruebasBD {
	
	public static void main(String[] args) {
		
		
		//BD_Local2 local = BD_Local2.getBD().clienteInsert(st, nombre, contrase�a, correo);
	
		
		/*BD_Local local = BD_Local.getBD();
		local.crearTablasBD();
		Connection conex = local.getConexion();
		Statement stat = local.getStat();*/
		
		BD_Remota remota = BD_Remota.getBD();
		Connection conex = remota.getConexion();
		Statement stat = remota.getStat();
		
		BD_Padre padre = new BD_Padre(conex, stat);
	   padre.clienteInsert(stat, "Aitor3", "12345678", "aitorugarte19@gmail.es");
	//	System.out.println("Cliente introducido");
		padre.mostrarContenido();
	//	BD_Remota2 remota = BD_Remota2.getBD(host, nombre_BD, usuario, pass);

		
	}

}