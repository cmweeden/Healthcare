package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PatientToDBListener implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// User log-on type (subject)
	static String _fn;

	// User log-on location (community)
	static String _loc;

	JTextField _first;
	JTextField _last;
	JTextField _birth;

	String _pidS;
	JComboBox _pid;
	JComboBox _ins;
	String _action;

	String _colvals;
	String _where;
	String _docId;

	boolean whichId;
	boolean _pol;

	ArrayList<String> _array;
	int _i;

	JLabel _error;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param action
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public PatientToDBListener(JFrame f, String fn, String loc, String action,
			JTextField firstname, JTextField lastname, JTextField dob,
			JComboBox insurance, JLabel error) {
		// Used for patient add
		_frame = f;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		_fn = fn;
		whichId = false;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param fn
	 * @param loc
	 * @param action
	 * @param pid
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public PatientToDBListener(JFrame f, String fn, String loc, String action,
			JComboBox pid, JTextField firstname, JTextField lastname,
			JTextField dob, JComboBox insurance, JLabel error) {
		// Used for patient view (all but researcher)
		_frame = f;
		_fn = fn;
		_pid = pid;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = true;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param fn
	 * @param loc
	 * @param action
	 * @param pid
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public PatientToDBListener(JFrame f, String fn, String loc, String action,
			JComboBox pid, JTextField dob, JComboBox insurance, JLabel error) {
		// Used for patient view (researcher)
		_frame = f;
		_fn = fn;
		_pid = pid;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = true;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param fn
	 * @param loc
	 * @param action
	 * @param pid
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public PatientToDBListener(JFrame f, String fn, String loc, String action,
			String pid, JTextField firstname, JTextField lastname,
			JTextField dob, JComboBox insurance, JLabel error) {
		// Used for physician update
		// TODO Why does admin update pass through the where string, but not
		// physician?
		_frame = f;
		_fn = fn;
		_pidS = pid;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = false;
		_loc = loc;

		_error = error;

	}

	/**
	 * @param f
	 * @param fn
	 * @param loc
	 * @param action
	 * @param pid
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param array
	 * @param i
	 * @param error
	 */
	public PatientToDBListener(JFrame f, String fn, String loc, String action,
			String pid, JTextField firstname, JTextField lastname,
			JTextField dob, JComboBox insurance, ArrayList<String> array,
			int i, JLabel error) {
		// Used for patient update if from policy
		// Pass through array in case of multiple. May be empty?
		_frame = f;
		_fn = fn;
		_pidS = pid;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = false;
		_loc = loc;
		_array = array;
		_i = i;

		_error = error;

	}

	/**
	 * @param frame
	 * @param fn
	 * @param loc
	 * @param action
	 * @param nameText
	 * @param lnameText
	 * @param ageText
	 * @param insBox
	 * @param where
	 * @param error
	 */
	public PatientToDBListener(JFrame frame, String fn, String loc,
			String action, JTextField nameText, JTextField lnameText,
			JTextField ageText, JComboBox insBox, String where, JLabel error) {
		// Used for admin update and delete

		_frame = frame;
		_fn = fn;
		_first = nameText;
		_last = lnameText;
		_birth = ageText;
		_ins = insBox;
		_action = action;
		_where = where;
		whichId = false;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param where
	 * @param frame
	 * @param fn
	 * @param loc
	 * @param action
	 * @param array
	 * @param i
	 * @param pol
	 * @param error
	 */
	public PatientToDBListener(String where, JFrame frame, String fn,
			String loc, String action, ArrayList<String> array, int i,
			Boolean pol, JLabel error) {
		// Used when multiple patients, from next patient and previous patient
		// buttons
		_where = where;
		_frame = frame;
		_fn = fn;
		_action = action;
		_array = array;
		_i = i;
		_pol = pol;
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

		if (_action.equalsIgnoreCase("insert")) {
			if (_fn.equalsIgnoreCase("administrator")) {

				String firstname = _first.getText();
				String lastname = _last.getText();
				String dob = _birth.getText();
				String insurance = (String) _ins.getSelectedItem();
				PatientDatabase db = new PatientDatabase();
				int pid = db.insert(firstname, lastname, dob, insurance);
				if (pid < 0) {
					_error.setText("Could not add new patient");
				} else {
					_error.setText("New patient added with ID " + pid);
					_first.setText("");
					_last.setText("");
					_birth.setText("");
					_ins.setSelectedIndex(0);
				}
				// PatientDBScreen screen = new PatientDBScreen(_frame, _fn,
				// _loc);
				// screen.createFrame(2);

			} else {
				_error.setText("Must be an admin to add new patients.");
			}
		} else if (_action.equalsIgnoreCase("update")) {
			if (_fn.equalsIgnoreCase("administrator")) {
				PatientDatabase db = new PatientDatabase();
				boolean success = db.update(addCols(), _where);
				// System.out.println("addCols: " + addCols());
				// System.out.println("where: " + _where);
				// PatientDBScreen screen = new PatientDBScreen(_frame, _fn,
				// _loc);
				// screen.createFrame(1);
				if (success) {
					_error.setText("Patient data updated");
				} else {
					_error.setText("Unable to update patient data");
				}

			} else if (_fn.equalsIgnoreCase("physician")) {

				if ((_pid == null) && (_pidS == null)) {
					_i = _i - 5;
					// System.out.println(_i);
					String pid = _array.get(_i);
					String f = _array.get(_i + 1);
					String l = _array.get(_i + 2);
					String dob = _array.get(_i + 3);
					String ins = _array.get(_i + 4);
					_where = addWhereS(pid, f, l, dob, ins);
				}
				if (_where == null) {
					_where = addWhereS(_pidS, null, null, null, null);
				}
				String sentence = "U.TYPE = '" + _fn + "'";
				PolicyDatabase p_db = new PolicyDatabase();
				ArrayList<String> writeP = p_db.checkWrite(sentence);

				String write = writeP.get(0);
				// System.out.println("write: " + write);
				if (write.equals("true")) {
					PatientDatabase db = new PatientDatabase();
					boolean success = db.update(addCols(), _where);
					// System.out.println("addCols: " + addCols());
					// System.out.println("where: " + _where);
					// PatientDBScreen screen = new PatientDBScreen(_frame, _fn,
					// _loc);
					// screen.createFrame(1);
					if (success) {
						_error.setText("Patient data updated");
					} else {
						_error.setText("Unable to update patient data");
					}
				} else {

					JFrame frame2 = new JFrame("Permission");
					frame2.setSize(350, 250);
					frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					JPanel panel2 = new JPanel();
					JTextArea area2 = new JTextArea(
							"YOU DO NOT HAVE ACCESS FOR THIS ACTION");
					panel2.add(area2);
					frame2.add(panel2);
					frame2.setVisible(true);
					frame2.setLocationRelativeTo(null);
					frame2.pack();
					_error.setText("Update denied");
				}
			}
		} else if (_action.equalsIgnoreCase("view")) {
			// Currently need id provided to search for certain function
			// Need to test further where this works
			// if (true) { //Uncomment while testing id functions
			if (Helper.isEmptyString((String) _pid.getSelectedItem())) { // and
																			// comment
																			// this
																			// out
																			// so
																			// do
																			// not
																			// have
																			// to
																			// mess
																			// with
																			// brackets
				_error.setText("Must enter an ID.");
			} else {
				if (_fn.equalsIgnoreCase("administrator")) {

					String where = addWhere(_pid, _first, _last, _birth, _ins);
					PatientDatabase db = new PatientDatabase();
					ArrayList<String> array = db.view(where);

					if (array.size() > 0) {

						String pid = array.get(0);
						String f = array.get(1);
						String l = array.get(2);
						String dob = array.get(3);
						String ins = array.get(4);

						Boolean back = false;
						Boolean next;

						if (6 < array.size()) {
							next = true;
						} else {
							next = false;
						}

						PatientDBScreen screen = new PatientDBScreen(where,
								_frame, _fn, _loc, pid, f, l, dob, ins, array,
								5, next, back);
						screen.createFrame(3);
					} else {
						_error.setText("Could not find a matching patient");
					}

				} else if (_fn.equalsIgnoreCase("researcher")) {

					String where = addWhere(_pid, null, null, _birth, _ins);
					PatientDatabase db = new PatientDatabase();
					ArrayList<String> array = db.view(where);
					if (array.size() > 0) {
						String pid = array.get(0);
						String dob = array.get(3);
						String ins = array.get(4);

						Boolean back = false;
						Boolean next;

						if (6 < array.size()) {
							next = true;
						} else {
							next = false;
						}
						PatientDBScreen screen = new PatientDBScreen(where,
								_frame, _fn, _loc, pid, dob, ins, array, 5,
								next, back);
						screen.createFrame(3);
					} else {
						_error.setText("Could not find a matching patient");
					}

				} else if (_fn.equalsIgnoreCase("physician")) {

					String sentence = "U.TYPE = '" + _fn + "'";
					PolicyDatabase p_db = new PolicyDatabase();
					ArrayList<String> p_id = p_db.checkCred(sentence);
					// String pid2;

					int i = 0;
					if (!(p_id.isEmpty())) {

						// Loops through the patients that the user has access
						// to until
						// Reaches the patient whose data is being requested.
						// TODO Would be faster if find the patient data and
						// loop through
						// Policies to see if the user has access
						// But would require re-working
						while (i < p_id.size()) {
							String pid2 = p_id.get(i);

							// TODO Need to check against whichever values are
							// filled out
							// Not just pid, and possibly not pid at all
							// System.out.println(pid2);
							// System.out.println(_pid.getSelectedItem());

							// Do not need a sub loop because only one patient
							// id will match
							String where = addWhere(_pid, _first, _last,
									_birth, _ins);
							PatientDatabase db = new PatientDatabase();
							ArrayList<String> array = db.view(where);
							if (_pid.getSelectedIndex() != 0) {
								if (_pid.getSelectedItem().equals(pid2)) {

									String pid = array.get(0);
									String f = array.get(1);
									String l = array.get(2);
									String dob = array.get(3);
									String ins = array.get(4);
									PatientDBScreen screen = new PatientDBScreen(
											_frame, _fn, _loc, pid, f, l, dob,
											ins);
									screen.createFrame(3);
								}
							} else {

								// Need a sub loop because multiple patients may
								// have similar data
								// As long as user has access we want to display
								// all
								// Currently this will only display the first
								// result which the user has access to
								// TODO Display more than the first match
								// Need a new array with a double loop adding
								// elements to the array to add all patients
								// who both match the query and the user has
								// access to, since each array
								// currently in use only fits one criteria
								int j = 0;
								while (j < array.size()) {

									String pid = array.get(j);
									if (pid.equals(pid2)) {
										String f = array.get(j + 1);
										String l = array.get(j + 2);
										String dob = array.get(j + 3);
										String ins = array.get(j + 4);
										PatientDBScreen screen = new PatientDBScreen(
												_frame, _fn, _loc, pid, f, l,
												dob, ins);
										screen.createFrame(3);
									}
								}

							}
							i++;
						}
					}
					// JFrame frame2 = new JFrame("Permission");
					// frame2.setSize(350, 250);
					// frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					// JPanel panel2 = new JPanel();
					// JTextArea area2 = new JTextArea(
					// "YOU DO NOT HAVE ACCESS TO THIS PATIENT");
					// panel2.add(area2);
					// frame2.add(panel2);
					// frame2.setVisible(true);
					// frame2.setLocationRelativeTo(null);
					// frame2.pack();
					_error.setText("ACCESS DENIED");

				} else if (_fn.equalsIgnoreCase("insurance agent")) {

					String sentence = "U.TYPE = '" + _fn + "'";
					PolicyDatabase p_db = new PolicyDatabase();
					ArrayList<String> p_id = p_db.checkCred(sentence);

					int i = 0;
					if (!(p_id.isEmpty())) {

						while (i < p_id.size()) {
							String pid2 = p_id.get(i);
							if (_pid.getSelectedItem().equals(pid2)) {
								PatientDatabase db = new PatientDatabase();
								ArrayList<String> array = db.view(addWhere(
										_pid, _first, _last, _birth, _ins));
								String pid = array.get(0);
								String f = array.get(1);
								String l = array.get(2);
								String dob = array.get(3);
								String ins = array.get(4);
								PatientDBScreen screen = new PatientDBScreen(
										_frame, _fn, _loc, pid, f, l, dob, ins);
								screen.createFrame(3);
							}
							i++;
						}
					}

					_error.setText("ACCESS DENIED");

				} else {
					_error.setText("Must select a valid type to view.");
				}
			}
		} else if (_action.equalsIgnoreCase("delete")) {
			if (_fn.equalsIgnoreCase("administrator")) {

				if (_pid != null) {
					_where = addWhere(_pid, _first, _last, _birth, _ins);
				}
				PatientDatabase db = new PatientDatabase();
				boolean success = db.delete(_where);

				if (success) {
					PatientDBScreen screen = new PatientDBScreen(_frame, _fn,
							_loc);
					screen.createFrame(1);
				} else {
					_error.setText("Unable to delete patient data");
				}

				// _pid.setSelectedIndex(0);
				// _first.setText("");
				// _last.setText("");
				// _birth.setText("");
				// _ins.setSelectedIndex(0);
			} else {
				_error.setText("Must be an admin to delete patients.");
			}
			if (!(_fn.equalsIgnoreCase("researcher"))) {
				_first.setText("");
				_last.setText("");
			}
			_birth.setText("");
			_ins.setSelectedItem("");
		} else if (_action.equalsIgnoreCase("next")) {
			if (_fn.equalsIgnoreCase("administrator")) {

				String pid = _array.get(_i);
				String f = _array.get(_i + 1);
				String l = _array.get(_i + 2);
				String dob = _array.get(_i + 3);
				String ins = _array.get(_i + 4);

				_where = addWhereS(pid, f, l, dob, ins);

				Boolean back;
				Boolean next;
				if (_i < 2) {
					back = false;
				} else {
					back = true;
				}
				if ((_i + 6) < _array.size()) {
					next = true;
				} else {
					next = false;
				}

				PatientDBScreen screen = new PatientDBScreen(_where, _frame,
						_fn, _loc, pid, f, l, dob, ins, _array, _i + 5, next,
						back);
				screen.createFrame(3);

			} else if (_fn.equalsIgnoreCase("researcher")) {

				if (!_pol) {
					String pid = _array.get(_i);
					String dob = _array.get(_i + 3);
					String ins = _array.get(_i + 4);
					_where = addWhereS(pid, null, null, dob, ins);

					Boolean back;
					Boolean next;
					if (_i < 2) {
						back = false;
					} else {
						back = true;
					}
					if ((_i + 6) < _array.size()) {
						next = true;
					} else {
						next = false;
					}

					PatientDBScreen screen = new PatientDBScreen(_where,
							_frame, _fn, _loc, pid, dob, ins, _array, _i + 5,
							next, back);
					screen.createFrame(3);
				} else {

					String pid2 = _array.get(_i);

					PatientDatabase db = new PatientDatabase();
					ArrayList<String> array2 = db.view("PID= " + pid2);

					String pid3 = array2.get(0);
					String f = array2.get(1);
					String l = array2.get(2);
					String dob = array2.get(3);
					String ins = array2.get(4);

					Boolean back;

					if (_i < 2) {
						back = false;
					} else {
						back = true;
					}
					Boolean next;

					if (_i + 6 < _array.size()) {
						next = true;
					} else {
						next = false;
					}

					PatientDBScreen screen = new PatientDBScreen(_frame, _fn,
							_loc, pid3, f, l, dob, ins, _array, _i + 5, next,
							back, _pol);
					screen.createFrame(3);
				}

			} else {
				_error.setText("Must select a valid type to view.");
			}
		}

	}

	/**
	 * @param p
	 * @param f
	 * @param l
	 * @param d
	 * @param i
	 * @return
	 */
	public String addWhere(JComboBox p, JTextField f, JTextField l,
			JTextField d, JComboBox i) {

		String where = "";
		boolean addAnd = false;

		try {
			if (!Helper.isEmptyString((String) p.getSelectedItem())) {
				where += "PID=" + Integer.valueOf((String) p.getSelectedItem());
				addAnd = true;
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString(f.getText())) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "FIRST_NAME='" + f.getText() + "'";
				} else {
					where += "FIRST_NAME='" + f.getText() + "'";
					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString(l.getText())) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "LAST_NAME='" + l.getText() + "'";
				} else {
					where += "LAST_NAME='" + l.getText() + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {

			if (!Helper.isEmptyString(d.getText())) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "DATE_OF_BIRTH='" + d.getText() + "'";

				} else {
					where += "DATE_OF_BIRTH='" + d.getText() + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString((String) i.getSelectedItem())) {
				if (addAnd) {
					where = Helper.addAnd(where);
					where += "INSURANCE_PROVIDER='"
							+ (String) i.getSelectedItem() + "'";

				} else {
					where += "INSURANCE_PROVIDER='"
							+ (String) i.getSelectedItem() + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}

		// System.out.println(where);
		return where;
	}

	/**
	 * @param p
	 * @param f
	 * @param l
	 * @param d
	 * @param i
	 * @return
	 */
	public String addWhereS(String p, String f, String l, String d, String i) {

		String where = "";
		boolean addAnd = false;

		try {
			if (!Helper.isEmptyString(p)) {
				where += "PID=" + p;
				addAnd = true;
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString(f)) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "FIRST_NAME='" + f + "'";
				} else {
					where += "FIRST_NAME='" + f + "'";
					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString(l)) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "LAST_NAME='" + l + "'";
				} else {
					where += "LAST_NAME='" + l + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {

			if (!Helper.isEmptyString(d)) {

				if (addAnd) {
					where = Helper.addAnd(where);
					where += "DATE_OF_BIRTH='" + d + "'";

				} else {
					where += "DATE_OF_BIRTH='" + d + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}
		try {
			if (!Helper.isEmptyString(i)) {
				if (addAnd) {
					where = Helper.addAnd(where);
					where += "INSURANCE_PROVIDER='" + i + "'";

				} else {
					where += "INSURANCE_PROVIDER='" + i + "'";

					addAnd = true;
				}
			}
		} catch (NullPointerException e) {

		}

		// System.out.println(where);
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

		String dob = _birth.getText();

		col += ", DATE_OF_BIRTH = '" + dob + "'";

		String insu = (String) _ins.getSelectedItem();

		col += ", INSURANCE_PROVIDER = '" + insu + "'";

		return col;
	}

}
