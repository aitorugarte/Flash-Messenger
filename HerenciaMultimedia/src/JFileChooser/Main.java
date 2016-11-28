package JFileChooser;

import javax.swing.JFrame;

public class Main {
	 public static void main(String args[]){
         JFrame frame = new JFrame("JFileChooser");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().add(new FileChooser());
         frame.pack();
         frame.setVisible(true);
 }
}
