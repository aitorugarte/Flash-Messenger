package ServidorV2.BD;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ServidorV2.Logs.Log_errores;

import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.DefaultListModel;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;

public class Utilidades_BD extends JFrame {

	private static final long serialVersionUID = 6057563744123344711L;
	private JPanel contentPane;
	private Connection conex = null;
	private Statement stat = null;
	private DefaultListModel<String> modelo;
	private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Utilidades_BD frame = new Utilidades_BD();
		frame.setVisible(true);

	}

	/**
	 * Create the frame.
	 */
	public Utilidades_BD() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 479, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TODO tabla que muestre todo el contenido
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		modelo = new DefaultListModel<String>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 22, 212, 94);
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		mostrarClientes();
	} 
	
	public void mostrarClientes(){
		BD_Padre padre = new BD_Padre(conex, stat);
		ArrayList<String> clientes = new ArrayList<String>();
		clientes.add("Aitor 1234 aitorugarte@opendeusto.es");
		clientes.add("Joseba_Garcia 12354 josebagarcia@opendeusto.es");
		clientes.add("Javi99 45646416468 javiP56@opendeusto.es");
		
		for (int i = 0; i < clientes.size(); i++) {
			String cliente = clientes.get(i);
			modelo.addElement(cliente);
		}
		
		list.setModel(modelo);
		
	}
	public void setDatos(Statement stat, Connection conex){
		this.stat = stat;
		this.conex = conex;	
	}
	/**
	 *Método que indica si hay disponible una conexión a internet
	 * @return true si hay internet
	 * @return false si no hay internet
	 */
	public static boolean TestInternet() {

		String web = "www.google.es";
		int puerto = 80;
		boolean hayinternet = false;
		Socket test = null;
		try {
			test = new Socket(web, puerto);
			if (test.isConnected()) {
				// Si hay internet comprobamos el puerto 3306
				if (TestPuerto() == true) {
					hayinternet = true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		//Cerramos el puerto
		try {
			test.close();
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
		}
		return hayinternet;
	}

	/*
	 * Método que comprueba si el puerto necesario para conectarse a la bd
	 * está abierto o cerrado
	 * @return true si está abierto
	 * @return false si está cerrado
	 */
	private static boolean TestPuerto() {
		boolean abierto = true;
		try {
			Socket s = null;
			try{
			//Comprobamos con nuestro host
			s = new Socket("www.phpmyadmin.co", 3306);
			}catch(ConnectException e){
				Log_errores.log( Level.SEVERE, "Puerto cerrado: " + e.getMessage(), e );
				abierto = false;
			}
			//Cerramos el puerto
			s.close();
		} catch (UnknownHostException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log_errores.log( Level.SEVERE, "Error: " + e.getMessage(), e );
			e.printStackTrace();
			return false;
		}
		return abierto;
	}
}
