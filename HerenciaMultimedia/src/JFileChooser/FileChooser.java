package JFileChooser;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
 
public class FileChooser extends JPanel implements ActionListener{
        private JFileChooser fileChooser;
        private JButton Buscar;
        private BufferedReader br;
        private File fichero;
        int returnVar;
        String currentLine;
       
        public FileChooser(){
                fileChooser = new JFileChooser();
                Buscar = new JButton("Buscar");
               
                setPreferredSize(new Dimension(200, 200));
                setLayout(null);
               
                add(Buscar);
               
                Buscar.setBounds(50, 80, 100, 25);
                Buscar.addActionListener(this);
        }
       
        public void actionPerformed(ActionEvent e){
                if(e.getSource() == Buscar){
                        returnVar = fileChooser.showOpenDialog(null);
                        if(returnVar == JFileChooser.APPROVE_OPTION){
                                fichero = fileChooser.getSelectedFile();
                               
                                try{
                                        br = new BufferedReader(new FileReader(fichero));
                                       
                                        while((currentLine = br.readLine()) != null){
                                                System.out.println(currentLine);
                                        }
                                } catch (Exception error){
                                        error.printStackTrace();
                                }
                               
                        }
                }
        }
}