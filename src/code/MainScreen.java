package code;

import java.awt.Font;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainScreen implements ActionListener {

	static JMenuBar _menuBar;

	// Common frame and panel for the program
	static JFrame _frame;
	ImagePanel _panel;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _location;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param type
	 *            The user log-on type (subject)
	 * @param location
	 *            The user log-on location (community)
	 */
	public MainScreen(JFrame f, String type, String location) {
		// Welcome screen
		// And screen to set up menu bar for entire program
		_frame = f;

		height = 40;
		width = 160;

		_login_type = type;
		_location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		showFunctions();

	}

	/**
	 * 
	 */
	public void showFunctions() {
		_frame.getContentPane().removeAll();
		_frame.setTitle("Main Functions");

		ImagePanel pane = new ImagePanel();

		addComponents(pane);

		_frame.add(pane);

		_menuBar = addToPanel();

		_frame.setJMenuBar(_menuBar);
		_frame.setVisible(true);
	}

	/**
	 * @param panel
	 */
	public void addComponents(ImagePanel panel) {
		
	
		
		
		panel.setLayout(null);
		
		Rectangle r = _frame.getBounds();
		int h = r.height;
		int w = r.width;
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("resources/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(w/2-75,h-76-50, 150, 76);			
			panel.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel label = new JLabel("WELCOME");
		label.setBounds(x2, y, width, height);
		panel.add(label);

		y = y + 100;

		JLabel label2 = new JLabel("Your ActivityTheory Client");
		label2.setBounds(x + 55, y, width * 4, height);
		Font labelFont = label2.getFont();
		label2.setFont(new Font(labelFont.getName(), Font.ITALIC, 30));
		panel.add(label2);
		


	}

	/**
	 * @return
	 */
	public static JMenuBar addToPanel() {

		_menuBar = new JMenuBar();
		
		JMenu menu = new JMenu("Menu");
		_menuBar.add(menu);

	    JMenuItem home, logout;
		home = new JMenuItem("Home");
		home.addActionListener(new MainScreen(_frame, _login_type, _location));
		menu.add(home);

		logout = new JMenuItem("Logout");
		logout.addActionListener(new LoginScreen2(_frame));
		menu.add(logout);
		
		JMenu menuPatient = new JMenu("Patient");
		_menuBar.add(menuPatient);
		
		JMenuItem addPatient, viewPatient, addPolicy, viewPolicy, addUser, viewUser, addExternal, viewExternal, addAbout, mappings, hierarchies;
		if (_login_type.equalsIgnoreCase("administrator")) {
			addPatient = new JMenuItem("Add");
			addPatient.addActionListener(new PatientDBScreen(_frame, _login_type, _location));
			menuPatient.add(addPatient);

			viewPatient = new JMenuItem("View, Update, or Delete");
		} else if (_login_type.equalsIgnoreCase("physician")) {
			viewPatient = new JMenuItem("View or Update");
			
		} else {
			viewPatient = new JMenuItem("View");
		}

		viewPatient.addActionListener(new PatientDBScreen(_frame, _login_type, _location));
		menuPatient.add(viewPatient);

		if (_login_type.equalsIgnoreCase("administrator")) {

			JMenu menuUser = new JMenu("User");
			_menuBar.add(menuUser);

			addUser = new JMenuItem("Add");
			addUser.addActionListener(new UserDBScreen(_frame, _login_type, _location));
			menuUser.add(addUser);

			viewUser = new JMenuItem("View, Update, or Delete");
			viewUser.addActionListener(new UserDBScreen(_frame, _login_type, _location));
			menuUser.add(viewUser);

		}

		JMenu menuPolicy = new JMenu("Policy");
		_menuBar.add(menuPolicy);

		if (_login_type.contains("Admin")) {
			addPolicy = new JMenuItem("Add");
			addPolicy.addActionListener(new PolicyDBScreen(_frame, _login_type, _location));
			menuPolicy.add(addPolicy);
			viewPolicy = new JMenuItem("View, Update, or Delete");
			mappings = new JMenuItem("Mappings");
			hierarchies = new JMenuItem("Hierarchy Assignments");
			mappings.addActionListener(new MappingsScreen(_frame, _login_type, _location));
			hierarchies.addActionListener(new HierarchyScreen(_frame, _login_type, _location));
			menuPolicy.add(mappings);
			menuPolicy.add(hierarchies);
		} else {
			viewPolicy = new JMenuItem("View");
			mappings = new JMenuItem("Mappings");
			hierarchies = new JMenuItem("Hierarchy Assignments");
		}

		viewPolicy.addActionListener(new PolicyDBScreen(_frame, _login_type, _location));
		menuPolicy.add(viewPolicy);

		JMenu menuExternal = new JMenu("External");
		_menuBar.add(menuExternal);

		addExternal = new JMenuItem("Export Data");
		addExternal.addActionListener(new DataExportScreen(_frame, _login_type, _location));
		menuExternal.add(addExternal);

		viewExternal = new JMenuItem("View Imported Data");
		viewExternal.addActionListener(new DataImportScreen(_frame, _login_type, _location));
		menuExternal.add(viewExternal);

		JMenu menuAbout = new JMenu("About");
		_menuBar.add(menuAbout);

		addAbout = new JMenuItem("About This Program");
		addAbout.addActionListener(new AboutScreen(_frame, _login_type, _location));
		menuAbout.add(addAbout);

		return _menuBar;
	}
}
