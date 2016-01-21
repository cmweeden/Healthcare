package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
//Login screen combobox listener for location you are logging into
public class LocationComboBoxListener implements ActionListener {
	JComboBox _typeComboBox;
	ImagePanel _panel;
	public LocationComboBoxListener(JComboBox typeComboBox, ImagePanel panel){
		_typeComboBox=typeComboBox;
		_panel = panel;
	}
	public void actionPerformed(ActionEvent e) {
		_typeComboBox.removeAllItems();
		JComboBox combo = (JComboBox)e.getSource();
        String selectedItem = (String)combo.getSelectedItem();
        ArrayList<String> selectedTable = Helper.getColumns("Policy_DB", selectedItem.replaceAll("\\s",""), "Position");
        	for (int j=0; j<selectedTable.size(); j++){
        		System.out.println(selectedTable.get(j));
        		_typeComboBox.addItem(selectedTable.get(j));
        	}
      _panel.revalidate();
      _panel.repaint();
        
		
	}

}
