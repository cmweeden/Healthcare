package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class UserToDBListener implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _loc;

	JComboBox _id;
	JTextField _first;
	JTextField _last;
	JComboBox _title;
	JTextField _username;
	JComboBox _type;
	String _action;
	String _where;
	boolean which;

	ArrayList<String> _array;
	int _i;

	JLabel _error;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param a
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 * @param error
	 */
	public UserToDBListener(JFrame f, String login_type, String loc, String a,
			JTextField firstname, JTextField lastname, JComboBox title,
			JTextField username, JComboBox type, JLabel error) {
		// Used for user insert
		_frame = f;
		_login_type = login_type;
		_action = a;
		_first = firstname;
		_last = lastname;
		_title = title;
		_username = username;
		_type = type;
		which = false;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param where
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param a
	 * @param array
	 * @param i
	 * @param error
	 */
	public UserToDBListener(String where, JFrame f, String login_type, String loc,
			String a, ArrayList<String> array, int i, JLabel error) {
		// Used for next and back when multiple patients
		_where = where;
		_frame = f;
		_login_type = login_type;
		_action = a;
		which = false;
		_array = array;
		_i = i;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param a
	 * @param i
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 * @param error
	 */
	public UserToDBListener(JFrame f, String login_type, String loc, String a,
			JComboBox i, JTextField firstname, JTextField lastname,
			JComboBox title, JTextField username, JComboBox type, JLabel error) {
		// Used for view and delete from initial view selection page
		_frame = f;
		_login_type = login_type;
		_action = a;
		_id = i;
		_first = firstname;
		_last = lastname;
		_title = title;
		_username = username;
		_type = type;
		which = true;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param where
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param a
	 * @param idb
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 * @param error
	 */
	public UserToDBListener(String where, JFrame f, String login_type, String loc,
			String a, JComboBox idb, JTextField firstname, JTextField lastname,
			JComboBox title, JTextField username, JComboBox type, JLabel error) {
		// Used for update and delete when data has been selected previously
		// through view

		_where = where;
		_frame = f;
		_login_type = login_type;
		_action = a;
		_first = firstname;
		_last = lastname;
		_title = title;
		_username = username;
		_type = type;
		which = true;
		_id = idb;
		_loc = loc;

		_error = error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		String id;
		if (which) {
			id = (String) _id.getSelectedItem();
		} else {
			id = "0";
		}
		String firstname;
		String lastname;
		String title;
		String username;
		String type;

		if (!(_action.equalsIgnoreCase("next"))) {
			firstname = _first.getText();
			lastname = _last.getText();
			title = (String) _title.getSelectedItem();
			username = _username.getText();
			type = (String) _type.getSelectedItem();

			if (_action.equalsIgnoreCase("insert")) {
				UserDatabase db = new UserDatabase();
				int uid = db.insert(firstname, lastname, title, username, type);

				if (uid > 0) {
					_error.setText("User added with UID " + uid);
					_first.setText("");
					_last.setText("");
					_title.setSelectedIndex(0);
					_username.setText("");
					_type.setSelectedIndex(0);
				} else {
					_error.setText("Unable to add user");
				}

				// UserDBScreen screen = new UserDBScreen(_frame, _login_type, _loc);
				// screen.createFrame(2);

			} else if (_action.equalsIgnoreCase("update")) {
				UserDatabase db = new UserDatabase();
				boolean success = db.update(addCols(), _where);

				if (success) {
					_error.setText("User data updated");
				} else {
					_error.setText("Unable to update user data");
				}
				// UserDBScreen screen = new UserDBScreen(_frame, _login_type, _loc);
				// screen.createFrame(1);

			} else if (_action.equalsIgnoreCase("delete")) {

				if (_id != null) {
					_where = addWhere((String) _id.getSelectedItem(),
							_first.getText(), _last.getText(),
							(String) _title.getSelectedItem(),
							_username.getText(),
							(String) _type.getSelectedItem());
				}
				UserDatabase db = new UserDatabase();
				boolean success = db.delete(_where);

				if (success) {
					UserDBScreen screen = new UserDBScreen(_frame, _login_type, _loc);
					screen.createFrame(1);
				} else {
					_error.setText("Unable to delete user data");
				}
				// _first.setText("");
				// _last.setText("");
				// _title.setSelectedIndex(0);
				// _username.setText("");
				// _type.setSelectedIndex(0);

			} else if (_action.equalsIgnoreCase("view")) {

				_where = addWhere((String) _id.getSelectedItem(),
						_first.getText(), _last.getText(),
						(String) _title.getSelectedItem(), _username.getText(),
						(String) _type.getSelectedItem());
				UserDatabase db = new UserDatabase();
				ArrayList<String> array = db.view(_where);
				if (array.size() > 0) {
					id = array.get(0);
					firstname = array.get(1);
					lastname = array.get(2);
					title = array.get(3);
					username = array.get(4);
					type = array.get(5);

					Boolean back = false;
					Boolean next;

					if (7 < array.size()) {
						next = true;
					} else {
						next = false;
					}

					UserDBScreen screen = new UserDBScreen(_where, _frame, _login_type,
							_loc, "view", id, firstname, lastname, title,
							username, type, array, 6, next, back);
					screen.createFrame(3);
				} else {
					_error.setText("Could not find a matching user");
				}
			}
		} else {
			// Is next

			id = _array.get(_i);
			firstname = _array.get(_i + 1);
			lastname = _array.get(_i + 2);
			title = _array.get(_i + 3);
			username = _array.get(_i + 4);
			type = _array.get(_i + 5);

			_where = addWhere(id, firstname, lastname, title, username, type);

			Boolean back;
			Boolean next;
			if (_i < 2) {
				back = false;
			} else {
				back = true;
			}
			if ((_i + 7) < _array.size()) {
				next = true;
			} else {
				next = false;
			}

			UserDBScreen screen = new UserDBScreen(_where, _frame, _login_type, _loc,
					"view", id, firstname, lastname, title, username, type,
					_array, _i + 6, next, back);
			screen.createFrame(3);

		}

	}

	/**
	 * @param i
	 * @param f
	 * @param l
	 * @param t
	 * @param u
	 * @param ty
	 * @return
	 */
	public String addWhere(String i, String f, String l, String t, String u,
			String ty) {
		String where = "";
		boolean addAnd = false;

		if (!Helper.isEmptyString(i)) {
			where += "ID=" + i;
			addAnd = true;
		}

		if (!Helper.isEmptyString(f)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "FIRST_NAME='" + f + "'";
			} else {
				where += "FIRST_NAME='" + f + "'";
				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(l)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "LAST_NAME='" + l + "'";
			} else {
				where += "LAST_NAME='" + l + "'";

				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(t)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "TITLE='" + t + "'";

			} else {
				where += "TITLE='" + t + "'";

				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(ty)) {
			if (addAnd) {
				where = Helper.addAnd(where);
				where += "TYPE='" + ty + "'";

			} else {
				where += "TYPE='" + ty + "'";

				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(u)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "USERNAME='" + u + "'";
			} else {
				where += "USERNAME='" + u + "'";

				addAnd = true;
			}
		}

		return where;
	}

	/**
	 * @return
	 */
	public String addCols() {
		String col = "";

		String firstN = _first.getText();

		col += "FIRST_NAME = '" + firstN + "'";

		String lastN = _last.getText();

		col += ", LAST_NAME = '" + lastN + "'";

		String titleS = (String) _title.getSelectedItem();

		col += ", TITLE = '" + titleS + "'";

		String typeS = (String) _type.getSelectedItem();

		col += ", TYPE = '" + typeS + "'";

		String uname = _username.getText();

		col += ", USERNAME = '" + uname + "'";

		return col;
	}
}
