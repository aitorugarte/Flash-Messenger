import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class EnviarArchivo {

	   private String nombreArchivo = "";
       
       public EnviarArchivo( String nombreArchivo )
       {
            this.nombreArchivo = nombreArchivo;
       }
       
       public void enviarArchivo( )
       {
       
        try
        {
           //IP
          InetAddress direccion = InetAddress.getByName("192.168.0.34");
          Socket socket = new Socket( direccion, 5000 );
          socket.setSoTimeout( 2000 );
          socket.setKeepAlive( true );
       
          // Se crea el archivo que vamos a enviar
          File archivo = new File( nombreArchivo );
       
          // Obtenemos el tamaño del archivo
          int tamañoArchivo = ( int )archivo.length();
       
          // Creamos el tunel de salida que nos permitirá enviar el archivo que deseamos 
          DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
       
          System.out.println( "Enviando Archivo: "+archivo.getName() );
       
          // Enviamos el nombre del archivo 
          dos.writeUTF( archivo.getName() );
       
          // Enviamos el tamaño del archivo
          dos.writeInt( tamañoArchivo );
       
          // Creamos flujo de entrada para realizar la lectura del archivo en bytes
          FileInputStream fichero = new FileInputStream( nombreArchivo );
          BufferedInputStream bis = new BufferedInputStream( fichero );
       
          // Creamos el flujo de salida para enviar los datos del archivo en bytes
          BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream()          );
       
          // Creamos un array de bytse  
          byte[] buffer = new byte[ tamañoArchivo ];
       
          // Leemos el archivo y lo introducimos en el array de bytes 
          bis.read( buffer ); 
       
          // Realizamos el envio de los bytes que conforman el archivo (los envía de a pocos)
          for( int i = 0; i < buffer.length; i++ )
          {
              bos.write( buffer[ i ] ); 
          } 
       
          System.out.println( "Archivo Enviado: "+archivo.getName() );
          // Cerramos socket y tuneles
          bis.close();
          bos.close();
          socket.close(); 
        }
        catch( Exception e )
        {
          System.out.println( e.toString() );
        }
       
       }
       
       // Lanzamos nuestro cliente para realizar el envio del archivo 
       // ubicado en C:\\Users\\Javier\\Pictures\\campo1.gif
       public static void main( String args[] )
       {
          EnviarArchivo ea = new EnviarArchivo( "C:\\Users\\Javier\\Pictures\\campo1.gif" );
          ea.enviarArchivo();
       }
       
	
}
