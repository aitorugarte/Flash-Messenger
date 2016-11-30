package JFileChooser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Principal extends JFrame {
 
 JPanel pdatos;
 JLabel limagen,lnom,lpeso;
 
 public Principal(){
  JPanel parriba=new JPanel();
  parriba.add(new JLabel("Seleccione una Imagen: "));
  JButton btnabrir=new JButton("Abrir");
  btnabrir.addActionListener(new ActionListener(){


   @Override
   public void actionPerformed(ActionEvent e) {
    JFileChooser selector=new JFileChooser();
    FileNameExtensionFilter filtroImagen=new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
    selector.setFileFilter(filtroImagen);
    int r=selector.showOpenDialog(null);
    if(r==JFileChooser.APPROVE_OPTION){
     try {
      File f=selector.getSelectedFile();
      lnom.setText(f.getName());
      lpeso.setText(""+f.length());
      ImageIcon img=new ImageIcon(selector.getSelectedFile().toURL());
      limagen.setIcon(img);
     } catch (MalformedURLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
     }
    }
   }
   
  });
  parriba.add(btnabrir);
  
  limagen=new JLabel();
  JPanel pimg=new JPanel();
  pimg.add(limagen);
  
  pdatos=new JPanel();
  pdatos.add(new JLabel("Nombre"));
  lnom=new JLabel();
  pdatos.add(lnom);
  pdatos.add(new JLabel("Peso"));
  lpeso=new JLabel();
  pdatos.add(lpeso);
  
  add(parriba,BorderLayout.NORTH);
  add(pdatos);
  add(pimg,BorderLayout.SOUTH);
  
 }
 
 public static void main(String arg[]){
  Principal p=new Principal();
  p.setBounds(400, 400, 400, 400);
  p.setVisible(true);
  p.setLocationRelativeTo(null);
  p.setDefaultCloseOperation(EXIT_ON_CLOSE);
 }
}