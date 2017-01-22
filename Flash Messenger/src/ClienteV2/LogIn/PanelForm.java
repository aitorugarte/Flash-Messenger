package ClienteV2.LogIn;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * Clase cedida por @author David García
 *
 */
public class PanelForm extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private final Image image;
	private BufferedImage bufferedImage;
	int xPoints[];
	int yPoints[];

	/**
	 * Constructor de clase
	 */
	public PanelForm(int ancho, int alto, int[] CoordenadasX, int[] CoordenadasY, String pathImagen) {
		super();

		image = new ImageIcon(pathImagen).getImage();

		bufferedImage = imageToBI(image);
		// Tamaño del panel
		Dimension dimension = new Dimension(ancho, alto);
		PanelForm.this.setSize(dimension);
		PanelForm.this.setPreferredSize(dimension);

		// Coordenadas para la firgura:
		xPoints = CoordenadasX;
		yPoints = CoordenadasY;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GeneralPath polygonPrincipal = new GeneralPath(GeneralPath.WIND_EVEN_ODD, xPoints.length);
		polygonPrincipal.moveTo(xPoints[0], yPoints[0]);
		for (int i = 0; i < xPoints.length; i++) {
			polygonPrincipal.lineTo(xPoints[i], yPoints[i]);
		}
		polygonPrincipal.closePath();

		// transparencia del jpanel
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));

		// se cargan las texturas
		TexturePaint paint = new TexturePaint(bufferedImage,
				new Rectangle2D.Double(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()));

		// pinta cuerpo y borde del panel
		g2.setPaint(paint);// Para pintar el interior del polígono
		g2.fill(polygonPrincipal);// Para rellenar el polígono
		g2.setColor(Color.ORANGE); // Dar color al borde del polígono
		g2.setStroke(new BasicStroke(1));// Grosor del lineado
		g2.draw(polygonPrincipal);// Para mostrar borde del polígono

	}

	/**
	 * Método que dado un image retorna un BufferedImage:
	 * 
	 * @param img
	 *            Image
	 * @return BufferedImage
	 */
	private BufferedImage imageToBI(Image img) {
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return bi;
	}

}