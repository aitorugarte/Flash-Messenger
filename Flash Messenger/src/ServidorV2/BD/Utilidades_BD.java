package ServidorV2.BD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

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
}
