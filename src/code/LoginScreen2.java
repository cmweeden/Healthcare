package code;

//Reuse frame created in Login Screen
// This second LoginScreen allows creation and retention of menu bars


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginScreen2 implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _loc;

	// The menu bar to be used in the program.
	static JMenuBar _menuBar;

	/**
	 * @param f
	 *            The program frame which is shared with the entire program.
	 */
	public LoginScreen2(JFrame f) {
		// Reuse frame created in Login Screen
		// This second LoginScreen allows creation and retention of menu bars
		_frame = f;

		height = 40;
		width = 160;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		setHome();
	}

	/**
	 * Sets which screen is considered the home screen. Re-creates the menu bar
	 * as needed.
	 */
	public void setHome() {
		_frame.getContentPane().removeAll();

		_menuBar = buttonPanel();
		_menuBar.remove(0);
		_menuBar.revalidate();

		_frame.setJMenuBar(_menuBar);
		_frame.setTitle("Login");

		ImagePanel pane = new ImagePanel();
		placeComponents(pane);

		_frame.add(pane);
		_frame.setVisible(true);

	}

	/**
	 * Creates the menu bar for use in the program
	 * 
	 * @return THe menu bar which is created
	 */
	public static JMenuBar buttonPanel() {
		JMenuBar menuBar = new JMenuBar();
		JMenuItem home, logout;

		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		home = new JMenuItem("Home");
		home.addActionListener(new LoginScreen2(_frame));
		menu.add(home);

		logout = new JMenuItem("Logout");
		logout.addActionListener(new LoginScreen2(_frame));
		menu.add(logout);

		return menuBar;
	}

	/**
	 * Arranges the elements on the login screen
	 * 
	 * @param panel
	 *            The panel on which to place the elements
	 */
	public static void placeComponents(ImagePanel panel) {
		panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel loc = new JLabel("Login Location");
		loc.setBounds(x, y, width, height);
		panel.add(loc);

		JComboBox ldb = new JComboBox();
		ArrayList<String> type = Helper.getColumns("Policy_DB", "LOCATIONS",
				"LOCATION");
		for (int i = 0; i < type.size(); i++) {
			ldb.addItem(type.get(i));
		}
		ldb.setBounds(x2, y, width + 50, height);
		panel.add(ldb);

		y = y + 50;

		JLabel uType = new JLabel("User Type");
		uType.setBounds(x, y, width, height);
		panel.add(uType);

		JComboBox idb = new JComboBox();
		ArrayList<String> type2 = Helper.getColumns("Policy_DB", "TYPES",
				"TYPE");
		for (int i = 0; i < type2.size(); i++) {
			idb.addItem(type2.get(i));
		}
		idb.setBounds(x2, y, width + 50, height);
		panel.add(idb);

		y = y + 50;

		JLabel username = new JLabel("User Name");
		username.setBounds(x, y, width, height);
		panel.add(username);

		JTextField nameText = new JTextField(20);
		nameText.setBounds(x2, y, width, height);
		nameText.setEditable(true);
		panel.add(nameText);

		y = y + 50;

		JLabel password = new JLabel("Pass Phrase");
		password.setBounds(x, y, width, height);
		panel.add(password);

		JPasswordField pText = new JPasswordField(20);
		pText.setBounds(x2, y, width, height);
		panel.add(pText);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width, height);
		panel.add(error);

		JButton submit = new JButton("Login");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new LoginListener(ldb, idb, nameText,
				(JTextField) pText, _frame, error));
		_frame.getRootPane().setDefaultButton(submit);
		panel.add(submit);

	}

}
