import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RecibirArchivo {

    private ServerSocket servidor = null;
    
    public RecibirArchivo( ) throws IOException
    {
       // Creamos socket servidor escuchando en el mismo puerto donde se comunica el cliente
       servidor = new ServerSocket( 5000 );

       System.out.println( "Esperando archivos..." ); 
    }

    public void iniciarServidor()
    {
       while( true )
       {

         try
         {
            // Creamos el socket del servidor
            Socket cliente = servidor.accept(); 

            // Creamos flujo de entrada para leer los datos que envia el cliente 
            DataInputStream dis = new DataInputStream( cliente.getInputStream() );
     
            // Obtenemos el nombre del archivo
            String nombreArchivo = dis.readUTF().toString(); 

            // Obtenemos el tamaño del archivo
            int tam = dis.readInt(); 

            System.out.println( "Recibiendo archivo "+nombreArchivo );
     
            // Decidimos donde guardar el archivo
            FileOutputStream fos = new FileOutputStream( " C:\\"+nombreArchivo);
            BufferedOutputStream out = new BufferedOutputStream( fos );
            BufferedInputStream in = new BufferedInputStream( cliente.getInputStream() );

            // Creamos el array de bytes para leer los datos del archivo
            byte[] buffer = new byte[ tam ];

            // Obtenemos el archivo mediante la lectura de bytes enviados
            for( int i = 0; i < buffer.length; i++ )
            {
               buffer[ i ] = ( byte )in.read( ); 
            }

            // Escribimos el archivo 
            out.write( buffer ); 

            // Cerramos flujos
            out.flush(); 
            in.close();
            out.close(); 
            cliente.close();

            System.out.println( "Archivo Recibido "+nombreArchivo );
     
        }
        catch( Exception e )
        {
           System.out.println( e.toString() ); 
        }
      } 
    }
    
    // Lanzamos el servidor para la recepción de archivos
    public static void main( String args[] ) throws IOException
    {
        new RecibirArchivo().iniciarServidor(); 
    }
}
