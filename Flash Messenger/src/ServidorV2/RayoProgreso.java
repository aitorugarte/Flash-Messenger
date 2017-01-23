package ServidorV2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import ClienteV2.LogIn.PanelForm;

public class RayoProgreso extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Timer Simulacion;
	private int X = 0; // Dimensión X del frame
	private int Xmax = 1000; // Final del despliegue!
	private int velocidad = 5;
	private JPanel contentPane;
	private BarraProgreso progressBar = new BarraProgreso(Xmax);


	// Variables necesarias para crear la forma del panel:
	int x[] = { 275+200, 400+200, 300+200, 350+200, 200+200, 275+200, 25+200, 150+200, 75+200, 225+200, 150+200, 275+200 };
	int y[] = { 0+200, 0+200, 250/2+200, 250/2+200, 550/2+200, 550/2+200, 800/2+200, 600/2+200, 600/2+200, 300/2+200, 300/2+200, 0+200 };
	private PanelForm PF = new PanelForm(800, 800, x, y, "images/fondoo.jpg");

	/**
	 * Create the frame.
	 */
	public RayoProgreso() {
		Inicializar();
		Añadir();
		Componentes();
		StartSimulacion(null);
	}

	private void Inicializar() {
		contentPane = new JPanel();
	}

	private void Añadir() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(0, 0); // Size por defecto = 0;
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		// TODO: Importante undecorated!
		setUndecorated(true);

		// TODO: Importante el último 0, indica la opacidad del fondo de la
		// ventana!
		setBackground(new Color(0, 0, 0, 0));
		contentPane.setLayout(null);

		// TODO: Importante añadir el PanelForm al contenedor!
		PF.setLayout(null);
		contentPane.add(PF);

	}

	private void Componentes() {
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	}

	private void StartSimulacion(ActionEvent evt) {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				// Repaint de los componentes:
				setSize(X, 800);
				setLocationRelativeTo(null);

				// Velocidad del desplazamiento:
				X = X + velocidad;

				// Añadimos valor a la progressBar:
				progressBar.progressBar.setValue(X);

				if (X >= Xmax) {
					X = 0;
				}
			}
		};

		Simulacion = new Timer(20, taskPerformer);
		Simulacion.start();
	}

	public void StopSimulacion(ActionEvent evt) {
		X = Xmax;
		Simulacion.stop();
		progressBar.dispose();
	}

	// TODO: Clase interna para la progressbar:
	class BarraProgreso extends JFrame {

		private static final long serialVersionUID = 1L;

		// public para poder manejarlo desde fuera:
		public JProgressBar progressBar;

		public BarraProgreso(int valorMax) {
			Inicializar();
			Añadir();
			Componentes(valorMax);
		}

		private void Inicializar() {
			progressBar = new JProgressBar();
		}

		private void Añadir() {
			setUndecorated(true);
			setLayout(null);
			//TODO: falta situarlo donde tú quieras!
			setBounds(700, 1000, 400, 20);
			add(progressBar);
			setVisible(true);
		}

		private void Componentes(int valorMax) {
			progressBar.setBackground(Color.BLACK);
			progressBar.setForeground(Color.ORANGE);
			progressBar.setStringPainted(true);
			progressBar.setBounds(0, 0, 400, 20);
			progressBar.setMaximum(valorMax);
		}

	}
}
