package code;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DataImportScreen implements ActionListener {

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

	// Return from transaction selection
	String _did; // Transaction ID
	String _pt; // Text to display

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 */
	public DataImportScreen(JFrame f, String login_type, String loc) {
		// Base Import Screen

		height = 40;
		width = 160;

		_frame = f;
		_login_type = login_type;
		_loc = loc;

	}

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param did
	 *            Decryption id == transaction id
	 * @param pt
	 *            Text to display
	 */
	public DataImportScreen(JFrame f, String login_type, String loc, String did,
			String pt) {
		// Displaying results of import

		height = 40;
		width = 160;

		_frame = f;
		_login_type = login_type;
		_loc = loc;

		_did = did;
		_pt = pt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		int action;
		if (arg0.getActionCommand().equals("View Imported Data")) {
			action = 1;
		} else if (arg0.getActionCommand().equals("Back")) {
			action = 1;
		} else {
			action = 0;
			// Invalid action
		}
		createFrame(action);
	}

	/**
	 * @param action
	 */
	public void createFrame(int action) {
		_frame.getContentPane().removeAll();
		_frame.setTitle("View Imported Data");

		_panel = new ImagePanel();

		_frame.add(_panel);
		addComponents();

		switch (action) {
		case 0:
			// TODO Show something about not a valid screen
			break;
		case 1:
			choosePatient();
			break;
		case 2:
			viewData();
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

		JLabel pid = new JLabel("Import ID");
		pid.setBounds(x, y, width, height);
		_panel.add(pid);

	}

	/**
	 * 
	 */
	public void choosePatient() {

		int x2 = 180;
		int y = 10;

		JComboBox idb = new JComboBox();

		ArrayList<String> pids = Helper.getColumns("Policy_DB", "HIE", "ID");
		for (int i = 0; i < pids.size(); i++) {
			idb.addItem(pids.get(i));
		}
		idb.setEditable(true);
		idb.setBounds(x2, y, width, height);
		_panel.add(idb);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width * 3, height);
		_panel.add(error);

		JButton submit = new JButton("View");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new HIEDBListener(_frame, _login_type, _loc, "import",
				idb, error));
		if (!(_login_type.equalsIgnoreCase("Administrator"))) {
			_frame.getRootPane().setDefaultButton(submit);
		}
		_panel.add(submit);
		if ((_login_type.equalsIgnoreCase("Administrator"))) {
			y = y + 50;
			JButton submit2 = new JButton("Delete");
			submit2.setBounds(x2, y, width, height);
			submit2.addActionListener(new HIEDBListener(_frame, _login_type, _loc,
					"delete", idb, error));
			_panel.add(submit2);
		}
	}

	/**
	 * 
	 */
	public void viewData() {

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel did = new JLabel(_did);
		did.setBounds(x2, y, width, height);
		_panel.add(did);

		y = y + 50;

		JLabel dlabel = new JLabel("Data:");
		dlabel.setBounds(x, y, width, height);
		_panel.add(dlabel);

		y = y + 50;

		JTextArea data = new JTextArea(_pt);
		// data.setLineWrap(true);
		// data.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(data,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(490, 250));
		scroll.setBounds(x, y, 490, 250);
		data.setWrapStyleWord(true);
		data.setLineWrap(true);
		data.setEditable(false);
		_panel.add(scroll);

		y = y + 280;

		JButton submit = new JButton("Back");
		submit.setBounds(x, y, width, height);
		submit.addActionListener(new DataImportScreen(_frame, _login_type, _loc));
		_frame.getRootPane().setDefaultButton(submit);
		_panel.add(submit);

	}

}
