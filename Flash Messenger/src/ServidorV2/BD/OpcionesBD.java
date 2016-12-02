package ServidorV2.BD;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpcionesBD extends JFrame {

	private JPanel contentPane;
	private JButton btnEjecutar;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpcionesBD frame = new OpcionesBD();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OpcionesBD() {
	    setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 245, 338, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnEjecutar = new JButton("Ejecutar");
		btnEjecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String orden = textField.getText();
				mostrarOrden(orden);
				textField.setText("");
			}
		});
		btnEjecutar.setBounds(358, 244, 91, 23);
		contentPane.add(btnEjecutar);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(0, 0, 461, 236);
		contentPane.add(textArea);
		
		
	}
	public void mostrarOrden(String orden) {
		this.textArea.append(orden + "\n");
	}
}
