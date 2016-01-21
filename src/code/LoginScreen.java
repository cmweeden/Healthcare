package code;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class LoginScreen {

	// Common frame to create for the program
	static JFrame _frame;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	/**
	 * Launches program. This creates a frame which displays a login screen for
	 * the program.
	 * 
	 * @param args
	 *            Empty arguments for the usage of the current program.
	 */
	public static void main(String[] args) {

		// TODO Allow frame to size based on user screen
		// Will need to change a lot of UI elements throughout program for this.

		setUIFont(new FontUIResource(new Font("Arial", 0, 20)));

		_frame = new JFrame("Login");
		_frame.setSize(500, 550);
		_frame.setResizable(true);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		height = 40;
		width = 160;

		ImagePanel pane = new ImagePanel();

		placeComponents(pane);

		_frame.add(pane);
		_frame.setVisible(true);
		_frame.setLocationRelativeTo(null);

	}

	/**
	 * Allows modification of and setting font for entire program
	 * 
	 * @param f
	 */
	@SuppressWarnings("rawtypes")
	public static void setUIFont(FontUIResource f) {
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
				Font font = new Font(f.getFontName(), orig.getStyle(),
						f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}

	/**
	 * Arranges the elements on the login screen
	 * 
	 * @param panel
	 *            The panel on which to place the elements
	 */
	public static void placeComponents(ImagePanel panel) {
		panel.setLayout(null);

		int x = 10;
		int x2 = 180;
		int y = 10;

		JLabel loc = new JLabel("Login Location");
		loc.setBounds(x, y, width, height);
		panel.add(loc);

		JComboBox ldb = new JComboBox();
		ArrayList<String> type = Helper.getColumns("Policy_DB", "LOCATIONS",
				"LOCATION");
		for (int i = 0; i < type.size(); i++) {
			ldb.addItem(type.get(i));
		}
		JComboBox idb = new JComboBox();
		ldb.addActionListener((ActionListener) new LocationComboBoxListener(idb, panel));
		ldb.setBounds(x2, y, width + 50, height);
		panel.add(ldb);

		y = y + 50;

		JLabel uType = new JLabel("User Type");
		uType.setBounds(x, y, width, height);
		panel.add(uType);

		/*ArrayList<String> type2 = Helper.getColumns("Policy_DB", "TYPES",
				"TYPE");
		for (int i = 0; i < type2.size(); i++) {
			idb.addItem(type2.get(i));
		}
		*/idb.setBounds(x2, y, width + 50, height);
		panel.add(idb);

		y = y + 50;

		JLabel username = new JLabel("User Name");
		username.setBounds(x, y, width, height);
		panel.add(username);

		JTextField nameText = new JTextField(20);
		nameText.setBounds(x2, y, width, height);
		nameText.setEditable(true);
		panel.add(nameText);

		y = y + 50;

		JLabel password = new JLabel("Pass Phrase");
		password.setBounds(x, y, width, height);
		panel.add(password);

		JPasswordField pText = new JPasswordField(20);

		pText.setBounds(x2, y, width, height);
		panel.add(pText);

		y = y + 50;

		JLabel error = new JLabel("");
		error.setBounds(x2, y + 30, width, height);
		panel.add(error);

		JButton submit = new JButton("Login");
		submit.setBounds(x2, y, width, height);
		submit.addActionListener(new LoginListener(ldb, idb, nameText,
				(JTextField) pText, _frame, error));
		_frame.getRootPane().setDefaultButton(submit);
		panel.add(submit);

	}

}
