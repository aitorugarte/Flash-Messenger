package ClienteV2;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

public class BarraProgreso extends JFrame {

	private JPanel contentPane;
	private JProgressBar progressBar;
	private JLabel lblCargando;
	

	public BarraProgreso() {

	    
	    setSize(180, 98);		
	    setLocationRelativeTo(null);
	    setUndecorated(true);
	    setVisible(true);
		
		setResizable(false);
		setAlwaysOnTop(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setBounds(10, 58, 160, 23);
		contentPane.add(progressBar);
		progressBar.setCursor (Cursor.getPredefinedCursor (Cursor.WAIT_CURSOR));
		
		lblCargando = new JLabel("Buscando servidores...");
		lblCargando.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCargando.setBounds(10, 11, 183, 36);
		contentPane.add(lblCargando);
	
	}
}
