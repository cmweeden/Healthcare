package code;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
		HashMap<String, Set<String>> checkBoxMap = new HashMap<String,Set<String>>();
		
		int x = 10;
		int y = 10;
		System.out.println(_loc);
		
		JLabel facility = new JLabel("Your Facility is: " + _loc);
		facility.setBounds(x, y, width+200, height);
		JLabel logintype = new JLabel("Role to map: ");
		logintype.setBounds(x, y+50, width, height);
		JComboBox logintypebox = new JComboBox();
		logintypebox.setBounds(x+125, y+50, width, height);
		JComboBox facilitybox = new JComboBox();
		facilitybox.setBounds(x+300, y, width, height);
		for (int j=0; j<pids.size(); j++){
			facilitybox.addItem(pids.get(j));
		}
		ArrayList<String> selectedTable = Helper.getColumns("Policy_DB", _loc.replaceAll("\\s", ""), "Position");
	        
	        for (int j=0; j<selectedTable.size(); j++){
	        	logintypebox.addItem(selectedTable.get(j));
	        }
	        
		JButton submitButton = new JButton("Submit");
		JButton deleteButton = new JButton("Reset Mappings");
		submitButton.addActionListener(new SubmitListener( _panel,checkBoxMap, _loc));
		submitButton.setBounds(x+50, y+30+30*4+30, width, height);
		MappingsComboBoxesListener classWithCheckBoxes = new MappingsComboBoxesListener(_panel,checkBoxMap,facilitybox, logintypebox, width, height);
		facilitybox.addActionListener((ActionListener) classWithCheckBoxes);
		logintypebox.addActionListener(new MappingPositionBoxActionListener(_panel, logintypebox, facilitybox, checkBoxMap, classWithCheckBoxes, _loc));
		deleteButton.addActionListener(new MappingsResetButtonListener(facilitybox, checkBoxMap, classWithCheckBoxes, _loc ));
		deleteButton.setBounds(x+50, y+30+30*4+30+50, width+90, height);
		_panel.add(facilitybox);
		_panel.add(facility);
		_panel.add(submitButton);
		_panel.add(logintype);
		_panel.add(logintypebox);
		_panel.add(deleteButton);
		
		Rectangle r = _frame.getBounds();
		int h = r.height;
		int w = r.width;
		
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("resources/logo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			picLabel.setBounds(w/2-75,h-76-50, 150, 76);			
			_panel.add(picLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
