import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Imagen extends Envio {

		public void Enviar(){
				try {		
		            BufferedImage bufferedImage = ImageIO.read(new File("/home/leyer/lsm.png"));
		            ImageIO.write(bufferedImage, "png", salida);
		            salida.flush();
		        }catch (Exception e) {
		            e.printStackTrace();
		        }
			}
		
		
	  public void captura(){
	  Rectangle rectangleTam = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	   try {
	   Robot robot = new Robot();
	   BufferedImage bufferedImage = robot.createScreenCapture(rectangleTam);
	   ByteArrayOutputStream salidaImagen = new ByteArrayOutputStream();
	   ImageIO.write(bufferedImage, "jpg", salidaImagen);
	   byte[] bytesImage = salidaImagen.toByteArray();
	   DatagramPacket dgp = new DatagramPacket(bytesImage, bytesImage.length, InetAddress.getByName("230.0.0.1"), 50000);	
	   MulticastSocket enviador = new MulticastSocket();
	   enviador.send(dgp);
	   System.out.println( "Se ha enviado la imagen" );
	   } 
	   catch (AWTException e) {
		   e.printStackTrace();
	   } // procesar los problemas que pueden ocurrir al enviar el objeto
	  catch ( IOException excepcionES ) {
		   System.out.println( "\nError al escribir el objeto" );
	  }
	  
	 }
}
