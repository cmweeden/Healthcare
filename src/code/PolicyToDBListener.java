package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PolicyToDBListener implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// User log-on type (subject)
	static String _fn;

	// User log-on location (community)
	static String _loc;

	JComboBox _pid;
	JComboBox _subject;
	JComboBox _org;
	JCheckBox _read;
	JCheckBox _write;
	JCheckBox _tool1;
	JCheckBox _tool2;
	JCheckBox _rule1;
	JCheckBox _resp1;
	JCheckBox _resp2;
	String _action;
	boolean whichId;
	boolean whichOrg;
	String _where;
	ArrayList<String> _array;
	int _i;

	JLabel _error;

	/**
	 * @param where
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param a
	 * @param array
	 * @param i
	 * @param error
	 */
	public PolicyToDBListener(String where, JFrame f, String fn, String loc,
			String a, ArrayList<String> array, int i, JLabel error) {
		// When multiple policies, from next and previous buttons
		_where = where;
		_frame = f;
		_fn = fn;
		_action = a;
		_loc = loc;
		_array = array;
		_i = i;

		_error = error;
	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param a
	 * @param pid
	 * @param subject
	 * @param org
	 * @param read
	 * @param write
	 * @param tool1
	 * @param tool2
	 * @param rule1
	 * @param resp1
	 * @param resp2
	 * @param error
	 */
	public PolicyToDBListener(JFrame f, String fn, String loc, String a,
			JComboBox pid, JComboBox subject, JComboBox org, JCheckBox read,
			JCheckBox write, JCheckBox tool1, JCheckBox tool2, JCheckBox rule1,
			JCheckBox resp1, JCheckBox resp2, JLabel error) {
		// View or delete from admin, or view from researcher
		_frame = f;
		_fn = fn;
		_action = a;
		_pid = pid;
		_subject = subject;
		_org = org;
		_read = read;
		_write = write;

		_tool1 = tool1;
		_tool2 = tool2;
		_rule1 = rule1;
		_resp1 = resp1;
		_resp2 = resp2;

		whichId = true;
		whichOrg = true;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param a
	 * @param pid
	 * @param read
	 * @param write
	 * @param tool1
	 * @param tool2
	 * @param rule1
	 * @param resp1
	 * @param resp2
	 * @param error
	 */
	public PolicyToDBListener(JFrame f, String fn, String loc, String a,
			JComboBox pid, JCheckBox read, JCheckBox write, JCheckBox tool1,
			JCheckBox tool2, JCheckBox rule1, JCheckBox resp1, JCheckBox resp2,
			JLabel error) {
		// View from physician or insurance agent
		_frame = f;
		_fn = fn;
		_action = a;
		_pid = pid;
		_loc = loc;
		_read = read;
		_write = write;

		_tool1 = tool1;
		_tool2 = tool2;
		_rule1 = rule1;
		_resp1 = resp1;
		_resp2 = resp2;

		whichId = false;
		whichOrg = false;

		_error = error;
	}

	/**
	 * @param where
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param a
	 * @param idb
	 * @param subjectb
	 * @param orgb
	 * @param read
	 * @param write
	 * @param tool1
	 * @param tool2
	 * @param rule1
	 * @param resp1
	 * @param resp2
	 * @param error
	 */
	public PolicyToDBListener(String where, JFrame f, String fn, String loc,
			String a, JComboBox idb, JComboBox subjectb, JComboBox orgb,
			JCheckBox read, JCheckBox write, JCheckBox tool1, JCheckBox tool2,
			JCheckBox rule1, JCheckBox resp1, JCheckBox resp2, JLabel error) {
		// Update or delete from admin when came through view previously

		_where = where;
		_frame = f;
		_fn = fn;
		_action = a;
		_subject = subjectb;
		_pid = idb;
		_org = orgb;
		_read = read;
		_write = write;

		_tool1 = tool1;
		_tool2 = tool2;
		_rule1 = rule1;
		_resp1 = resp1;
		_resp2 = resp2;

		_loc = loc;
		whichId = true;
		whichOrg = true;

		_error = error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		PolicyDatabase db = new PolicyDatabase();
		PatientDatabase db2 = new PatientDatabase();

		if (_action.equals("insert")) {

			String pid = (String) _pid.getSelectedItem();
			String subject;
			if (_subject != null) {
				subject = (String) _subject.getSelectedItem();
			} else {
				subject = _fn;
			}
			String org;
			if (_org != null) {
				org = (String) _org.getSelectedItem();
			} else {
				org = _loc;
			}
			String tool;
			if (_tool1.isSelected() && _tool2.isSelected()) {
				tool = "E-mail and Call";
			} else if (_tool1.isSelected()) {
				tool = "E-mail";
			} else if (_tool2.isSelected()) {
				tool = "Call";
			} else {
				tool = "No contact method set";
			}
			String rule;
			if (_rule1.isSelected()) {
				rule = "mismatch";
			} else {
				rule = "none";
			}
			String resp;
			if (_resp1.isSelected() && _resp2.isSelected()) {
				resp = "Administrator and Patient";
			} else if (_resp1.isSelected()) {
				resp = "Administrator";
			} else if (_resp2.isSelected()) {
				resp = "Patient";
			} else {
				resp = "No contact set";
			}
			if (_fn.equalsIgnoreCase("administrator")) {
				int polid = db.insert(pid, subject, org, _read.isSelected(),
						_write.isSelected(), tool, rule, resp);
				if (!(polid < 0)) {
					// PolicyDBScreen screen = new PolicyDBScreen(_frame, _fn,
					// _loc);
					// screen.createFrame(2);
					_pid.setSelectedIndex(0);
					_subject.setSelectedIndex(0);
					_org.setSelectedIndex(0);
					_read.setSelected(false);
					_write.setSelected(false);
					_tool1.setSelected(false);
					_tool2.setSelected(false);
					_rule1.setSelected(false);
					_resp1.setSelected(false);
					_resp2.setSelected(false);
					_error.setText("Policy successfully added");
				} else {
					_error.setText("Unable to insert policy");
				}
			} else {
				_error.setText("Must be an admin to add new policies.");
			}
		} else if (_action.equals("update")) {
			if (_fn.equalsIgnoreCase("administrator")) {

				System.out.println(_where);
				boolean success = db.update(addCols(), _where);

				if (success) {
					_error.setText("Policy data updated");
				} else {
					_error.setText("Unable to update policy data");
				}

				// PolicyDBScreen screen = new PolicyDBScreen(_frame, _fn,
				// _loc);
				// screen.createFrame(1);
			} else {
				System.out.println("Must be an admin to modify policies.");
			}
		} else if (_action.equals("delete")) {
			if (_fn.equalsIgnoreCase("administrator")) {

				if (_where == null) {
					String pid = (String) _pid.getSelectedItem();
					String subject;
					if (_subject != null) {
						subject = (String) _subject.getSelectedItem();
					} else {
						subject = _fn;
					}
					String org;
					if (_org != null) {
						org = (String) _org.getSelectedItem();
					} else {
						org = _loc;
					}
					_where = addWhere(pid, subject, org);
				}
				boolean success = db.delete(_where);
				if (success) {
					PolicyDBScreen screen = new PolicyDBScreen(_frame, _fn,
							_loc);
					screen.createFrame(1);
				} else {
					_error.setText("Unable to delete policy");
				}
			} else {
				_error.setText("Must be an admin to modify policies.");
			}
		} else if (_action.equals("view")) {
			if ((_fn.equalsIgnoreCase("administrator"))) {
				// TODO allow pid to be blank, as long as one of pid, subj, org
				// is selected
				// view
				String pid = (String) _pid.getSelectedItem();
				String subject;
				if (_subject != null) {
					subject = (String) _subject.getSelectedItem();
				} else {
					subject = "null";
				}
				String org;
				if (_org != null) {
					org = (String) _org.getSelectedItem();
				} else {
					org = "null";
				}
				_where = addWhere(pid, subject, org);

				ArrayList<String> array = db.view(_where);
				if (array.size() > 0) {
					String pid2 = array.get(0);
					String sub = array.get(1);
					String org2 = array.get(2);
					String read = array.get(3);
					String write = array.get(4);
					String tool = array.get(5);
					String rule = array.get(6);
					String resp = array.get(7);

					Boolean back = false;
					Boolean next;

					if (9 < array.size()) {
						next = true;
					} else {
						next = false;
					}

					if (true) {
						PolicyDBScreen screen = new PolicyDBScreen(_where,
								_frame, _fn, _loc, pid2, sub, org2, read,
								write, tool, rule, resp, array, 8, next, back);
						screen.createFrame(3);

						if (true) {
							GetXmlListener view = new GetXmlListener(_pid,
									_subject, _org, _read, _write, _fn);
							view.actionPerformed(null);
						}
					}
				} else {
					_error.setText("Could not find a matching policy");
				}

			} else if (_fn.equalsIgnoreCase("researcher")) {
				String pid = (String) _pid.getSelectedItem();
				String subject;
				if (_subject != null) {
					subject = (String) _subject.getSelectedItem();
				} else {
					subject = _fn;
				}
				String org;
				if (_org != null) {
					org = (String) _org.getSelectedItem();
				} else {
					org = _loc;
				}
				_where = addWhere(pid, subject, org);
				ArrayList<String> array = db.view(_where);
				if (array.size() > 0) {
					String pid2 = array.get(0);
					String sub = array.get(1);
					String org2 = array.get(2);
					String read = array.get(3);
					String write = array.get(4);
					String tool = array.get(5);
					String rule = array.get(6);
					String resp = array.get(7);

					ArrayList<String> array2 = db2.view("PID= " + pid2);

					String pid3 = array2.get(0);
					String f = array2.get(1);
					String l = array2.get(2);
					String dob = array2.get(3);
					String ins = array2.get(4);

					Boolean back = false;
					Boolean next;
					Boolean policy = true;

					// Currently allowing only one patient lookup through policy
					// for
					// researcher, since it brings up patient data
					// not just policy data
					// and has errors because this is trying to cycle through
					// the
					// policy data

					// if (9 < array.size()) {
					// next = true;
					// } else {
					next = false;
					// }
					if (true) {
						// Here, pop up patient information screen instead
						// PolicyDBScreen screen = new
						// PolicyDBScreen(addWhere(),
						// _frame,
						// _type, pid2, sub, org2, read, write);
						// screen.createFrame(3);

						PatientDBScreen screen = new PatientDBScreen(_frame,
								_fn, _loc, pid3, f, l, dob, ins, array, 8,
								next, back, policy);
						screen.createFrame(3);

						if (true) {
							GetXmlListener view = new GetXmlListener(_pid,
									_subject, _org, _read, _write, _fn);
							view.actionPerformed(null);
						}
					}
				} else {
					_error.setText("Could not find a matching policy");
				}
			} else if (_fn.equalsIgnoreCase("insurance agent")) {
				// Need to force subject to be logged in user pass thru username
				VerifyCredListener verify = new VerifyCredListener(_frame, _fn,
						_loc, _pid, _read, _write, _error);
				verify.actionPerformed(null);
			} else if (_fn.equalsIgnoreCase("physician")) {
				// Need to force subject to be logged in user pass thru username
				// Make subject == username, but display full name and title
				VerifyCredListener verify = new VerifyCredListener(_frame, _fn,
						_loc, _pid, _read, _write, _error);
				verify.actionPerformed(null);
			} else {
				_error.setText("Must select a valid type to view.");
			}
		} else if (_action.equals("next")) {
			if ((_fn.equalsIgnoreCase("administrator"))) {

				// view

				String pid2 = _array.get(_i);
				String sub = _array.get(_i + 1);
				String org2 = _array.get(_i + 2);
				String read = _array.get(_i + 3);
				String write = _array.get(_i + 4);
				String tool = _array.get(_i + 5);
				String rule = _array.get(_i + 6);
				String resp = _array.get(_i + 7);

				Boolean back;
				Boolean next;

				if (_i < 2) {
					back = false;
				} else {
					back = true;
				}

				if ((_i + 9) < _array.size()) {
					next = true;
				} else {
					next = false;
				}

				_where = addWhere(pid2, sub, org2);

				if (true) {
					PolicyDBScreen screen = new PolicyDBScreen(_where, _frame,
							_fn, _loc, pid2, sub, org2, read, write, tool,
							rule, resp, _array, _i + 8, next, back);
					screen.createFrame(3);

				}

			}
		}
		if (_pid != null) {
			_pid.setSelectedIndex(0);
		}
		// TODO Causes Errors
		if (whichId) {
			_subject.setSelectedIndex(0);
		}
		if (whichOrg) {
			_org.setSelectedIndex(0);
		}
	}

	/**
	 * @param p
	 * @param s
	 * @param o
	 * @return
	 */
	public String addWhere(String p, String s, String o) {
		String where = "";
		boolean addAnd = false;

		if (!(Helper.isEmptyString(p))) {
			where += "PID=" + p;
			addAnd = true;

		}

		if (!(Helper.isEmptyString(s))) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "SUBJECT='" + s + "'";

			} else {
				where += "SUBJECT='" + s + "'";

				addAnd = true;
			}
		}
		if (!(Helper.isEmptyString(o))) {
			if (addAnd) {
				where = Helper.addAnd(where);
				where += "ORGANIZATION='" + o + "'";
				;

			} else {
				where += "ORGANIZATION='" + o + "'";
				;
				addAnd = true;
			}
		}

		return where;
	}

	/**
	 * @return
	 */
	public String addCols() {
		// TODO Where is this used again
		String col = "";

		String pid = (String) _pid.getSelectedItem();
		col += "PID = " + pid;

		String subjectS = (String) _subject.getSelectedItem();

		col += ", SUBJECT = '" + subjectS + "'";

		String orgS = (String) _org.getSelectedItem();

		col += ", ORGANIZATION = '" + orgS + "'";

		String read;
		if (_read.isSelected()) {
			read = "true";
		} else {
			read = "false";
		}

		col += ", READ = '" + read + "'";

		String write;
		if (_write.isSelected()) {
			write = "true";
		} else {
			write = "false";
		}

		col += ", WRITE = '" + write + "'";

		return col;
	}

}
