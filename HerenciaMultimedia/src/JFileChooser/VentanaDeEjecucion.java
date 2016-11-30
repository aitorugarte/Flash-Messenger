package JFileChooser;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaDeEjecucion {

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaDeEjecucion window = new VentanaDeEjecucion();
					window.frame.setVisible(true);
					String imagePath = "src.JFileChooser.Img/FlashLogo.jpg";
					InputStream imgStream = VentanaDeEjecucion.class.getResourceAsStream(imagePath );
					BufferedImage myImg = ImageIO.read(imgStream);
					VentanaDeEjecucion.frame.setIconImage(myImg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaDeEjecucion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnImagen = new JButton("Imagen");
		btnImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Imagen ventana = new Imagen();
				   JFrame frame = new JFrame("Imagen");
				   ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				   ventana.pack();
				   ventana.setBounds(400, 200, 400, 400);
			       ventana.setVisible(true);
				
			}
		});
		btnImagen.setBounds(53, 189, 89, 23);
		frame.getContentPane().add(btnImagen);
		
		JButton btnTexto = new JButton("Texto");
		btnTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("TXT");
		         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		         frame.getContentPane().add(new TXT());
		         frame.pack();
		         frame.setVisible(true);
			}
		});
		btnTexto.setBounds(292, 189, 89, 23);
		frame.getContentPane().add(btnTexto);
		
		JLabel lblNewLabe = new JLabel("\u00BFQu\u00E9 tipo de archivo deseas leer?");
		lblNewLabe.setBackground(new Color(160, 160, 160));
		lblNewLabe.setForeground(Color.BLACK);
		lblNewLabe.setBounds(126, 79, 222, 45);
		frame.getContentPane().add(lblNewLabe);
	}
}
