package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.Timer;
//submit button listener for mappings screen
public class SubmitListener implements ActionListener {
	HashMap<String, Set<String>> _mapOfRoles;
	String _loc;
	ImagePanel _panel;
	
	
	public SubmitListener(ImagePanel panel, HashMap<String, Set<String>> mapOfRoles, String loc) {
		_mapOfRoles = mapOfRoles;
		_loc = loc;
		_panel=panel;
	}
	public void actionPerformed(ActionEvent e) {
		Connection c = null;
		Statement s = null;
		String sql="";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Policy_DB.db");
			for (Entry<String, Set<String>> entry : _mapOfRoles.entrySet()){
				for (String position : entry.getValue()){
					c.setAutoCommit(false);
					s = c.createStatement();
					sql = "INSERT INTO " + _loc.replaceAll("\\s","")+"Mappings" + "(" + entry.getKey().replaceAll("\\s","") +")"+ "VALUES ('"+position+"');";
					s.executeUpdate(sql);
					s.close();
					c.commit();
				}
			}
			c.close();
			
		}catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
	}

}
