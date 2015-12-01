package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MappingsComboBoxesListener implements ActionListener {
	JLabel _facility; 
	ArrayList<JComboBox> _FacilityBox;
	JPanel _panel;
/*	public MappingsComboBoxesListener(JLabel leftfacility, JLabel rightFacility, JComboBox leftFacilityBox, JComboBox rightFacilityBox){
		_leftfacility = leftfacility;
		_rightFacility = rightFacility; 
		_leftFacilityBox = leftFacilityBox;
		_rightFacilityBox = rightFacilityBox;
	}*/
	public MappingsComboBoxesListener(JPanel panel, JLabel facility, ArrayList<JComboBox> FacilityBox) {
		_panel = panel;
		_facility = facility;
		_FacilityBox = FacilityBox;
	}
	public void actionPerformed(ActionEvent e) {
		for (int i=0; i<4; i++){
			_FacilityBox.get(i).removeAllItems();
		}
		JComboBox combo = (JComboBox)e.getSource();
        String selectedItem = (String)combo.getSelectedItem();
        System.out.println(selectedItem.replaceAll("\\s",""));
        ArrayList<String> selectedTable = Helper.getColumns("Policy_DB", selectedItem.replaceAll("\\s",""), "Position");
        for (int i=0; i<4; i++){
        	for (int j=0; j<selectedTable.size(); j++){
        		System.out.println(selectedTable.get(j));
        		_FacilityBox.get(i).addItem(selectedTable.get(j));
        	}
        }
      _panel.revalidate();
      _panel.repaint();
	}
}
