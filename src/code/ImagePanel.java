package code;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * Auto generated UID
	 */
	private static final long serialVersionUID = 1L;
	Image imageOrg = null;
	Image image = null;
	{
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				int w = ImagePanel.this.getWidth();
				int h = ImagePanel.this.getHeight();
				//image = w > 0 && h > 0 ? imageOrg.getScaledInstance(w, h,
				///		java.awt.Image.SCALE_SMOOTH) : imageOrg;
				ImagePanel.this.repaint();
			}
		});
	}

	/**
	 * 
	 */
	public ImagePanel() {
		File here = new File(".");
		String path = "";
		try {
			path += here.getCanonicalPath();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Acknowledgment in "About" screen

		// Image courtesy of dream designs at FreeDigitalPhotos.net
		// Caedaecus on blue "/resources/ID-100274167.jpg";

		String url = path + "/resources/ID-100274167 Flipped.jpg";
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Image disabled until new image found
		//imageOrg = img;
		//image = img;
		setOpaque(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
}
