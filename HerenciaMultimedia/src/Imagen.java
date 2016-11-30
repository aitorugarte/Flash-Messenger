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
import java.util.Scanner;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class Imagen extends Envio {
	
		
	  public static void captura(String fileName) throws Exception{
		  Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		  Rectangle screenRectangle = new Rectangle(screenSize);
		  Robot robot = new Robot();
		  BufferedImage image = robot.createScreenCapture(screenRectangle);
		  ImageIO.write(image, "png", new File(fileName));
	  	 }

	  public static void main(String[] args) throws Exception {
		  Scanner sc = new Scanner(System.in);
		  System.out.println("¿Qué nombre quieres que tenga la captura?");
		  String nombreImagen = sc.next();
		  String FILENAME="C:/Users/Javier/Pictures/" + nombreImagen;
		  Imagen.captura(FILENAME);
		  System.out.println("[ Captura finalizada ]");
		  }

}
