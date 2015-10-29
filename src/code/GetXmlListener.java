package code;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GetXmlListener implements ActionListener {

	JComboBox _pid;
	JComboBox _subject;
	JComboBox _org;
	JCheckBox _read;
	JCheckBox _write;
	String _login_type;

	/**
	 * @param pid
	 * @param subject
	 * @param org
	 * @param read
	 * @param write
	 * @param login_type
	 */
	public GetXmlListener(JComboBox pid, JComboBox subject, JComboBox org,
			JCheckBox read, JCheckBox write, String login_type) {
		_pid = pid;
		_subject = subject;
		_org = org;
		_read = read;
		_write = write;
		_login_type = login_type;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String where = "";
		boolean ifPID = false;
		if (_pid != null) {
			String pid = (String) _pid.getSelectedItem();
			if (_pid.getSelectedIndex() != 0) {
				where += "P.PID='" + pid + "' ";
				ifPID = true;
			}
		}
		boolean ifSub = false;
		if (_subject != null) {
			String subject = (String) _subject.getSelectedItem();
			if (_subject.getSelectedIndex() != 0) {
				if (ifPID) {
					where += "AND ";

				}
				where += "SUBJECT='" + subject + "' ";
				ifSub = true;

			}
		}
		if (_org != null) {
			String org = (String) _org.getSelectedItem();

			if (_org.getSelectedIndex() != 0) {
				if (ifPID || ifSub) {
					where += "AND ";
				}
				where += "ORGANIZATION='" + org + "' ";
			}
		}

		PolicyDatabase db = new PolicyDatabase();
		ArrayList<String> val = db.select(where, _login_type);
		String xml = val.get(1);

		JFrame frame = new JFrame("Selected Policies");
		frame.setSize(350, 250);
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
		frame.pack();

		if (_pid != null) {
			_pid.setSelectedIndex(0);
		}

		if (_subject != null) {

		}
		if (_org != null) {
			_org.setSelectedIndex(0);
		}

		if (_read != null) {
			_read.setSelected(false);
		}
		if (_write != null) {
			_write.setSelected(false);
		}

	}

}
