package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MappingsComboBoxesListener implements ActionListener, ItemListener {
	ArrayList<JCheckBox> _checkboxes; 
	JComboBox _FacilityBox;
	 JComboBox _loginTypeBox;
	 HashMap<String, Set<String>> _checkBoxHashMap;
	JPanel _panel;
	int _height;
	int _width;
	/*
	 * Actionlistener class for the facility box selection, this class also makes the checkboxes. 
	 * 
	 * 
	 */
	public MappingsComboBoxesListener(JPanel panel, HashMap<String, Set<String>> checkBoxMap, JComboBox FacilityBox, JComboBox loginTypeBox, int width, int height) {
		_panel = panel;
		_FacilityBox = FacilityBox;
		_height = height;
		_width = width;
		_loginTypeBox = loginTypeBox;
		_checkBoxHashMap = checkBoxMap;
	}
	public void actionPerformed(ActionEvent e) {
		JComboBox combo = (JComboBox)e.getSource();
        makeCheckBoxes(combo);
        if (_checkBoxHashMap.containsKey(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem())){
			for (int i =0; i<_checkBoxHashMap.get(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem()).size(); i++){
				for (int j=0; j<_checkboxes.size(); j++){
					if (_checkBoxHashMap.get(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem() ).contains( _checkboxes.get(j).getText()) ){
						_checkboxes.get(j).setSelected(true);
					}
				}
			}
		}
	}
	public void makeCheckBoxes(JComboBox combo){
		if (_checkboxes!=null){
			for (int i=0; i<_checkboxes.size(); i++){
				_panel.remove(_checkboxes.get(i));
			}
		}
		_panel.revalidate();
	    _panel.repaint();
		String selectedItem = (String)combo.getSelectedItem();
        ArrayList<String> selectedTable = Helper.getColumns("Policy_DB", selectedItem.replaceAll("\\s",""), "Position");
        _checkboxes = new ArrayList<JCheckBox>(selectedTable.size());
        	for (int j=0; j<selectedTable.size(); j++){
        		JCheckBox checkBox = new JCheckBox(selectedTable.get(j));
        		checkBox.setBounds(10+300, 10 + j*40, _width, _height);
        		checkBox.addItemListener(this);
        		_checkboxes.add(checkBox);
        		_panel.add(checkBox);
        	}
      _panel.revalidate();
      _panel.repaint();
	}
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED){
			Object source = e.getItemSelectable();
			for (int i=0; i<_checkboxes.size(); i++){
			 
				if (source == _checkboxes.get(i)){
					if (_checkBoxHashMap.containsKey(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem())){
						Set<String> temp = _checkBoxHashMap.get(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem());
						temp.add(_checkboxes.get(i).getText());
						_checkBoxHashMap.put((String) (_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem()), temp);
					}
					else{
						Set<String> hashValueArray = new TreeSet<String>();
						hashValueArray.add(_checkboxes.get(i).getText());
						_checkBoxHashMap.put((String) (_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem()), hashValueArray);
					}
				}
			}
		}
		
		else{
			Object source = e.getItemSelectable();
			for (int i=0; i<_checkboxes.size(); i++){
			 
				if (source == _checkboxes.get(i)){
					if (_checkBoxHashMap.containsKey(_loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem())){
						_checkBoxHashMap.get( _loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem()).remove( _checkboxes.get(i).getText() );
					}
				}
			}
		}
		System.out.println( _loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem());
		System.out.println(_checkBoxHashMap.get( _loginTypeBox.getSelectedItem() + " " + _FacilityBox.getSelectedItem()));
	}
	
	
}
