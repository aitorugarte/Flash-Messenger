package JFileChooser;

import java.util.Scanner;

import javax.swing.JFrame;

public class Main {
	
	 public static void main(String args[]){
    int eleccion = 0;
    Scanner sc = new Scanner(System.in);
    System.out.println("¿Qué tipo de archivo deseas ver?");
    eleccion =  sc.nextInt();
    switch(eleccion){
    case 1:
    	Imagen();
    	break;
    case 2:
    	TXT();
    	break;
    }
    
 }
	 
	 
	 public static void Imagen(){
		 Principal ventana = new Principal();
		   JFrame frame = new JFrame("Imagen");
		   ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   ventana.pack();
		   ventana.setBounds(400, 200, 400, 400);
	       ventana.setVisible(true);
	 }
	 public static void TXT(){
		   JFrame frame = new JFrame("TXT");
	         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	         frame.getContentPane().add(new FileChooser());
	         frame.pack();
	         frame.setVisible(true);
	 }
}
