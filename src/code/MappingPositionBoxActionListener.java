package code;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
/*
 * Class that is for the actionlistener for the login combo box on the left hand side of the mappings screen
 * 
 * 
 */
public class MappingPositionBoxActionListener implements ActionListener {
	HashMap<String, Set<String>> _checkBoxMap;
	MappingsComboBoxesListener _classWithCheckboxes;
	String _loc;
	ImagePanel _panel;
	JComboBox _logintypebox;
	JComboBox _facilitybox;
	public MappingPositionBoxActionListener(ImagePanel panel, JComboBox logintypebox, JComboBox facilitybox, HashMap<String, Set<String>> checkBoxMap, MappingsComboBoxesListener classWithCheckBoxes, String loc) {
		_checkBoxMap = checkBoxMap;
		_classWithCheckboxes = classWithCheckBoxes;
		_loc = loc;
		_panel = panel;
		_logintypebox = logintypebox;
		_facilitybox = facilitybox;
		
	}
	public void actionPerformed(ActionEvent e) {
		_classWithCheckboxes.makeCheckBoxes(_facilitybox);
		if (_checkBoxMap.containsKey(_logintypebox.getSelectedItem() )){
			// for value mapped to key
				// set checkbox to be selected
			for (int i =0; i<_checkBoxMap.get(_logintypebox.getSelectedItem() ).size(); i++){
				for (int j=0; j<_classWithCheckboxes._checkboxes.size(); j++){
					if (_checkBoxMap.get(_logintypebox.getSelectedItem()  ).contains( _classWithCheckboxes._checkboxes.get(j).getText()+";"+ _facilitybox.getSelectedItem()) ){
						_classWithCheckboxes._checkboxes.get(j).setSelected(true);
					}
				}
			}
		}
		
		_panel.revalidate();
	    _panel.repaint();
	}

}
