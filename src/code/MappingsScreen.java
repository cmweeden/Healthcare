package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MappingsScreen implements ActionListener{
	static JFrame _frame;
	ImagePanel _panel;
	
	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;
		
	// User log-on type (subject)
	static String _login_type;
	// User log-on location (community)
	static String _loc;
	public MappingsScreen(JFrame f, String type, String loc){
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
		//Code for left columns
		JLabel facility = new JLabel("Facility");
		JLabel rightFacility = new JLabel("Facility2");
		JLabel thirdFacility = new JLabel("Facility3");
		JLabel fourthFacility = new JLabel("Facility4");
		JComboBox facilityBox = new JComboBox();
		JComboBox rightfacilityBox = new JComboBox();
		JComboBox thirdfacilityBox = new JComboBox();
		JComboBox fourthfacilityBox = new JComboBox();
		ArrayList<String> listOfPositions= new ArrayList<String>();
		for (int j=0; j<pids.size(); j++){
			facilityBox.addItem(pids.get(j));
			rightfacilityBox.addItem(pids.get(j));
			thirdfacilityBox.addItem(pids.get(j));
			fourthfacilityBox.addItem(pids.get(j));
		}
		facility.setBounds(x, y, width, height);
		facilityBox.setBounds(x+105, y, width, height);
		rightFacility.setBounds(x+300, y, width, height);
		rightfacilityBox.setBounds(x+300+105, y, width, height);
		thirdFacility.setBounds(x+300+300, y, width, height);
		thirdfacilityBox.setBounds(x+105+300+300, y, width, height);
		fourthFacility.setBounds(x+300+300+300, y, width, height);
		fourthfacilityBox.setBounds(x+300+300+300+105, y, width, height);
		ArrayList<JComboBox> leftComboBoxArray = new ArrayList<JComboBox>();
		ArrayList<JComboBox> rightComboBoxArray = new ArrayList<JComboBox>();
		ArrayList<JComboBox> thirdComboBoxArray = new ArrayList<JComboBox>();
		ArrayList<JComboBox> fourthComboBoxArray = new ArrayList<JComboBox>();
		for (int i=0; i<4; i++){
			leftComboBoxArray.add(new JComboBox());
			rightComboBoxArray.add(new JComboBox());
			thirdComboBoxArray.add(new JComboBox());
			fourthComboBoxArray.add(new JComboBox());
			leftComboBoxArray.get(i).setBounds(x+105, y+30+i*30, width, height);
			rightComboBoxArray.get(i).setBounds(x+300+105, y+30+i*30, width, height);
			thirdComboBoxArray.get(i).setBounds(x+105+300+300, y+30+i*30, width, height);
			fourthComboBoxArray.get(i).setBounds(x+300+105+300+300, y+30+i*30, width, height);
			JLabel logintype = new JLabel("Logintype"+""+(i+1));
			JLabel rightlogintype = new JLabel("Logintype"+""+(i+1));
			JLabel thirdlogintype = new JLabel("Logintype"+""+(i+1));
			JLabel fourthlogintype = new JLabel("Logintype"+""+(i+1));
			logintype.setBounds(x, y+30+i*30, width, height);
			rightlogintype.setBounds(x+300, y+30+i*30, width, height);
			thirdlogintype.setBounds(x+300+300, y+30+i*30, width, height);
			fourthlogintype.setBounds(x+300+300+300, y+30+i*30, width, height);
		
			_panel.add(logintype);
			_panel.add(leftComboBoxArray.get(i));
			_panel.add(rightlogintype);
			_panel.add(rightComboBoxArray.get(i));
			_panel.add(thirdlogintype);
			_panel.add(thirdComboBoxArray.get(i));
			_panel.add(fourthlogintype);
			_panel.add(fourthComboBoxArray.get(i));
		}
		facilityBox.addActionListener((ActionListener) new MappingsComboBoxesListener(_panel, facility, leftComboBoxArray));
		rightfacilityBox.addActionListener((ActionListener) new MappingsComboBoxesListener(_panel, rightFacility, rightComboBoxArray));
		thirdfacilityBox.addActionListener((ActionListener) new MappingsComboBoxesListener(_panel, thirdFacility, thirdComboBoxArray));
		fourthfacilityBox.addActionListener((ActionListener) new MappingsComboBoxesListener(_panel, fourthFacility, fourthComboBoxArray));
		JButton submitButton = new JButton("Submit Mappings");
		submitButton.setBounds(x+50, y+30+30*4+30, width, height);
		_panel.add(submitButton);
		_panel.add(facility);
		_panel.add(facilityBox);
		_panel.add(rightFacility);
		_panel.add(rightfacilityBox);
		_panel.add(thirdFacility);
		_panel.add(thirdfacilityBox);
		_panel.add(fourthFacility);
		_panel.add(fourthfacilityBox);
		
	}
}
