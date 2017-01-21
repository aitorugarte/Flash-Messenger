package ServidorV2;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ServidorV2.Ventana_Servidor;
import ServidorV2.BD.BD_Local;
import ServidorV2.BD.BD_Padre;
import ServidorV2.BD.BD_Remota;
import ServidorV2.Hilos.H_Comunicacion;
import ServidorV2.Hilos.H_EnviarIp;
import ServidorV2.Hilos.H_Servidor;
import ServidorV2.Logs.Log_chat;
import ServidorV2.Logs.Log_errores;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Clase principal del Servidor
 */
public class Ventana_Servidor extends JFrame {

	private static final long serialVersionUID = 930849024921895057L;
	private JPanel contentPane, p_oeste, p_centro, p_este, p_sur, p_norte;
	private String ip; // ip del cliente
	private static Connection conex = null;
	private static Statement stat = null;
	private static BD_Remota remota;
	private static BD_Local local;
	private static BD_Padre padre;
	private static boolean hayInternet = Analisis_Red.TestInternet();
	private JLabel lbTitulo, lblUsuariosActivos, lbVisualizar, lbOpciones;
	private JButton btnExpulsar, btnBaseDeDatos, btnRedWifi, btnRegistro, btnEliminar, btnEscanear;
	private JScrollPane scrollPane1, scrollPane2;
	private JTable table;
	private DefaultTableModel modelo;
	private JList<Integer> lista;
	public DefaultListModel<Integer> model;
	public static ArrayList<Integer> puertos;
	private MiBoton btnDesconectar, btnConectar;
	private JList list_activos;
	public static JProgressBar progressBar;

	public Ventana_Servidor() {
		Ini();
		Add();
		Comp();
		Actions();
	}

	private void Ini() {
		btnRedWifi = new JButton("Red wifi");
		contentPane = new JPanel();
		p_norte = new JPanel();
		lbTitulo = new JLabel("Panel de control del Servidor");
		p_oeste = new JPanel();
		scrollPane1 = new JScrollPane();
		lblUsuariosActivos = new JLabel("Usuarios activos");
		btnExpulsar = new JButton("Expulsar");
		p_centro = new JPanel();
		lbVisualizar = new JLabel("En el panel aparecer\u00E1 lo que pulse");
		scrollPane2 = new JScrollPane();
		p_este = new JPanel();
		lbOpciones = new JLabel("Opciones");
		btnBaseDeDatos = new JButton("Base de datos");
		btnRegistro = new JButton("Registro");
		p_sur = new JPanel();
		btnDesconectar = new MiBoton("Desconectado");
		btnConectar = new MiBoton("Conectar");
		list_activos = new JList();
		progressBar = new JProgressBar();
	}

	private void Add() {
		setAlwaysOnTop(true);
		setTitle("Flash Messenger");
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		p_norte.setLayout(new BorderLayout(0,0));
		p_oeste.setLayout(new BorderLayout(0, 0));
		p_centro.setLayout(new BorderLayout(0, 0));
		p_este.setLayout(new GridLayout(0, 1, 0, 0));
		p_sur.setLayout(new BorderLayout(0, 0));

		p_norte.add(lbTitulo, BorderLayout.CENTER);
		p_norte.add(btnDesconectar, BorderLayout.EAST);
		p_norte.add(btnConectar, BorderLayout.WEST);
		p_este.add(lbOpciones);
		p_este.add(btnBaseDeDatos);
		p_este.add(btnRedWifi);
		p_este.add(btnRegistro);
		p_sur.add(progressBar);

		p_oeste.add(scrollPane1, BorderLayout.CENTER);
		p_oeste.add(btnExpulsar, BorderLayout.SOUTH);
		p_centro.add(lbVisualizar, BorderLayout.NORTH);
		p_centro.add(scrollPane2, BorderLayout.CENTER);

		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(p_sur, BorderLayout.SOUTH);
		contentPane.add(p_oeste, BorderLayout.WEST);
		contentPane.add(p_centro, BorderLayout.CENTER);
		contentPane.add(p_este, BorderLayout.EAST);
		contentPane.add(p_norte, BorderLayout.NORTH);

	}

	private void Comp() {
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		lbTitulo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitulo.setPreferredSize(new Dimension(10, 40));
		scrollPane1.setViewportView(list_activos);
		scrollPane1.setPreferredSize(new Dimension(150, 0));
		lblUsuariosActivos.setFont(new Font("Tahoma", Font.BOLD, 12));
		scrollPane1.setColumnHeaderView(lblUsuariosActivos);
		lblUsuariosActivos.setHorizontalAlignment(SwingConstants.CENTER);
		btnExpulsar.setVerticalAlignment(SwingConstants.BOTTOM);
		lbVisualizar.setHorizontalAlignment(SwingConstants.CENTER);
		lbVisualizar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbOpciones.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbOpciones.setHorizontalAlignment(SwingConstants.CENTER);
		progressBar.setBackground(Color.ORANGE);
		progressBar.setStringPainted(true);
		btnDesconectar.setBackground(Color.yellow);
	}

	private void Actions() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				/*if(hayInternet == true){
					try {
						remota.servidorDelete(stat, Inet4Address.getLocalHost().getHostAddress());
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
				}*/
			}
		});
		btnBaseDeDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cargarTabla();
			}
		});

		btnRedWifi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarListaPuertos();
			}
		});
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnConectar.setBackground(Color.yellow);
				btnConectar.setText("Conectado");
				btnDesconectar.setBackground(getBackground());
				btnDesconectar.setText("Desconectar");
				H_Comunicacion comunicarse = new H_Comunicacion();
				comunicarse.start();
				
				Calendar calendario = GregorianCalendar.getInstance();
				try {
					Log_chat.empezarLog(calendario);
				} catch (IOException e) {
					e.printStackTrace();
				}
				runServer();
			}
		});
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnConectar.setBackground(getBackground());
				btnConectar.setText("Conectar");
				btnDesconectar.setBackground(Color.yellow);
				btnDesconectar.setText("Desconectado");
				if(hayInternet == true){
					try {
						remota.servidorDelete(stat, Inet4Address.getLocalHost().getHostAddress());
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	public void cargarTabla() {

		modelo = new DefaultTableModel();
		String[] fila = { "Aitor", "123", "aitorugarte@opendeusto.es" };

		modelo.addColumn("Nombre");
		modelo.addColumn("Contraseña");
		modelo.addColumn("Correo electrónico");
		modelo.addRow(fila);

		table = new JTable();
		table.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (table.getSelectedRow() != -1) {
					System.out.println("Fila modificada: " + table.getSelectedRow());
					System.out.println("Cambios guardados.");
				}
			}
		});
		scrollPane2.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setModel(modelo);

		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(70);

		if (p_centro.isAncestorOf(btnEscanear)) {
			p_centro.remove(btnEscanear);
		}
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Fila número: " + table.getSelectedRow());
			}
		});
		
		p_centro.add(btnEliminar, BorderLayout.SOUTH);
		lbVisualizar.setText("Contenido de la Base de Datos");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 6)); // Para que se actualice la ventana y aparezca el botón
	}

	public void cargarListaPuertos() {

		if (model != null) {
			scrollPane2.setViewportView(lista);
			lista.setModel(model);
		}

		btnEscanear = new JButton("Escanear red");
		btnEscanear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lista = new JList<Integer>();
				model = new DefaultListModel<>();
				progressBar.setMaximum(65535);
				iniciar.start();
			}
		});
		lbVisualizar.setText("Puertos abiertos en la red wifi");
		if (p_centro.isAncestorOf(btnEliminar)) {
			p_centro.remove(btnEliminar);
		}
		if (scrollPane2.isAncestorOf(table)){
			scrollPane2.setViewportView(null);;
		}
		p_centro.add(btnEscanear, BorderLayout.SOUTH);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 6)); // Para que se actualice la ventana y aparezca el botón

	}

	Thread iniciar = new Thread() {
		@Override
		public void run() {
			
			ExecutorService executor = Executors.newCachedThreadPool();
			int inicio = 1;
			int fin = 100;
			long s = System.currentTimeMillis();
			while (fin <= 65500) {
				executor.execute(new ejecutarTareas(inicio, fin));
				inicio += 100;
				fin += 100;
			}
			executor.execute(new ejecutarTareas(65501, 65535));

			executor.shutdown();

			try {
				executor.awaitTermination(4, TimeUnit.MINUTES);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}

			System.out.println("Ha tardado: " + (System.currentTimeMillis() - s) / 1000 + " segundos");
			progressBar.setValue(65535);

			puertos = Analisis_Red.almacen;
			Collections.sort(puertos);
			for (int i = 0; i < puertos.size(); i++) {
				model.addElement(puertos.get(i));
			}
			//TODO no se muestran en una lista los elementos -_-
		}
	};
	//Método que busca el usuario y la contraseña en la BD
		public static boolean buscarUsuario(String usuario){
			boolean hay = padre.existeUsuario(usuario, stat, conex);
			return hay;
		}
		
		/*
		 * Método que divide la cadena de texto para ser insertada
		 */
		public static void dividir(String algo) {

			String nombre = "";
			String contraseña = "";
			String correo = "";

			// Encontar la posición de los espacios
			int espacio = 0;
			int espacio2 = 0;

			for (int i = 0; i < algo.length(); i++) {

				if (algo.charAt(i) == ' ') {
					if (espacio == 0) {
						espacio = i;
					} else {
						espacio2 = i;
					}
				}
			}
			// Formamos las palabras
			int x = 0;
			while (x < espacio) {

				nombre = nombre + algo.charAt(x);
				x++;
			}
			espacio = espacio + 1;

			while (espacio < espacio2) {

				contraseña = contraseña + algo.charAt(espacio);
				espacio++;
			}
			espacio2 = espacio2 + 1;
			while (espacio2 < algo.length()) {

				correo = correo + algo.charAt(espacio2);
				espacio2++;
			}

			padre.clienteInsert(stat, nombre, contraseña, correo);

		}
		
		/*
		 * Mëtodo que corre el servidor
		 */
		public void runServer() {

			ServerSocket servidor1 = null;
		
			boolean activo = true;
			
			try {

				servidor1 = new ServerSocket(8000);

				
				while (activo) {
				
					Socket socket = null;
				
					try {
						
						socket = servidor1.accept();

					} catch (IOException e) {
						Log_errores.log( Level.SEVERE, "Error al unirse al servidor: " + e.getMessage(), e );
					    JOptionPane.showInputDialog("Error al unirse al servidor : " + e.getMessage());
						continue;
					}
					
					ip = (((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
					
					//Activamos el usuario (nombre, puerto1, puerto2, la ventana, su ip);
					H_Servidor user = new H_Servidor(BD_Padre.nomb, socket, this, ip);
					user.start();
				}

			} catch (IOException e) {
				Log_errores.log( Level.SEVERE, "Error " + e.getMessage(), e );
				JOptionPane.showInputDialog("Error: " + e.getMessage());
			}
		}
	/*
	 * Main del programa Servidor
	 */
	public static void main(String[] args) throws IOException {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
		}
		Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.jpg");

		if(hayInternet == true){
			padre = new BD_Padre(conex, stat);
			remota = BD_Remota.getBD();
			stat = remota.getStat();
			conex = remota.getConexion();
			remota.servidorInsert(stat, Inet4Address.getLocalHost().getHostAddress());
			System.out.println("Hay internet");
		}else{
			padre = new BD_Padre(conex, stat);
			local = BD_Local.getBD();
			local.crearTablasBD();
			stat = local.getStat();
			conex = local.getConexion();
			System.out.println("No hay internet");
			
		H_EnviarIp enviar = new H_EnviarIp();
		enviar.start();
		}
	
		Ventana_Servidor servidor = new Ventana_Servidor();
		servidor.setIconImage(icon);
		servidor.setVisible(true);

	}

	class MiBoton extends JButton {

		
		public MiBoton(String texto) {
			super(texto);
			setContentAreaFilled(false);
		}
		
		protected void paintComponent(Graphics g) {
		
			g.setColor(getBackground());
			g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

			super.paintComponent(g);
		}

		protected void paintBorder(Graphics g) {
			g.setColor(getForeground());
			g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
		}

		Shape shape;

		public boolean contains(int x, int y) {
			if (shape == null || !shape.getBounds().equals(getBounds())) {
				shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
			}
			return shape.contains(x, y);
		}

	}
}
