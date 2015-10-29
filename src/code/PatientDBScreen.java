package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PatientDBScreen implements ActionListener {

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

	String _pid;
	String _login_typeame;
	String _lname;
	String _dob;
	String _ins;
	String _where;

	String _id;
	String _back;
	Boolean _pol;
	ArrayList<String> _array;
	int _i;
	Boolean _next;
	Boolean _backB;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param type
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 */
	public PatientDBScreen(JFrame f, String type, String loc) {
		// Used for physician or insurance agent
		_frame = f;
		_login_type = type;
		height = 40;
		width = 160;
		_loc = loc;
		_pol = false;
		_next = false;
		_backB = false;

	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param array
	 * @param i
	 * @param n
	 * @param b
	 * @param pol
	 */
	public PatientDBScreen(JFrame f, String login_type, String loc,
			ArrayList<String> array, int i, Boolean n, Boolean b, Boolean pol) {
		// Used for physician or insurance agent coming from policy
		_frame = f;
		_login_type = login_type;
		height = 40;
		width = 160;
		_loc = loc;
		_pol = pol;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;

	}

	/**
	 * @param f
	 * @param type
	 * @param loc
	 * @param pol
	 */
	public PatientDBScreen(JFrame f, String type, String loc, Boolean pol) {
		// Used for physician or insurance agent coming from policy
		_frame = f;
		_login_type = type;
		height = 40;
		width = 160;
		_loc = loc;
		_pol = pol;
		_next = false;
		_backB = false;

	}

	/**
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param first
	 * @param last
	 * @param dob
	 * @param ins
	 */
	public PatientDBScreen(JFrame f, String type, String loc, String pid,
			String first, String last, String dob, String ins) {
		// Used for physician or insurance agent
		height = 40;
		width = 160;

		_frame = f;
		_login_type = type;
		_loc = loc;

		_pid = pid;
		_login_typeame = first;
		_lname = last;
		_dob = dob;
		_ins = ins;

		_pol = false;
		_next = false;
		_backB = false;
	}

	/**
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param dob
	 * @param ins
	 */
	public PatientDBScreen(JFrame f, String type, String loc, String pid,
			String dob, String ins) {
		// Used for physician or insurance agent
		_frame = f;
		_login_type = type;
		_pid = pid;
		_loc = loc;
		_dob = dob;
		_ins = ins;
		height = 40;
		width = 160;
		_pol = false;
		_next = false;
		_backB = false;
	}

	/**
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param login_typeame
	 * @param lname
	 * @param dob
	 * @param ins
	 * @param array
	 * @param i
	 * @param n
	 * @param b
	 * @param p
	 */
	public PatientDBScreen(JFrame f, String type, String loc, String pid,
			String login_typeame, String lname, String dob, String ins,
			ArrayList<String> array, int i, Boolean n, Boolean b, Boolean p) {

		_frame = f;
		_login_type = type;

		height = 40;
		width = 160;
		_loc = loc;
		_pid = pid;
		_login_typeame = login_typeame;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_pol = p;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param where
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param login_typeame
	 * @param lname
	 * @param dob
	 * @param ins
	 */
	public PatientDBScreen(String where, JFrame f, String type, String loc,
			String pid, String login_typeame, String lname, String dob, String ins) {

		_where = where;
		_frame = f;
		_login_type = type;

		height = 40;
		width = 160;
		_loc = loc;
		_pid = pid;
		_login_typeame = login_typeame;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_pol = false;
		_next = false;
		_backB = false;
	}

	/**
	 * @param where
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param login_typeame
	 * @param lname
	 * @param dob
	 * @param ins
	 * @param array
	 * @param i
	 * @param n
	 * @param b
	 */
	public PatientDBScreen(String where, JFrame f, String type, String loc,
			String pid, String login_typeame, String lname, String dob, String ins,
			ArrayList<String> array, int i, Boolean n, Boolean b) {

		// Used by admin functions
		_where = where;
		_frame = f;
		_login_type = type;

		height = 40;
		width = 160;
		_loc = loc;
		_pid = pid;
		_login_typeame = login_typeame;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_pol = false;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param where
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param dob
	 * @param ins
	 * @param array
	 * @param i
	 * @param n
	 * @param b
	 */
	public PatientDBScreen(String where, JFrame f, String type, String loc,
			String pid, String dob, String ins, ArrayList<String> array, int i,
			Boolean n, Boolean b) {

		// Used by researcher functions
		_where = where;
		_frame = f;
		_login_type = type;

		height = 40;
		width = 160;

		_pid = pid;
		_dob = dob;
		_ins = ins;
		_pol = false;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param where
	 * @param f
	 * @param type
	 * @param loc
	 * @param pid
	 * @param login_typeame
	 * @param lname
	 * @param dob
	 * @param ins
	 * @param string
	 */
	public PatientDBScreen(String where, JFrame f, String type, String loc,
			String pid, String login_typeame, String lname, String dob, String ins,
			String string) {
		_where = where;
		_frame = f;
		_login_type = type;

		height = 40;
		width = 160;

		_pid = pid;
		_login_typeame = login_typeame;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_back = string;
		_pol = true;
		_next = false;
		_backB = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		int action;
		if (arg0.getActionCommand().equals("View, Update, or Delete")) {
			action = 1;

		} else if (arg0.getActionCommand().equals("Add")) {
			action = 2;
		} else if (arg0.getActionCommand().equals("Back")) {
			if (_pol) {
				PolicyDBScreen screen = new PolicyDBScreen(_frame, _login_type, _loc);
				screen.createFrame(1);
			}
			action = 1;

		} else if (arg0.getActionCommand().equals("View or Update")) {
			action = 1;

		} else if (arg0.getActionCommand().equals("View")) {
			action = 1;

		} else {
			action = 3;
		}
		createFrame(action);
	}

	/**
	 * @param action
	 */
	public void createFrame(int action) {
		_frame.getContentPane().removeAll();
		_frame.setTitle("Modify Patient");

		_panel = new ImagePanel();

		_frame.add(_panel);

		addComponents();

		switch (action) {
		case 1:
			if (_pol) {
				PolicyDBScreen screen = new PolicyDBScreen(_frame, _login_type, _loc);
				screen.createFrame(1);

			} else {
				modifyPatient();
			}
			break;
		case 2:
			addPatient();
			break;
		case 3:
			viewPatient();
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

		JLabel pid = new JLabel("Patient ID");
		pid.setBounds(x, y, width, height);
		_panel.add(pid);

		y = y + 50;

		if (!(_login_type.equalsIgnoreCase("researcher"))) {
			JLabel firstname = new JLabel("First Name");
			firstname.setBounds(x, y, width, height);
			_panel.add(firstname);

			y = y + 50;

			JLabel lastname = new JLabel("Last Name");
			lastname.setBounds(x, y, width, height);
			_panel.add(lastname);

			y = y + 50;
		}

		JLabel age = new JLabel("Date of Birth");
		age.setBounds(x, y, width, height);
		_panel.add(age);

		y = y + 50;

		JLabel insurance = new JLabel("Insurance");
		JLabel provider = new JLabel("Provider");
		insurance.setBounds(x, y - 10, width, height);
		_panel.add(insurance);

		provider.setBounds(x, y + 10, width, height);
		_panel.add(provider);

	}

	/**
	 * 
	 */
	public void addPatient() {

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

		JTextField ageText = new JTextField(20);
		ageText.setBounds(x2, y, width, height);
		_panel.add(ageText);

		y = y + 50;

		JComboBox insBox = new JComboBox();
		ArrayList<String> insAgents = Helper.getColumns("Policy_DB",
				"PATIENTS", "INSURANCE_PROVIDER");
		for (int i = 0; i < insAgents.size(); i++) {
			insBox.addItem(insAgents.get(i));
		}
		insBox.setEditable(true);
		insBox.setBounds(x2, y, width, height);
		_panel.add(insBox);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		JButton submit = new JButton("Submit");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new PatientToDBListener(_frame, _login_type, _loc,
				"insert", nameText, lnameText, ageText, insBox, error));
		_frame.getRootPane().setDefaultButton(submit);
		_panel.add(submit);

	}

	/**
	 * 
	 */
	public void modifyPatient() {

		int x = 10;
		int x2 = 180;
		int y = 10;

		JComboBox idb = new JComboBox();

		ArrayList<String> pids = Helper.getColumns("Policy_DB", "PATIENTS",
				"PID");
		for (int i = 0; i < pids.size(); i++) {
			idb.addItem(pids.get(i));
		}
		idb.setEditable(true);
		idb.setBounds(x2, y, width, height);
		idb.setSelectedIndex(0);
		_panel.add(idb);

		y = y + 50;
		JTextField nameText = new JTextField(20);
		JTextField lnameText = new JTextField(20);
		JTextField ageText = new JTextField(20);
		JComboBox insBox = new JComboBox();

		// if ((!(_login_type.equalsIgnoreCase("physician")))
		// && (!(_login_type.equalsIgnoreCase("insurance agent")))) {

		if (!(_login_type.equalsIgnoreCase("researcher"))) {

			nameText.setBounds(x2, y, width, height);
			nameText.setText("");
			nameText.setEditable(true);

			_panel.add(nameText);

			y = y + 50;

			lnameText.setBounds(x2, y, width, height);
			lnameText.setText("");
			lnameText.setEditable(true);

			_panel.add(lnameText);

			y = y + 50;
		}

		ageText.setBounds(x2, y, width, height);
		ageText.setText("");
		_panel.add(ageText);

		y = y + 50;

		ArrayList<String> insAgents = Helper.getColumns("Policy_DB",
				"PATIENTS", "INSURANCE_PROVIDER");
		for (int i = 0; i < insAgents.size(); i++) {
			insBox.addItem(insAgents.get(i));
		}
		insBox.setEditable(true);
		insBox.setBounds(x2, y, width, height);
		insBox.setSelectedIndex(0);
		_panel.add(insBox);

		y = y + 50;

		// }

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		if (_login_type.equalsIgnoreCase("administrator")) {
			JButton submit = new JButton("View");
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new PatientToDBListener(_frame, _login_type, _loc,
					"view", idb, nameText, lnameText, ageText, insBox, error));
			_panel.add(submit);

			JButton submit3 = new JButton("Delete");
			submit3.setBounds(x + 320, y, width, height);
			submit3.addActionListener(new PatientToDBListener(_frame, _login_type,
					_loc, "delete", idb, nameText, lnameText, ageText, insBox,
					error));
			_panel.add(submit3);
		} else if ((_login_type.equalsIgnoreCase("physician"))
				|| (_login_type.equalsIgnoreCase("insurance agent"))) {
			y = y + 200;
			JButton submit = new JButton("View");
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new PatientToDBListener(_frame, _login_type, _loc,
					"view", idb, nameText, lnameText, ageText, insBox, error));
			_frame.getRootPane().setDefaultButton(submit);
			_panel.add(submit);
		} else {
			JButton submit = new JButton("View");
			submit.setBounds(x2, y, width, height);
			submit.addActionListener(new PatientToDBListener(_frame, _login_type, _loc,
					"view", idb, ageText, insBox, error));
			_frame.getRootPane().setDefaultButton(submit);
			_panel.add(submit);
		}

	}

	/**
	 * 
	 */
	public void viewPatient() {

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel pidLabel = new JLabel(_pid);
		pidLabel.setBounds(x2, y, width, height);
		_panel.add(pidLabel);

		y = y + 50;
		JTextField nameText = new JTextField(_login_typeame);
		JTextField lnameText = new JTextField(_lname);
		if (!(_login_type.equalsIgnoreCase("researcher"))) {
			nameText.setBounds(x2, y, width, height);
			nameText.setEditable(true);

			_panel.add(nameText);

			y = y + 50;

			lnameText.setBounds(x2, y, width, height);

			_panel.add(lnameText);

			y = y + 50;
		}

		JTextField ageText = new JTextField(_dob);
		ageText.setBounds(x2, y, width, height);

		_panel.add(ageText);

		y = y + 50;

		JComboBox insBox = new JComboBox();
		ArrayList<String> insAgents = Helper.getColumns("Policy_DB",
				"PATIENTS", "INSURANCE_PROVIDER");
		for (int i = 0; i < insAgents.size(); i++) {
			insBox.addItem(insAgents.get(i));
		}
		insBox.setEditable(true);
		insBox.setSelectedItem(_ins);
		insBox.setBounds(x2, y, width, height);
		_panel.add(insBox);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		if ((_login_type.equalsIgnoreCase("physician"))
				|| (_login_type.equalsIgnoreCase("insurance agent"))) {
			JButton submit = new JButton("Back");
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new PatientDBScreen(_frame, _login_type, _loc,
					_pol));
			if (_login_type.equalsIgnoreCase("insurance agent")) {
				_frame.getRootPane().setDefaultButton(submit);
			}
			_panel.add(submit);

		} else if ((_login_type.equalsIgnoreCase("researcher"))) {
			JButton submit = new JButton("Back");
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new PatientDBScreen(_frame, _login_type, _loc,
					_pol));
			_frame.getRootPane().setDefaultButton(submit);
			_panel.add(submit);

		}

		if ((_login_type.equalsIgnoreCase("physician"))) {
			JButton submit2 = new JButton("Update");
			submit2.setBounds(x + 160, y, width, height);
			if (_pol) {
				submit2.addActionListener(new PatientToDBListener(_frame, _login_type,
						_loc, "update", _pid, nameText, lnameText, ageText,
						insBox, _array, _i, error));
			} else {
				submit2.addActionListener(new PatientToDBListener(_frame, _login_type,
						_loc, "update", _pid, nameText, lnameText, ageText,
						insBox, error));
			}
			_panel.add(submit2);
		} else

		if (_login_type.equalsIgnoreCase("administrator")) {
			JButton submit = new JButton("Back");
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new PatientDBScreen(_frame, _login_type, _loc,
					_pol));
			_panel.add(submit);

			JButton submit1 = new JButton("Update");
			submit1.setBounds(x + 160, y, width, height);
			submit1.addActionListener(new PatientToDBListener(_frame, _login_type,
					_loc, "update", nameText, lnameText, ageText, insBox,
					_where, error));
			_panel.add(submit1);
			JButton submit2 = new JButton("Delete");
			submit2.setBounds(x + 320, y, width, height);
			submit2.addActionListener(new PatientToDBListener(_frame, _login_type,
					_loc, "delete", nameText, lnameText, ageText, insBox,
					_where, error));
			_panel.add(submit2);

		}

		y = y + 50;
		if (_next) {
			JButton next = new JButton("Next Patient");
			next.setBounds(x2, y, width, height);
			next.addActionListener(new PatientToDBListener(_where, _frame, _login_type,
					_loc, "next", _array, _i, _pol, error));
			_panel.add(next);
		}

		if (_backB) {
			JButton back = new JButton("Previous Patient");
			back.setBounds(x, y, width, height);
			int b = _i - 10;
			back.addActionListener(new PatientToDBListener(_where, _frame, _login_type,
					_loc, "next", _array, b, _pol, error));
			_panel.add(back);
		}

	}

}
