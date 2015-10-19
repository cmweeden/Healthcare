package code;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VerifyCredListener implements ActionListener {

	// Common frame for the program
	static JFrame _frame;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// User log-on type (subject)
	static String _fn;

	// User log-on location (community)
	static String _loc;

	JComboBox _pid;
	JComboBox _subject;
	JComboBox _org;
	JCheckBox _read;
	JCheckBox _write;
	String _id;
	boolean whichId;
	boolean whichOrg;

	JLabel _error;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param fn
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param pid
	 * @param read
	 * @param write
	 * @param error
	 */
	public VerifyCredListener(JFrame f, String fn, String loc, JComboBox pid,
			JCheckBox read, JCheckBox write, JLabel error) {
		_pid = pid;
		_loc = loc;
		_read = read;
		_write = write;
		_frame = f;
		_fn = fn;

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
		String where = "";

		String pid = (String) _pid.getSelectedItem();

		boolean read = _read.isSelected();
		boolean write = _write.isSelected();

		where += "P.PID='" + _pid.getSelectedItem() + "' ";

		where += "AND SUBJECT='" + _fn + "' ";

		where += "AND ORGANIZATION='" + _loc + "' ";

		if (read) {
			where += "AND READ='" + read + "' ";
		}
		if (write) {
			where += "AND WRITE='" + write + "' ";
		}

		PolicyDatabase db = new PolicyDatabase();
		ArrayList<String> val = db.select(where, _fn);
		boolean access = Boolean.parseBoolean(val.get(0));
		String xml = val.get(1);
		System.out.println(access);
		System.out.println(xml);
		// String message = "";
		if (access) {
			// message = "ACCESS GRANTED";
			JFrame frame = new JFrame("Selected Policies");
			frame.setSize(500, 250);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			ImagePanel panel = new ImagePanel();

			JTextArea textbox = new JTextArea(xml);
			JScrollPane scroll = new JScrollPane(textbox,
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			scroll.setPreferredSize(new Dimension(550, 400));
			panel.add(scroll);
			frame.add(panel);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.pack();

			PatientDatabase db2 = new PatientDatabase();
			String w = "PID = " + pid;
			ArrayList<String> array2 = db2.view(w);

			if (array2.size() > 0) {
				String f = array2.get(1);
				String l = array2.get(2);
				String dob = array2.get(3);
				String ins = array2.get(4);

				Boolean back = false;
				Boolean next;
				Boolean policy = true;

				if (6 < array2.size()) {
					next = true;
				} else {
					next = false;
				}

				PatientDBScreen screen = new PatientDBScreen(_frame, _fn, _loc,
						pid, f, l, dob, ins, array2, 5, next, back, policy);
				screen.createFrame(3);

			} else {
				_error.setText("No matching patients/policies");
			}

		} else {
			_error.setText("ACCESS DENIED: SEE ADMINISTRATOR FOR PERMISSIONS");
		}

		// JFrame frame2 = new JFrame("Permission");
		// frame2.setSize(350, 250);
		// frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// JPanel panel2 = new JPanel();
		// JTextArea area2 = new JTextArea(message);
		// panel2.add(area2);
		// frame2.add(panel2);
		// frame2.setVisible(true);
		// frame2.setLocationRelativeTo(null);
		// frame2.pack();

		_pid.setSelectedIndex(0);

		_read.setSelected(false);
		_write.setSelected(false);
	}

}
