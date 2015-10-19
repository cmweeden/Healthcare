package code;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	static String _fn;

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

		_fn = type;
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
		JMenuItem home, logout;

		JMenu menu = new JMenu("Menu");
		_menuBar.add(menu);

		home = new JMenuItem("Home");
		home.addActionListener(new MainScreen(_frame, _fn, _location));
		menu.add(home);

		logout = new JMenuItem("Logout");
		logout.addActionListener(new LoginScreen2(_frame));
		menu.add(logout);

		JMenuItem addPa, viewPa, addPo, viewPo, addU, viewU, addEx, viewEx, addA;

		JMenu menuPa = new JMenu("Patient");
		_menuBar.add(menuPa);

		if (_fn.equalsIgnoreCase("administrator")) {
			addPa = new JMenuItem("Add");
			addPa.addActionListener(new PatientDBScreen(_frame, _fn, _location));
			menuPa.add(addPa);

			viewPa = new JMenuItem("View, Update, or Delete");
		} else if (_fn.equalsIgnoreCase("physician")) {
			viewPa = new JMenuItem("View or Update");
		} else {
			viewPa = new JMenuItem("View");
		}

		viewPa.addActionListener(new PatientDBScreen(_frame, _fn, _location));
		menuPa.add(viewPa);

		if (_fn.equalsIgnoreCase("administrator")) {

			JMenu menuU = new JMenu("User");
			_menuBar.add(menuU);

			addU = new JMenuItem("Add");
			addU.addActionListener(new UserDBScreen(_frame, _fn, _location));
			menuU.add(addU);

			viewU = new JMenuItem("View, Update, or Delete");
			viewU.addActionListener(new UserDBScreen(_frame, _fn, _location));
			menuU.add(viewU);

		}

		JMenu menuPo = new JMenu("Policy");
		_menuBar.add(menuPo);

		if (_fn.equalsIgnoreCase("administrator")) {
			addPo = new JMenuItem("Add");
			addPo.addActionListener(new PolicyDBScreen(_frame, _fn, _location));
			menuPo.add(addPo);
			viewPo = new JMenuItem("View, Update, or Delete");
		} else {
			viewPo = new JMenuItem("View");
		}

		viewPo.addActionListener(new PolicyDBScreen(_frame, _fn, _location));
		menuPo.add(viewPo);

		JMenu menuEx = new JMenu("External");
		_menuBar.add(menuEx);

		addEx = new JMenuItem("Export Data");
		addEx.addActionListener(new DataExportScreen(_frame, _fn, _location));
		menuEx.add(addEx);

		viewEx = new JMenuItem("View Imported Data");
		viewEx.addActionListener(new DataImportScreen(_frame, _fn, _location));
		menuEx.add(viewEx);

		JMenu menuA = new JMenu("About");
		_menuBar.add(menuA);

		addA = new JMenuItem("About This Program");
		addA.addActionListener(new AboutScreen(_frame, _fn, _location));
		menuA.add(addA);

		return _menuBar;
	}
}
