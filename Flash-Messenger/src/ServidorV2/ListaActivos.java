package ServidorV2;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ListaActivos extends JFrame {

	private JPanel contentPane;
	private JLabel lblUsuariosActivos;
	private JList<String> listaNombres;
	private DefaultListModel<String> modelo;
	private JScrollPane scrollLista;
	private JButton btnEcharDelServidor;

	
	public ListaActivos() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 241, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblUsuariosActivos = new JLabel("Usuarios activos");
		lblUsuariosActivos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsuariosActivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariosActivos.setBounds(5, 5, 215, 37);
		contentPane.add(lblUsuariosActivos);
		
		//instanciamos la lista
		listaNombres = new JList<String>();
		listaNombres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION );
		   
		//instanciamos el modelo
		modelo = new DefaultListModel<String>();
		      
		//instanciamos el Scroll que tendra la lista
		scrollLista = new JScrollPane();
		scrollLista.setBounds(20, 53,181, 147);
		scrollLista.setViewportView(listaNombres);
		contentPane.add(scrollLista);
		
		btnEcharDelServidor = new JButton("Echar del servidor");
		btnEcharDelServidor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarNombre();	

					}
		});
		btnEcharDelServidor.setBounds(20, 220, 181, 23);
		contentPane.add(btnEcharDelServidor);
		
	}
	
	public void agregarNombre(){
		
		int tamanio = HiloServidor.clientesActivos.size();
		HiloServidor user = null;
		String datos;
	
			for (int i = 0; i < tamanio; i++) {
				
				user = HiloServidor.clientesActivos.get(i);
				datos = user.getNombUser() + "  " + user.getIp();
				user.interrupt();
				
				modelo.addElement(datos);
				listaNombres.setModel(modelo);
			}
		 
	
		
	}
	public void eliminarNombre(){
		
		 HiloServidor user = null;
		int indice = listaNombres.getSelectedIndex();
		
		modelo.removeElementAt(indice); //Borramos al usuario de la lista
		user = HiloServidor.clientesActivos.get(indice);
		user.desconectar(); //Echamos al usuario del servidor :)
		
	/*Falta que se cierre la ventana del usuario */
	}
}
