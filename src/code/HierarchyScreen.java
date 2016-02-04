package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HierarchyScreen implements ActionListener {
	static JFrame _frame;
	ImagePanel _panel;
	
	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;
		
	// User log-on type (subject)
	static String _login_type;
	// User log-on location (community)
	static String _location;
	public HierarchyScreen(JFrame f, String type, String loc){
		_frame = f;
		_login_type = type;
		height = 40;
		width = 160;
		_location = loc;
	}
	public void actionPerformed(ActionEvent e) {
		createFrame();
		
	}
	public void createFrame(){
		_frame.getContentPane().removeAll();
		_frame.setTitle("Hierarchy Assignment");
		_panel = new ImagePanel();

		_frame.add(_panel);
		addComponents();
		_frame.setVisible(true);
	}
	public void addComponents(){
		_panel.setLayout(null);
		ArrayList<String> pids = Helper.getColumns("Policy_DB", "LOCATIONS", "LOCATION");
		int x = 10;
		int y = 10;
		JLabel facility = new JLabel(_location);
		JComboBox facilityBox = new JComboBox();
		facility.setBounds(x+125, y, width, height);
		ArrayList<JComboBox> logintypeBoxArray = new ArrayList<JComboBox>();
		ArrayList<String> selectedTable = Helper.getColumns("Policy_DB", _location.replaceAll("\\s",""), "Position");
		//selectedTable.remove(0);
		for (int i=0; i<selectedTable.size()-1; i++){
			logintypeBoxArray.add(new JComboBox());
			for (int j=0; j<selectedTable.size(); j++){
				logintypeBoxArray.get(i).addItem(selectedTable.get(j));
			}
			logintypeBoxArray.get(i).setBounds(x+105, y+30+i*30, width, height);
			JLabel logintype = new JLabel("Rank"+" "+(i+1));
			logintype.setBounds(x, y+30+i*30, width, height);
			_panel.add(logintype);
			_panel.add(logintypeBoxArray.get(i));
		}
		_panel.add(facility);
		_panel.add(facilityBox);
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitHierarchyListener(logintypeBoxArray, _location));
		submitButton.setBounds(x+50, y+30+30*4+30, width, height);
		_panel.add(submitButton);
	}
}
