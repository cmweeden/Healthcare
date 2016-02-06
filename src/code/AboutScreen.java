package code;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AboutScreen implements ActionListener {

	// Common frame and panel for the program
	static JFrame _frame;
	ImagePanel _panel;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _loc;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param type
	 *            The user log-on type (subject)
	 * @param location
	 *            The user log-on location (community)
	 */
	public AboutScreen(JFrame f, String type, String location) {
		_frame = f;

		height = 40;
		width = 160;

		_login_type = type;
		_loc = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		createFrame();
	}

	/**
	 * Sets the title of the screen and calls the content creator addComponents
	 * Initializes the screen
	 */
	public void createFrame() {

		_frame.getContentPane().removeAll();
		_frame.setTitle("About This Program");

		_panel = new ImagePanel();

		_frame.add(_panel);

		addComponents();

		_frame.setVisible(true);
	}

	/**
	 * Creates the contents of the screen
	 */
	public void addComponents() {
		_panel.setLayout(null);

		int x = 10;
		int y = 100;

		JTextArea textArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea);

		// Acknowledgment in "About" screen
		// Image courtesy of anankkml at FreeDigitalPhotos.net
		// Stethescope & laptop pic "/resources/ID-100267860.jpg";
		// Not currently used

		// Image courtesy of dream designs at FreeDigitalPhotos.net
		// Caedaecus on blue "/resources/ID-100274167.jpg";
		// Modified to "/resources/ID-100274167 Flipped.jpg";

		// Image courtesy of cooldesign at FreeDigitalPhotos.net
		// Medical laptop "/resources/ID-100281851.jpg";
		// Not currently used

		textArea.append("YAC developed by Stacey Askey, Jennifer Cordaro, and Fandi Yi \n");
		textArea.append("\n");
		textArea.append("Development for research by Rohit Valecha, under Shambhu Upadhyaya and H.R. Rao \n");
		textArea.append("\n");
		textArea.append("Background image courtesy of dream designs at FreeDigitalPhotos.net");

		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);

		scrollPane.setPreferredSize(new Dimension(width * 3, height * 5));
		scrollPane.setOpaque(false);
		scrollPane.setBounds(x, y, width * 3, height * 5);

		_panel.add(scrollPane);
		
		
		Rectangle r = _frame.getBounds();
		int h = r.height;
		int w = r.width;
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("resources/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(w/2-75,h-76-50, 150, 76);			
			_panel.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
