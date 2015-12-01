package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	static String _loc;
	public HierarchyScreen(JFrame f, String type, String loc){
		_frame = f;
		_login_type = type;
		height = 40;
		width = 160;
		_loc = loc;
		//_pol = false;
		//_next = false;
		//_backB = false;
	}
	public void actionPerformed(ActionEvent e) {
		createFrame();
		
	}
	public void createFrame(){
		_frame.getContentPane().removeAll();
		_frame.setTitle("Create Mappings");
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
		JLabel facility = new JLabel("Facility");
		JComboBox facilityBox = new JComboBox();
		for (int j=0; j<pids.size(); j++){
			facilityBox.addItem(pids.get(j));
		}
		facility.setBounds(x, y, width, height);
		facilityBox.setBounds(x+105, y, width, height);
		for (int i=0; i<10; i++){
		JComboBox logintypeBox = new JComboBox();
		for (int j=0; j<pids.size(); j++){
			logintypeBox.addItem(pids.get(j));
		}
		logintypeBox.setBounds(x+105, y+30+i*30, width, height);
		JLabel logintype = new JLabel("Logintype"+""+(i+1));
		logintype.setBounds(x, y+30+i*30, width, height);
		_panel.add(logintype);
		_panel.add(logintypeBox);
		}
		_panel.add(facility);
		_panel.add(facilityBox);
	}
}
