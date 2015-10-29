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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PolicyDBScreen implements ActionListener {

	// Common frame and panel for the program
	static JFrame _frame;
	ImagePanel _panel;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on login_type (subject)
	static String _login_login_type;

	// User log-on location (community)
	static String _loc;

	static int panely;
	String _pid;
	String _subj;
	String _org;
	boolean _r;
	boolean _w;
	boolean which;
	String _where;

	ArrayList<String> _array;
	int _i;
	boolean _next;
	boolean _back;

	boolean _tool1;
	boolean _tool2;
	boolean _rule;
	boolean _resp1;
	boolean _resp2;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on login_type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 */
	public PolicyDBScreen(JFrame f, String login_type, String loc) {
		_frame = f;
		_login_login_type = login_type;
		_loc = loc;
		height = 40;
		width = 160;
		panely = 420;

	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param pid
	 * @param subj
	 * @param org
	 * @param r
	 * @param w
	 */
	public PolicyDBScreen(JFrame f, String login_type, String loc, String pid,
			String subj, String org, boolean r, boolean w) {
		_frame = f;
		_pid = pid;
		_subj = subj;
		_org = org;
		_r = r;
		_w = w;
		_login_login_type = login_type;
		height = 40;
		width = 160;
		panely = 420;
		which = true;
		_loc = loc;
	}

	/**
	 * @param frame
	 * @param login_type
	 * @param loc
	 * @param pid2
	 * @param sub
	 * @param org2
	 * @param read
	 * @param write
	 */
	public PolicyDBScreen(JFrame frame, String login_type, String loc, String pid2,
			String sub, String org2, String read, String write) {
		_frame = frame;
		_login_login_type = login_type;
		_pid = pid2;
		_subj = sub;
		_org = org2;
		height = 40;
		width = 160;
		panely = 420;
		which = false;
		_loc = loc;
		if (read.equals("true")) {
			_r = true;
		} else {
			_r = false;
		}

		if (write.equals("true")) {
			_w = true;
		} else {
			_w = false;
		}

	}

	/**
	 * @param addWhere
	 * @param _frame2
	 * @param login_type
	 * @param loc
	 * @param pid2
	 * @param sub
	 * @param org2
	 * @param read
	 * @param write
	 * @param tool
	 * @param rule
	 * @param resp
	 * @param array
	 * @param i
	 * @param next
	 * @param back
	 */
	public PolicyDBScreen(String addWhere, JFrame _frame2, String login_type,
			String loc, String pid2, String sub, String org2, String read,
			String write, String tool, String rule, String resp,
			ArrayList<String> array, int i, boolean next, boolean back) {

		_where = addWhere;

		_login_login_type = login_type;
		_loc = loc;
		height = 40;
		width = 160;
		panely = 420;
		which = false;

		_frame = _frame2;
		_pid = pid2;
		_subj = sub;
		_org = org2;

		if (tool.indexOf("E-mail") != -1) {
			_tool1 = true;
		} else {
			_tool1 = false;
		}

		if (tool.indexOf("Call") != -1) {
			_tool2 = true;
		} else {
			_tool2 = false;
		}

		if (rule.indexOf("E-mail") != -1) {
			_rule = true;
		} else {
			_rule = false;
		}
		if (resp.indexOf("Administrator") != -1) {
			_resp1 = true;
		} else {
			_resp1 = false;
		}
		if (resp.indexOf("Patient") != -1) {
			_resp2 = true;
		} else {
			_resp2 = false;
		}

		// TODO Change separate read and write in policy to be similar to
		// tool/rule/resp above
		if (read.equals("true")) {
			_r = true;
		} else {
			_r = false;
		}

		if (write.equals("true")) {
			_w = true;
		} else {
			_w = false;
		}

		_array = array;
		_i = i;
		_next = next;
		_back = back;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int action;
		if (arg0.getActionCommand().equals("View, Update, or Delete")) {
			action = 1;

		} else if (arg0.getActionCommand().equals("Add")) {
			action = 2;
		} else if (arg0.getActionCommand().equals("Back")) {
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
		_frame.setTitle("Add Policy");

		_panel = new ImagePanel();

		_panel.setLayout(new FlowLayout());
		_frame.add(_panel);
		addComponents();

		switch (action) {
		case 1:
			modifyPolicy();
			break;
		case 2:
			addPolicy();
			break;
		case 3:
			viewPolicy();
			break;
		}

		_frame.setVisible(true);
	}

	/**
	 * 
	 */
	public void addComponents() {
		// subject, action, resource, environment
		_panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel id = new JLabel("Object-PatientID");
		id.setBounds(x, y, width, height);
		_panel.add(id);

		y = y + 50;

		JLabel subject = new JLabel("Subject");
		subject.setBounds(x, y, width, height);
		_panel.add(subject);

		y = y + 50;

		JLabel org = new JLabel("Community");
		org.setBounds(x, y, width, height);
		_panel.add(org);

		y = y + 50;

		JLabel act = new JLabel("Action");
		act.setBounds(x, y, width, height);
		_panel.add(act);

		_panel.add(new JSeparator(SwingConstants.HORIZONTAL));

		y = y + 50;

		JLabel tool = new JLabel("Tool");
		tool.setBounds(x, y, width, height);
		_panel.add(tool);

		y = y + 50;

		JLabel rule = new JLabel("Rule");
		rule.setBounds(x, y, width, height);
		_panel.add(rule);

		y = y + 50;

		JLabel resp = new JLabel("Responsibility");
		resp.setBounds(x, y, width, height);
		_panel.add(resp);

	}

	/**
	 * 
	 */
	public void addPolicy() {
		// subject, action, resource, environment
		_panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JComboBox idb = new JComboBox();
		ArrayList<String> pids = Helper.getColumns("Policy_DB", "PATIENTS",
				"PID");
		for (int i = 0; i < pids.size(); i++) {
			idb.addItem(pids.get(i));
		}
		idb.setBounds(x2, y, width * 2, height);
		_panel.add(idb);

		y = y + 50;

		// To Do:
		JComboBox sdb = new JComboBox();
		ArrayList<String> sids = Helper
				.getColumns("Policy_DB", "USERS", "login_type");
		for (int i = 0; i < sids.size(); i++) {
			sdb.addItem(sids.get(i));
		}
		sdb.setBounds(x2, y, width * 2, height);
		_panel.add(sdb);

		y = y + 50;

		JComboBox orgb = new JComboBox();
		ArrayList<String> orgs = Helper.getColumns("Policy_DB", "LOCATIONS",
				"LOCATION");
		for (int i = 0; i < orgs.size(); i++) {
			orgb.addItem(orgs.get(i));
		}
		orgb.setBounds(x2, y, width * 2, height);
		_panel.add(orgb);

		y = y + 50;

		JCheckBox read = new JCheckBox("Read");
		read.setBounds(x2, y, width, height);
		read.setSelected(false);
		_panel.add(read);

		y = y + 20;

		JCheckBox write = new JCheckBox("Write");
		write.setBounds(x2, y, width, height);
		_panel.add(write);

		y = y + 30;

		JCheckBox tool1 = new JCheckBox("E-mail");
		tool1.setBounds(x2, y, width, height);
		tool1.setSelected(false);
		_panel.add(tool1);

		y = y + 20;

		JCheckBox tool2 = new JCheckBox("Call");
		tool2.setBounds(x2, y, width, height);
		tool2.setSelected(false);
		_panel.add(tool2);

		y = y + 30;

		JCheckBox rule1 = new JCheckBox("Mismatch");
		rule1.setBounds(x2, y, width, height);
		rule1.setSelected(true);
		_panel.add(rule1);

		y = y + 50;

		JCheckBox resp1 = new JCheckBox("Admin");
		resp1.setBounds(x2, y, width, height);
		resp1.setSelected(false);
		_panel.add(resp1);

		y = y + 20;

		JCheckBox resp2 = new JCheckBox("Patient");
		resp2.setBounds(x2, y, width, height);
		resp2.setSelected(false);
		_panel.add(resp2);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		JButton submit = new JButton("Submit");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new PolicyToDBListener(_frame, _login_login_type, _loc,
				"insert", idb, sdb, orgb, read, write, tool1, tool2, rule1,
				resp1, resp2, error));
		_frame.getRootPane().setDefaultButton(submit);
		_panel.add(submit);

	}

	/**
	 * 
	 */
	public void modifyPolicy() {

		_panel.setLayout(null);

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

		// TODO

		JComboBox subjectb = new JComboBox();
		if (!(_login_login_type.equalsIgnoreCase("physician"))
				&& !(_login_login_type.equalsIgnoreCase("insurance agent"))) {
			ArrayList<String> subjects = Helper.getColumns("Policy_DB",
					"USERS", "ID");
			for (int i = 0; i < subjects.size(); i++) {
				subjectb.addItem(subjects.get(i));
			}
			subjectb.setBounds(x2, y, width * 2, height);
			_panel.add(subjectb);
		} else {
			JLabel subj = new JLabel(_login_login_type);
			subj.setBounds(x2, y, width * 2, height);
			_panel.add(subj);
		}
		y = y + 50;

		JComboBox orgb = new JComboBox();
		if (!(_login_login_type.equalsIgnoreCase("physician"))
				&& !(_login_login_type.equalsIgnoreCase("insurance agent"))) {
			ArrayList<String> orgs = Helper.getColumns("Policy_DB",
					"LOCATIONS", "LOCATION");
			for (int i = 0; i < orgs.size(); i++) {
				orgb.addItem(orgs.get(i));
			}
			orgb.setBounds(x2, y, width * 2, height);
			_panel.add(orgb);
		} else {
			JLabel loc = new JLabel(_loc);
			loc.setBounds(x2, y, width * 2, height);
			_panel.add(loc);
		}
		y = y + 50;

		JCheckBox read = new JCheckBox("Read");
		read.setBounds(x2, y, width, height);
		read.setSelected(false);
		_panel.add(read);

		y = y + 20;

		JCheckBox write = new JCheckBox("Write");
		write.setBounds(x2, y, width, height);
		write.setSelected(false);
		_panel.add(write);

		y = y + 30;

		JCheckBox tool1 = new JCheckBox("E-mail");
		tool1.setBounds(x2, y, width, height);
		tool1.setSelected(false);
		_panel.add(tool1);

		y = y + 20;

		JCheckBox tool2 = new JCheckBox("Call");
		tool2.setBounds(x2, y, width, height);
		tool2.setSelected(false);
		_panel.add(tool2);

		y = y + 30;

		JCheckBox rule1 = new JCheckBox("Mismatch");
		rule1.setBounds(x2, y, width, height);
		rule1.setSelected(true);
		_panel.add(rule1);

		y = y + 50;

		JCheckBox resp1 = new JCheckBox("Admin");
		resp1.setBounds(x2, y, width, height);
		resp1.setSelected(false);
		_panel.add(resp1);

		y = y + 20;

		JCheckBox resp2 = new JCheckBox("Patient");
		resp2.setBounds(x2, y, width, height);
		resp2.setSelected(false);
		_panel.add(resp2);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		if (_login_login_type.equalsIgnoreCase("administrator")) {
			JButton submit1 = new JButton("View");
			submit1.setBounds(x, y, width, height);
			submit1.addActionListener(new PolicyToDBListener(_frame, _login_login_type, _loc,
					"view", idb, subjectb, orgb, read, write, tool1, tool2,
					rule1, resp1, resp2, error));
			_panel.add(submit1);
			JButton submit3 = new JButton("Delete");
			submit3.setBounds(x + 320, y, width, height);
			submit3.addActionListener(new PolicyToDBListener(_frame, _login_login_type, _loc,
					"delete", idb, subjectb, orgb, read, write, tool1, tool2,
					rule1, resp1, resp2, error));
			_panel.add(submit3);
		} else if ((_login_login_type.equalsIgnoreCase("physician"))
				|| (_login_login_type.equalsIgnoreCase("insurance agent"))) {
			JButton submit1 = new JButton("View");
			submit1.setBounds(x, y, width, height);
			submit1.addActionListener(new PolicyToDBListener(_frame, _login_login_type, _loc,
					"view", idb, read, write, tool1, tool2, rule1, resp1,
					resp2, error));
			_frame.getRootPane().setDefaultButton(submit1);
			_panel.add(submit1);
		} else {
			JButton submit1 = new JButton("View");
			submit1.setBounds(x2, y, width, height);
			submit1.addActionListener(new PolicyToDBListener(_frame, _login_login_type, _loc,
					"view", idb, subjectb, orgb, read, write, tool1, tool2,
					rule1, resp1, resp2, error));
			_frame.getRootPane().setDefaultButton(submit1);
			_panel.add(submit1);
		}

	}

	/**
	 * 
	 */
	public void viewPolicy() {

		_panel.setLayout(null);

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

		idb.setSelectedItem(_pid);

		idb.setBounds(x2, y, width * 2, height);
		_panel.add(idb);

		y = y + 50;

		JComboBox subjectb = new JComboBox();
		ArrayList<String> subjects = Helper.getColumns("Policy_DB", "USERS",
				"ID");
		for (int i = 0; i < subjects.size(); i++) {
			subjectb.addItem(subjects.get(i));
		}
		subjectb.setEditable(true);
		subjectb.setSelectedItem(_subj);
		subjectb.setBounds(x2, y, width * 2, height);
		_panel.add(subjectb);

		y = y + 50;

		JComboBox orgb = new JComboBox();
		ArrayList<String> orgs = Helper.getColumns("Policy_DB", "LOCATIONS",
				"LOCATION");
		for (int i = 0; i < orgs.size(); i++) {
			orgb.addItem(orgs.get(i));
		}
		orgb.setEditable(true);
		orgb.setSelectedItem(_org);
		orgb.setBounds(x2, y, width * 2, height);
		_panel.add(orgb);

		y = y + 50;

		JCheckBox read = new JCheckBox("Read");
		read.setBounds(x2, y, width, height);
		read.setSelected(_r);
		_panel.add(read);

		y = y + 20;

		JCheckBox write = new JCheckBox("Write");
		write.setBounds(x2, y, width, height);
		write.setSelected(_w);
		_panel.add(write);

		y = y + 30;

		JCheckBox tool1 = new JCheckBox("E-mail");
		tool1.setBounds(x2, y, width, height);
		tool1.setSelected(_tool1);
		_panel.add(tool1);

		y = y + 20;

		JCheckBox tool2 = new JCheckBox("Call");
		tool2.setBounds(x2, y, width, height);
		tool2.setSelected(_tool2);
		_panel.add(tool2);

		y = y + 30;

		JCheckBox rule1 = new JCheckBox("Mismatch");
		rule1.setBounds(x2, y, width, height);
		rule1.setSelected(_rule);
		_panel.add(rule1);

		y = y + 50;

		JCheckBox resp1 = new JCheckBox("Admin");
		resp1.setBounds(x2, y, width, height);
		resp1.setSelected(_resp1);
		_panel.add(resp1);

		y = y + 20;

		JCheckBox resp2 = new JCheckBox("Patient");
		resp2.setBounds(x2, y, width, height);
		resp2.setSelected(_resp2);
		_panel.add(resp2);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		JButton submit1 = new JButton("Back");
		submit1.setBounds(x, y, width, height);
		submit1.addActionListener(new PolicyDBScreen(_frame, _login_login_type, _loc));
		_panel.add(submit1);

		if (_login_login_type.equalsIgnoreCase("administrator")) {
			JButton submit2 = new JButton("Update");
			submit2.setBounds(x + 160, y, width, height);
			submit2.addActionListener(new PolicyToDBListener(_where, _frame,
					_login_login_type, _loc, "update", idb, subjectb, orgb, read, write,
					tool1, tool2, rule1, resp1, resp2, error));
			_panel.add(submit2);

			JButton submit3 = new JButton("Delete");
			submit3.setBounds(x + 320, y, width, height);
			submit3.addActionListener(new PolicyToDBListener(_where, _frame,
					_login_login_type, _loc, "delete", idb, subjectb, orgb, read, write,
					tool1, tool2, rule1, resp1, resp2, error));
			_panel.add(submit3);

			y = y + 50;
			if (_next) {
				JButton next = new JButton("Next Policy");
				next.setBounds(x2, y, width, height);
				next.addActionListener(new PolicyToDBListener(_where, _frame,
						_login_login_type, _loc, "next", _array, _i, error));
				_panel.add(next);
			}

			if (_back) {
				JButton back = new JButton("Previous Policy");
				back.setBounds(x, y, width, height);
				int b = _i - 16;
				// System.out.println(b);
				back.addActionListener(new PolicyToDBListener(_where, _frame,
						_login_login_type, _loc, "next", _array, b, error));
				_panel.add(back);
			}
		}

	}

}
