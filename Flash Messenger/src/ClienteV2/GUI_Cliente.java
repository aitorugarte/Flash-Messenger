package ClienteV2;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import ClienteV2.Encriptado.CesarRecursivo;
import ClienteV2.LogIn.Fichero;
import ClienteV2.LogIn.PanelForm;
import javax.swing.JToggleButton;

public class GUI_Cliente extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1458864985101903804L;
	private JPanel contentPane;
	private JPanel panUsus, panChat;
	private JButton btnEnviar;
	private JTextField textEnviar;
	private JLabel lblUsuariosConectados, lblConver;
	private JList<String> list;
	private DefaultListModel<String> modelo;
	private Cliente cliente;
	private String usuario;
	private JScrollPane scrollPane;
	private JEditorPane editorPane;
	private String/* seFue, entra, */cargarTxt = "", cargarImg = "";
	private JFileChooser fileChooser;
	private JButton btnImg;
	/*private String I_linea = "<br>", F_linea = "</br>";
	private String I_tamanio = "<FONT SIZE=" + 4 + ">", F_tamanio = "</FONT>";
	private String I_cursiva = "<I>", F_cursiva = "</I>";
	private String I_sub = "<SUB>", F_sub = "</SUB>";*/
	private String texto = "";

	int x[] = { 10, 150, 160, 170, 180, 680, 690, 690, 680, 180, 170, 160, 150, 10, 0, 0, 10 };
	int y[] = { 0, 0, 24, 24, 0, 0, 24, 388, 400, 400, 388, 388, 400, 400, 388, 24, 0 };
	private PanelForm PF = new PanelForm(710, 420, x, y, "images/fondoo.jpg");

	public GUI_Cliente() {
		Ini();
		Add();
		Comp();
		Actions();
		CrearFileChooser();
		IniciarCliente();
		RefrescarConexiones(null);
	}

	private void CargarListaUsuariosActivos() {
		// Ini modelo:
		modelo = new DefaultListModel<String>();

		// Cargamos fichero:
		LinkedList<String> new_activos = new LinkedList<String>();
		Fichero f = new Fichero();
		new_activos = f.cargar("activos.obj");

		// Buscamos usuarios activos del servidor y los introducimos en la
		// lista:
		for (int i = 0; i < new_activos.size(); i++) {
			modelo.addElement(new_activos.get(i));
			list.setModel(modelo);
		}

	}

	private Timer conect;
	private JButton Minimizar;
	private JButton Maximizar;
	private JButton Salir;
	private JToggleButton Desplegar;
	private JLabel MoverScreen;

	/*
	 * Método que recibe la dirección ip del servidor
	 */
	private void RefrescarConexiones(ActionEvent evt) {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CargarListaUsuariosActivos();
			}
		};

		conect = new Timer(1000, taskPerformer);
		conect.start();
	}

	private void CrearFileChooser() {
		// Creamos el FileChooser
		fileChooser = new JFileChooser();
		// Le añadimos los filtros
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpg", "jpeg", "jpe", "jfif");
		FileNameExtensionFilter filter2 = new FileNameExtensionFilter("PNG", "png");
	//	FileNameExtensionFilter filter3 = new FileNameExtensionFilter("TXT", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileFilter(filter2);
	//	fileChooser.setFileFilter(filter3);
	}

	private void IniciarCliente() {
		try {
			cliente = new Cliente(this);
			cliente.conexion();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Ini() {
		MoverScreen = new JLabel();
		contentPane = new JPanel();
		panUsus = new JPanel();
		list = new JList<String>();
		panChat = new JPanel();
		textEnviar = new JTextField();
		btnEnviar = new JButton("Enviar");
		lblConver = new JLabel();
		scrollPane = new JScrollPane();
		editorPane = new JEditorPane("text/html", "<html>");
		btnImg = new JButton("Img");
		lblUsuariosConectados = new JLabel("Usuarios conectados");
		Maximizar = new JButton("[]");
		Salir = new JButton("X");
		Salir.setForeground(Color.RED);
		Desplegar = new JToggleButton("<");
		Minimizar = new JButton("-");

	}

	private void Add() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(710, 420);
		setContentPane(contentPane);
		setTitle("GUI cliente");
		setResizable(false);
		setLocationRelativeTo(null);

		panUsus.setLayout(null);
		panUsus.add(lblUsuariosConectados);
		panUsus.add(list);
		panChat.setLayout(null);
		panChat.add(textEnviar);
		panChat.add(btnEnviar);
		panChat.add(lblConver);
		panChat.add(scrollPane);
		panChat.add(btnImg);

		setBackground(new Color(0, 0, 0, 0));
		PF.setLayout(null);
		PF.add(panUsus);
		PF.add(panChat);
		PF.add(Maximizar);
		PF.add(Salir);
		PF.add(Minimizar);
		PF.add(Desplegar);
		PF.add(MoverScreen);
		contentPane.add(PF);

	}

	private void Comp() {
		MoverScreen.setBounds(180, 1, 388, 23);
		Desplegar.setBounds(159, 24, 11, 364);
		lblConver.setForeground(Color.WHITE);
		panUsus.setBackground(Color.BLACK);
		panChat.setBackground(Color.BLACK);
		panUsus.setLocation(10, 24);
		panUsus.setSize(150, 364);
		panChat.setLocation(170, 24);
		panChat.setSize(520, 364);
		lblUsuariosConectados.setForeground(Color.WHITE);
		Maximizar.setBounds(604, 1, 39, 23);
		Salir.setBounds(641, 1, 39, 23);
		Minimizar.setBounds(567, 1, 39, 23);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		lblUsuariosConectados.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsuariosConectados.setBounds(0, 0, 149, 41);
		list.setBounds(10, 43, 130, 310);
		textEnviar.setBounds(10, 327, 401, 26);
		textEnviar.setColumns(10);
		btnEnviar.setBounds(421, 327, 89, 26);
		btnEnviar.addActionListener(this);
		lblConver.setBounds(10, 11, 401, 26);
		lblConver.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setBounds(10, 41, 500, 271);
		editorPane.setEditable(false);
		scrollPane.setViewportView(editorPane);
		btnImg.setBounds(421, 11, 89, 23);
	}

	private int posX = 0;
	private int posY = 0;

	private void Actions() {

		Desplegar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desplegar.isSelected() == true) {
					contentPane.removeAll();
					int x[] = { 170, 180, 680, 690, 690, 680, 180, 170, 170 };
					int y[] = { 24, 0, 0, 24, 388, 400, 400, 388, 24 };
					PanelForm PF = new PanelForm(710, 420, x, y, "images/fondoo.jpg");
					PF.setLayout(null);
					PF.add(panChat);
					PF.add(Maximizar);
					PF.add(Salir);
					PF.add(Minimizar);
					PF.add(Desplegar);
					PF.add(MoverScreen);
					contentPane.add(PF);
					repaint();

				} else {
					contentPane.removeAll();
					int x[] = { 10, 150, 160, 170, 180, 680, 690, 690, 680, 180, 170, 160, 150, 10, 0, 0, 10 };
					int y[] = { 0, 0, 24, 24, 0, 0, 24, 388, 400, 400, 388, 388, 400, 400, 388, 24, 0 };
					PanelForm PF = new PanelForm(710, 420, x, y, "images/fondoo.jpg");
					PF.setLayout(null);
					PF.add(panUsus);
					PF.add(panChat);
					PF.add(Maximizar);
					PF.add(Salir);
					PF.add(Minimizar);
					PF.add(Desplegar);
					PF.add(MoverScreen);
					contentPane.add(PF);
					repaint();
				}
			}
		});

		MoverScreen.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Rectangle rectangle = getBounds();
				setBounds(arg0.getXOnScreen() - posX, arg0.getYOnScreen() - posY, rectangle.width, rectangle.height);
			}
		});
		MoverScreen.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				posX = arg0.getX();
				posY = arg0.getY();
			}

			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					// Ajustamos size a la size de la pantalla:
					setSize(710, 420);
					setLocationRelativeTo(null);
				}
			}
		});

		Maximizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		Minimizar.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				setExtendedState(GUI_Cliente.CROSSHAIR_CURSOR);
			}
		});

		textEnviar.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.VK_ENTER == e.getKeyCode()) {
					String mensaje = textEnviar.getText();
					if (!mensaje.trim().equals("")) {
						mostrarMensaje(1, mensaje);
						mensaje = CesarRecursivo.recorrer(1, "", mensaje, 0);
						mensaje = getUsuario() + "_" + mensaje;
						cliente.enviarTexto(mensaje);
						// Limpiamos el cuadro de texto del mensaje
						textEnviar.setText("");
					}
				}
			}
		});

		btnImg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Valor que tomará el fileChooser
				int valor = fileChooser.showOpenDialog(null);
				String direccion = "";
				if (valor == JFileChooser.APPROVE_OPTION) {
					// Crear propiedades para ser utilizadas por fileChooser
					File archivoElegido = fileChooser.getSelectedFile();
					// Obteniendo la direccion del archivo
					direccion = archivoElegido.getPath();
				}
				mostrarImagen(1, direccion);
				String tipo = direccion.substring(direccion.length() - 3);
				System.out.println(direccion + " " + tipo);
				cliente.enviarImagen(direccion, tipo);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnEnviar)) {
			String mensaje = textEnviar.getText();
			if (!mensaje.trim().equals("")) {
				mostrarMensaje(1, mensaje);
				mensaje = CesarRecursivo.recorrer(1, "", mensaje, 0);
				mensaje = getUsuario() + "_" + mensaje;
				cliente.enviarTexto(mensaje);
				// Limpiamos el cuadro de texto del mensaje
				textEnviar.setText("");
			}
		}
	}

	/**
	 * Método que muestra el mensaje por pantalla
	 * 
	 * @param num
	 *            mensaje enviado = 1, mensaje recibido = 2
	 * @param mensaje
	 */
	public void mostrarMensaje(int num, String mensaje) {
		if (num == 1) {
			cargarTxt = "<FONT SIZE=" + 4 + "><DIV align=\"right\">" + getUsuario() + " => " + mensaje
					+ "</DIV></FONT></br>";
		} else {
			cargarTxt = "<FONT SIZE=" + 4 + "><DIV align=\"left\">" + mensaje + "</DIV></FONT></br>";
		}
		texto = texto + cargarTxt;
		editorPane.setText(texto + "<br>");
	}

	/**
	 * TODO método que resize las imagenes dependiendo si son cuadradas u
	 * horizontales Método que muestra la imagen por pantalla
	 * 
	 * @param num
	 *            imagen enviada = 1, imagen recibida = 2
	 * @param imagen
	 *            dirección local de la imagen
	 * @param usuario
	 *            que envía la imagen
	 */
	public void mostrarImagen(int num, String path) {

		if (num == 1) {
			cargarImg = "<DIV align=\"right\">" + getUsuario() + " => <img src='file:" + path
					+ "' width=60 height=60></DIV></img></br>";
		} else {
			cargarImg = "<DIV align=\"left\"><FONT SIZE=" + 4 + ">" + usuario + "<img src='file:" + path
					+ "' width=60 height=60></FONT></DIV></img></br>";
		}
		texto = texto + cargarImg;
		editorPane.setText(texto + "<br>");
	}

	public String getUsuario() {
		return usuario;
	}

	public void setNombreUser(String user) {
		lblConver.setText("Usuario " + user);
		this.usuario = user;
	}
}
