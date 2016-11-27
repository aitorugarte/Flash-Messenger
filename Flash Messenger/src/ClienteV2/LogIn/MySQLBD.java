package ClienteV2.LogIn;

import java.sql.*;

/**
 * @author Monillo007
 * :: Visita http://monillo007.blogspot.com ::
 */
public class MySQLBD {

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public MySQLBD conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String BaseDeDatos = "jdbc:mysql://localhost/test?user=usuario&password=123";
            setConexion(DriverManager.getConnection(BaseDeDatos));
            if (conexion != null) {
                System.out.println("Conexion exitosa!");
            } else {
                System.out.println("Conexion fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public ResultSet consultar(String sql) {
        ResultSet resultado;
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultado;
    }

    public boolean ejecutar(String sql) {
        try {
            Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MySQLBD baseDatos = new MySQLBD().conectar();

        if (baseDatos.ejecutar("INSERT INTO TEST(IDENTIFICADOR,DESCRIPCION) VALUES(3,'TRES')")) {
            System.out.println("Ejecución correcta!");
        } else {
            System.out.println("Ocurrió un problema al ejecutar!");
        }
        ResultSet resultados = baseDatos.consultar("SELECT * FROM TEST");
        if (resultados != null) {
            try {
                System.out.println("IDENTIFICADOR       DESCRIPCION");
                System.out.println("--------------------------------");
                while (resultados.next()) {
                    System.out.println("" + resultados.getBigDecimal("IDENTIFICADOR") + "       " + resultados.getString("DESCRIPCION"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
