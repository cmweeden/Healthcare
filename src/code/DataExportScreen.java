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

public class DataExportScreen implements ActionListener {

	// Common frame and panel for the program
	static JFrame _frame;
	ImagePanel _panel;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _fn;

	// User log-on location (community)
	static String _loc;

	static int panely;

	// Patient data
	String _pid;
	String _fname;
	String _lname;
	String _dob;
	String _ins;
	String _where;

	//
	String _back;

	// Array containing patient data
	ArrayList<String> _array;
	int _i;

	// Whether or not there are more than one data to fit the query
	Boolean _next;
	Boolean _backB;

	// Transaction Id to be used for encryption & identification
	int _tid;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community) This is used when first
	 *            entering the screen
	 */
	public DataExportScreen(JFrame f, String fn, String loc) {
		// Used when arriving on this screen for the first time
		// Or opening up a base screen
		_frame = f;
		_fn = fn;
		height = 40;
		width = 160;
		panely = 420;
		_loc = loc;
		_next = false;
		_backB = false;

	}

	// TODO Need to verify that all of these are being used

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance This is used by researchers entering the
	 *            screen after looking up data
	 */
	public DataExportScreen(JFrame f, String fn, String loc, String pid,
			String dob, String ins) {

		_frame = f;
		_fn = fn;
		_pid = pid;
		_loc = loc;
		_dob = dob;
		_ins = ins;
		height = 40;
		width = 160;
		panely = 420;
		_next = false;
		_backB = false;
	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param fname
	 *            patient first name
	 * @param lname
	 *            patient last name
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance This is used by anyone else entering the
	 *            screen after looking up data
	 */
	public DataExportScreen(JFrame f, String fn, String loc, String pid,
			String fname, String lname, String dob, String ins) {

		_frame = f;
		_fn = fn;
		_loc = loc;

		height = 40;
		width = 160;
		panely = 420;

		_pid = pid;
		_fname = fname;
		_lname = lname;
		_dob = dob;
		_ins = ins;

		_next = false;
		_backB = false;
	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param fname
	 *            patient first name
	 * @param lname
	 *            patient last name
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance
	 * @param array
	 *            array containing more patient data
	 * @param i
	 *            the index of the (current or next?) data
	 * @param n
	 *            a boolean telling whether there is more data
	 * @param b
	 *            boolean telling whether this is the first data in the set This
	 *            is used by anyone else entering the screen after looking up
	 *            data
	 */
	public DataExportScreen(JFrame f, String fn, String loc, String pid,
			String fname, String lname, String dob, String ins,
			ArrayList<String> array, int i, Boolean n, Boolean b) {

		_frame = f;
		_fn = fn;
		_loc = loc;

		height = 40;
		width = 160;
		panely = 420;

		_pid = pid;
		_fname = fname;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param where
	 *            The search string used to find the data
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param fname
	 *            patient first name
	 * @param lname
	 *            patient last name
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance
	 */
	public DataExportScreen(String where, JFrame f, String fn, String loc,
			String pid, String fname, String lname, String dob, String ins) {

		_where = where;
		_frame = f;
		_fn = fn;
		_loc = loc;

		height = 40;
		width = 160;
		panely = 420;

		_pid = pid;
		_fname = fname;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_next = false;
		_backB = false;
	}

	/**
	 * @param where
	 *            The search string used to find the data
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param fname
	 *            patient first name
	 * @param lname
	 *            patient last name
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance
	 * @param array
	 *            array containing more patient data
	 * @param i
	 *            the index of the (current or next?) data
	 * @param n
	 *            a boolean telling whether there is more data
	 * @param b
	 *            boolean telling whether this is the first data in the set
	 */
	public DataExportScreen(String where, JFrame f, String fn, String loc,
			String pid, String fname, String lname, String dob, String ins,
			ArrayList<String> array, int i, Boolean n, Boolean b) {

		// Used when multiple patient data matches for admin, physician, and
		// insurance
		_where = where;
		_frame = f;
		_fn = fn;
		_loc = loc;
		height = 40;
		width = 160;
		panely = 420;

		_pid = pid;
		_fname = fname;
		_lname = lname;
		_dob = dob;
		_ins = ins;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param where
	 *            The search string used to find the data
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 *            patient id
	 * @param dob
	 *            patient dob
	 * @param ins
	 *            patient insurance
	 * @param array
	 *            array containing more patient data
	 * @param i
	 *            the index of the (current or next?) data
	 * @param n
	 *            a boolean telling whether there is more data
	 * @param b
	 *            boolean telling whether this is the first data in the set
	 */
	public DataExportScreen(String where, JFrame f, String fn, String loc,
			String pid, String dob, String ins, ArrayList<String> array, int i,
			Boolean n, Boolean b) {

		// Used when multiple patient data matches for researcher
		_where = where;
		_frame = f;
		_fn = fn;
		_loc = loc;
		height = 40;
		width = 160;
		panely = 420;

		_pid = pid;
		_dob = dob;
		_ins = ins;
		_array = array;
		_i = i;
		_next = n;
		_backB = b;
	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param tid
	 *            The encryption id used for encryption algorithm
	 */
	public DataExportScreen(JFrame f, String fn, String loc, int tid) {
		// Used when returning from an export
		_frame = f;
		_fn = fn;
		height = 40;
		width = 160;
		panely = 420;
		_loc = loc;
		_next = false;
		_backB = false;
		_tid = tid;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Determines which button was pressed to choose function
	 */
	public void actionPerformed(ActionEvent arg0) {
		int action;
		if (arg0.getActionCommand().equals("Export Data")) {
			action = 1;

		} else if (arg0.getActionCommand().equals("Back")) {
			action = 1;
		} else if (arg0.getActionCommand().equals("view")) {
			action = 2;
		} else {
			action = 0;
			// Invalid action
		}
		createFrame(action);
	}

	/**
	 * @param action
	 *            Creates the screen based on the function that was chosen
	 */
	public void createFrame(int action) {
		_frame.getContentPane().removeAll();
		_frame.setTitle("Export Patient Data");

		_panel = new ImagePanel();

		_panel.setLayout(new FlowLayout());
		_frame.add(_panel);

		switch (action) {
		case 0:
			// Show something about not a valid screen
			break;
		case 1:
			addComponents();
			choosePatient();
			break;
		case 2:
			addComponents();
			viewPatient();
			break;
		case 3:
			displayTID();
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

		if (!(_fn.equals("researcher"))) {
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
	public void choosePatient() {

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
		_panel.add(idb);

		y = y + 50;
		JTextField nameText = new JTextField(20);
		JTextField lnameText = new JTextField(20);
		JTextField ageText = new JTextField(20);
		JComboBox insBox = new JComboBox();

		if ((!(_fn.equals("physician"))) && (!(_fn.equals("insurance")))) {

			if (!(_fn.equals("researcher"))) {

				nameText.setBounds(x2, y, width, height);

				nameText.setEditable(true);

				_panel.add(nameText);

				y = y + 50;

				lnameText.setBounds(x2, y, width, height);

				lnameText.setEditable(true);

				_panel.add(lnameText);

				y = y + 50;
			}

			ageText.setBounds(x2, y, width, height);
			_panel.add(ageText);

			y = y + 50;

			ArrayList<String> insAgents = Helper.getColumns("Policy_DB",
					"PATIENTS", "INSURANCE_PROVIDER");
			for (int i = 0; i < insAgents.size(); i++) {
				insBox.addItem(insAgents.get(i));
			}
			insBox.setEditable(true);
			insBox.setBounds(x2, y, width, height);
			_panel.add(insBox);

			y = y + 50;

		}

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		JButton submit = new JButton("View");
		if (_fn.equals("researcher")) {

			submit.setBounds(x, y, width, height);
			submit.addActionListener(new HIEDBListener(_frame, _fn, _loc,
					"view", idb, ageText, insBox, error));
			_panel.add(submit);

		} else {
			submit.setBounds(x, y, width, height);
			submit.addActionListener(new HIEDBListener(_frame, _fn, _loc,
					"view", idb, nameText, lnameText, ageText, insBox, error));
			_panel.add(submit);
		}
		_frame.getRootPane().setDefaultButton(submit);

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
		JTextField nameText = new JTextField(_fname);
		JTextField lnameText = new JTextField(_lname);
		if (!(_fn.equals("researcher"))) {
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
		error.setBounds(x2, y + 80, width * 3, height);
		_panel.add(error);

		if (_next) {
			JButton next = new JButton("Next Patient");
			next.setBounds(x2, y, width, height);
			next.addActionListener(new HIEDBListener(_where, _frame, _fn, _loc,
					"next", _array, _i, error));
			_panel.add(next);
		}

		if (_backB) {
			JButton back = new JButton("Previous Patient");
			back.setBounds(x, y, width, height);
			int b = _i - 10;
			back.addActionListener(new HIEDBListener(_where, _frame, _fn, _loc,
					"next", _array, b, error));
			_panel.add(back);
		}
		y = y + 50;

		JButton submit = new JButton("Back");
		submit.setBounds(x, y, width, height);
		submit.addActionListener(new DataExportScreen(_frame, _fn, _loc));
		_panel.add(submit);

		JButton export = new JButton("Export Patient Data");
		export.setBounds(x2, y, width, height);
		export.addActionListener(new HIEDBListener(_frame, _fn, _loc, "export",
				_pid, nameText, lnameText, ageText, insBox, error));
		_frame.getRootPane().setDefaultButton(export);
		_panel.add(export);

	}

	/**
	 * 
	 */
	public void displayTID() {

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel pid = new JLabel("Transaction ID:");
		pid.setBounds(x, y, width, height);
		_panel.add(pid);

		JLabel pidLabel = new JLabel(Integer.toString(_tid));
		pidLabel.setBounds(x2, y, width, height);
		_panel.add(pidLabel);

		y = y + 50;

		JButton submit = new JButton("Back");
		submit.setBounds(x, y, width, height);
		submit.addActionListener(new DataExportScreen(_frame, _fn, _loc));
		_frame.getRootPane().setDefaultButton(submit);
		_panel.add(submit);

	}

}
