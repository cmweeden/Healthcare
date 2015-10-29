package code;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserDBScreen implements ActionListener {

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

	static int panely;

	String _action;
	String _id;
	String _firstname;
	String _lastname;
	String _title;
	String _username;
	String _type;
	String _where;

	ArrayList<String> _array;
	int _i;
	Boolean _next;
	Boolean _backB;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on type (subject)
	 * @param location
	 *            The user log-on location (community)
	 */
	public UserDBScreen(JFrame f, String login_type, String location) {
		_frame = f;
		_login_type = login_type;
		_loc = location;
		height = 40;
		width = 160;
		panely = 420;

		_next = false;
		_backB = false;

	}

	/**
	 * @param f
	 * @param login_type
	 * @param location
	 * @param action
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 */
	public UserDBScreen(JFrame f, String login_type, String location, String action,
			String id, String firstname, String lastname, String title,
			String username, String type) {
		_frame = f;
		_login_type = login_type;
		height = 40;
		width = 160;
		panely = 420;
		_action = action;
		_id = id;
		_firstname = firstname;
		_lastname = lastname;
		_title = title;
		_username = username;
		_type = type;
		_loc = location;
		_next = false;
		_backB = false;
	}

	/**
	 * @param addWhere
	 * @param _frame2
	 * @param login_type
	 * @param location
	 * @param action
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 */
	public UserDBScreen(String addWhere, JFrame _frame2, String login_type,
			String location, String action, String id, String firstname,
			String lastname, String title, String username, String type) {

		_where = addWhere;
		_frame = _frame2;
		_login_type = login_type;
		_action = action;
		_id = id;
		_firstname = firstname;
		_lastname = lastname;
		_title = title;
		_username = username;
		_type = type;
		height = 40;
		width = 160;
		panely = 420;
		_loc = location;
		_next = false;
		_backB = false;

	}

	/**
	 * @param where
	 * @param _frame2
	 * @param login_type
	 * @param location
	 * @param action
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 * @param array
	 * @param i
	 * @param n
	 * @param b
	 */
	public UserDBScreen(String where, JFrame _frame2, String login_type,
			String location, String action, String id, String firstname,
			String lastname, String title, String username, String type,
			ArrayList<String> array, int i, boolean n, boolean b) {
		_where = where;
		_frame = _frame2;
		_login_type = login_type;
		_action = action;
		_id = id;
		_firstname = firstname;
		_lastname = lastname;
		_title = title;
		_username = username;
		_type = type;
		height = 40;
		width = 160;
		panely = 420;
		_loc = location;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (_login_type.equalsIgnoreCase("Administrator")) {
			int action;
			if (arg0.getActionCommand().equals("View, Update, or Delete")) {
				action = 1;

			} else if (arg0.getActionCommand().equals("Add")) {
				action = 2;
			} else if (arg0.getActionCommand().equals("Back")) {
				action = 1;

			} else {
				action = 3;
			}
			createFrame(action);
		} else {
			System.out
					.println("Only administrators can access the user database");
		}

	}

	/**
	 * @param action
	 */
	public void createFrame(int action) {
		_frame.getContentPane().removeAll();
		_frame.setTitle("User Database");

		_panel = new ImagePanel();
		_frame.add(_panel);

		addComponents();

		switch (action) {
		case 1:
			modifyUser();
			break;
		case 2:
			addUser();
			break;
		case 3:
			viewUser();
			break;
		}

		_frame.setVisible(true);
	}

	/**
	 * 
	 */
	public void addComponents() {
		_panel.setLayout(null);

		int x = 10;
		int y = 10;

		JLabel id = new JLabel("ID");
		id.setBounds(x, y, width, height);
		_panel.add(id);

		y = y + 50;

		JLabel firstname = new JLabel("First Name");
		firstname.setBounds(x, y, width, height);
		_panel.add(firstname);

		y = y + 50;

		JLabel lastname = new JLabel("Last Name");
		lastname.setBounds(x, y, width, height);
		_panel.add(lastname);

		y = y + 50;

		JLabel title = new JLabel("Title");
		title.setBounds(x, y, width, height);
		_panel.add(title);

		y = y + 50;

		JLabel username = new JLabel("Username");
		username.setBounds(x, y, width, height);
		_panel.add(username);

		y = y + 50;

		JLabel iLabel = new JLabel("Position");
		iLabel.setBounds(x, y, width, height);
		_panel.add(iLabel);

	}

	/**
	 * 
	 */
	public void addUser() {
		_panel.setLayout(null);

		int x2 = 180;
		int y = 10;

		JLabel id = new JLabel("Will be assigned");
		id.setBounds(x2, y, width, height);
		_panel.add(id);

		y = y + 50;

		JTextField nameText = new JTextField(20);
		nameText.setBounds(x2, y, width, height);
		nameText.setEditable(true);
		_panel.add(nameText);

		y = y + 50;

		JTextField lnameText = new JTextField(20);
		lnameText.setBounds(x2, y, width, height);
		_panel.add(lnameText);

		y = y + 50;

		JComboBox titledb = new JComboBox();
		ArrayList<String> titles = Helper.getColumns("Policy_DB", "USERS",
				"TITLE");
		for (int i = 0; i < titles.size(); i++) {
			titledb.addItem(titles.get(i));
		}
		titledb.setEditable(true);
		titledb.setBounds(x2, y, width, height);
		_panel.add(titledb);

		y = y + 50;

		JTextField userText = new JTextField(20);
		userText.setBounds(x2, y, width, height);
		_panel.add(userText);

		y = y + 50;

		JComboBox idb = new JComboBox();
		ArrayList<String> type = Helper
				.getColumns("Policy_DB", "TYPES", "TYPE");
		for (int i = 0; i < type.size(); i++) {
			idb.addItem(type.get(i));
		}

		idb.setBounds(x2, y, width, height);
		_panel.add(idb);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 2, height);
		_panel.add(error);

		JButton submit = new JButton("Submit");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new UserToDBListener(_frame, _login_type, _loc,
				"insert", nameText, lnameText, titledb, userText, idb, error));
		_frame.getRootPane().setDefaultButton(submit);
		_panel.add(submit);

	}

	/**
	 * 
	 */
	public void modifyUser() {
		_panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JComboBox idb = new JComboBox();
		// give empty slot as well so can write in
		ArrayList<String> pids = Helper.getColumns("Policy_DB", "USERS", "ID");
		for (int i = 0; i < pids.size(); i++) {
			idb.addItem(pids.get(i));
		}
		idb.setEditable(true);
		idb.setBounds(x2, y, width, height);
		_panel.add(idb);

		y = y + 50;

		JTextField nameText = new JTextField(20);
		nameText.setBounds(x2, y, width, height);
		nameText.setEditable(true);
		_panel.add(nameText);

		y = y + 50;

		JTextField lnameText = new JTextField(20);
		lnameText.setBounds(x2, y, width, height);
		_panel.add(lnameText);

		y = y + 50;

		JComboBox titledb = new JComboBox();
		ArrayList<String> titles = Helper.getColumns("Policy_DB", "USERS",
				"TITLE");
		for (int i = 0; i < titles.size(); i++) {
			titledb.addItem(titles.get(i));
		}
		titledb.setEditable(true);
		titledb.setBounds(x2, y, width, height);
		_panel.add(titledb);

		y = y + 50;

		JTextField userText = new JTextField(20);
		userText.setBounds(x2, y, width, height);
		_panel.add(userText);

		y = y + 50;

		JComboBox tdb = new JComboBox();
		ArrayList<String> type = Helper
				.getColumns("Policy_DB", "TYPES", "TYPE");
		for (int i = 0; i < type.size(); i++) {
			tdb.addItem(type.get(i));
		}
		tdb.setBounds(x2, y, width, height);
		_panel.add(tdb);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 2, height);
		_panel.add(error);

		JButton submit = new JButton("View");
		submit.setBounds(x, y, width, height);
		submit.addActionListener(new UserToDBListener(_frame, _login_type, _loc,
				"view", idb, nameText, lnameText, titledb, userText, tdb, error));

		_panel.add(submit);

		JButton submit3 = new JButton("Delete");
		submit3.setBounds(x + 320, y, width, height);
		submit3.addActionListener(new UserToDBListener(_frame, _login_type, _loc,
				"delete", idb, nameText, lnameText, titledb, userText, tdb,
				error));
		_panel.add(submit3);

	}

	/**
	 * 
	 */
	public void viewUser() {
		_panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JComboBox idb = new JComboBox();
		// give empty slot as well so can write in
		ArrayList<String> pids = Helper.getColumns("Policy_DB", "USERS", "ID");
		for (int i = 0; i < pids.size(); i++) {
			idb.addItem(pids.get(i));
		}
		idb.setEditable(true);
		idb.setSelectedItem(_id);
		idb.setBounds(x2, y, width, height);
		_panel.add(idb);

		y = y + 50;

		JTextField nameText = new JTextField(_firstname);
		nameText.setBounds(x2, y, width, height);
		nameText.setEditable(true);
		_panel.add(nameText);

		y = y + 50;

		JTextField lnameText = new JTextField(_lastname);
		lnameText.setBounds(x2, y, width, height);
		_panel.add(lnameText);

		y = y + 50;

		JComboBox titledb = new JComboBox();
		ArrayList<String> titles = Helper.getColumns("Policy_DB", "USERS",
				"TITLE");
		for (int i = 0; i < titles.size(); i++) {
			titledb.addItem(titles.get(i));
		}
		titledb.setEditable(true);
		titledb.setSelectedItem(_title);
		titledb.setBounds(x2, y, width, height);
		_panel.add(titledb);

		y = y + 50;

		JTextField userText = new JTextField(_username);
		userText.setBounds(x2, y, width, height);
		_panel.add(userText);

		y = y + 50;

		JComboBox tdb = new JComboBox();
		ArrayList<String> type = Helper
				.getColumns("Policy_DB", "TYPES", "TYPE");
		for (int i = 0; i < type.size(); i++) {
			tdb.addItem(type.get(i));
		}
		tdb.setSelectedItem(_type);
		tdb.setBounds(x2, y, width, height);
		_panel.add(tdb);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 2, height);
		_panel.add(error);

		JButton submit = new JButton("Back");
		submit.setBounds(x, y, width, height);
		submit.addActionListener(new UserDBScreen(_frame, _login_type, _loc));

		_panel.add(submit);

		JButton submit2 = new JButton("Update");
		submit2.setBounds(x + 160, y, width, height);
		submit2.addActionListener(new UserToDBListener(_where, _frame, _login_type,
				_loc, "update", idb, nameText, lnameText, titledb, userText,
				tdb, error));
		_panel.add(submit2);

		JButton submit3 = new JButton("Delete");
		submit3.setBounds(x + 320, y, width, height);
		submit3.addActionListener(new UserToDBListener(_where, _frame, _login_type,
				_loc, "delete", idb, nameText, lnameText, titledb, userText,
				tdb, error));
		_panel.add(submit3);

		y = y + 50;
		if (_next) {
			JButton next = new JButton("Next User");
			next.setBounds(x2, y, width, height);
			next.addActionListener(new UserToDBListener(_where, _frame, _login_type,
					_loc, "next", _array, _i, error));
			_panel.add(next);
		}

		if (_backB) {
			JButton back = new JButton("Previous User");
			back.setBounds(x, y, width, height);
			int b = _i - 12;
			back.addActionListener(new UserToDBListener(_where, _frame, _login_type,
					_loc, "next", _array, b, error));
			_panel.add(back);
		}
	}
}
