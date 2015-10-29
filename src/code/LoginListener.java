package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoginListener implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _loc;

	JComboBox _ldb;
	JComboBox _idb;
	JTextField _name;
	JTextField _pass;

	JLabel _error;

	/**
	 * @param ldb
	 * @param idb
	 * @param nameText
	 * @param pText
	 * @param frame
	 * @param error
	 */
	public LoginListener(JComboBox ldb, JComboBox idb, JTextField nameText,
			JTextField pText, JFrame frame, JLabel error) {

		_frame = frame;

		_ldb = ldb;
		_idb = idb;
		_name = nameText;
		_pass = pText;

		_error = error;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		String location = (String) _ldb.getSelectedItem();
		_login_type = (String) _idb.getSelectedItem();
		String user = _name.getText();
		String pass = _pass.getText();

		if (_login_type.equalsIgnoreCase("Administrator")) {
			if ((user.equalsIgnoreCase("admin")) && (pass.equals("admin"))) {
				MainScreen page = new MainScreen(_frame, _login_type, location);
				page.actionPerformed(null);
			} else {
				_error.setText("Invalid Login");
				_name.setText("");
				_pass.setText("");
			}
		} else {

			if ((pass.equals("password")) && checkUser(user)) {
				// checkUser checks to see if user is in DB and also
				// that the type they selected is the same as the type in the DB
				String type = Helper.selectItem(user, "USERNAME", "TYPE", "USERS");
				MainScreen page = new MainScreen(_frame, type, location);
				page.actionPerformed(null);
			} else {
				_error.setText("Invalid Login");
				_name.setText("");
				_pass.setText("");
			}

		}
	}

	/**
	 * @param username
	 * @return
	 */
	public boolean checkUser(String username) {

		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");

			c = DriverManager.getConnection("jdbc:sqlite:" + "Policy_DB"
					+ ".db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement s = null;
		ResultSet result = null;
		try {
			c.setAutoCommit(false);
			s = c.createStatement();
			result = s.executeQuery("SELECT TYPE FROM USERS WHERE USERNAME='" + username + "';");
			if (!result.next()) {
				System.out.println("no data");
				s.close();
				c.close();
				result.close();
				return false;
			} else {
				String type2 = result.getString("TYPE");
				if (_login_type.equals(type2)) {
					s.close();
					c.close();
					result.close();
					return true;
				} else {
					s.close();
					c.close();
					result.close();
					return false;
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
