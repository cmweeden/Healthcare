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
import java.util.Set;

import javax.swing.JComboBox;

import java.util.Map.Entry;

public class MappingsResetButtonListener implements ActionListener {
	HashMap<String, Set<String>> _checkBoxMap;
	MappingsComboBoxesListener _classWithCheckBoxes;
	String _loc;
	JComboBox _facilitybox;

	public MappingsResetButtonListener(JComboBox facilitybox, HashMap<String, Set<String>> checkBoxMap,
			MappingsComboBoxesListener classWithCheckBoxes, String loc) {
		_loc = loc;
		_checkBoxMap = checkBoxMap;
		_classWithCheckBoxes = classWithCheckBoxes;
		_facilitybox = facilitybox;
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList<String> yourLocationTypes = new ArrayList<String>();
		String statement = "SELECT " + "Position" + " FROM " +_loc.replaceAll("\\s","");
		getPositionAtLocation(statement,yourLocationTypes);
		Connection c = null;
		Connection c2 = null;
		Statement s = null;
		String sql="";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c2 = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			c2.setAutoCommit(false);
			DatabaseMetaData dbm = c.getMetaData();
			ResultSet tables = dbm.getTables(null, null, _loc.replaceAll("\\s","")+"Mappings", null);
			if (tables.next()) {
				s = c2.createStatement();
				sql = "DROP TABLE " + _loc.replaceAll("\\s","")+"Mappings";
				s.executeUpdate(sql);
			}
			else {
				System.out.println("TABLE DOES NOT EXIST");
			}
			s.close();
			c.commit();
			c.close();
			c2.commit();
			c2.close();
		


		} catch (Exception e1) {
			System.out.println("Delete Error: " + e1.getMessage());
			
		}
		finally{
		/*	try {
				if (s!=null)
					s.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			try {
				if (c!=null)
					c.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if (c2!=null)
					c2.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c3 = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c3.setAutoCommit(false);
			//for (Entry<String, Set<String>> entry : _mapOfRoles.entrySet()){
			s = c3.createStatement();
			sql = "CREATE TABLE " + _loc.replaceAll("\\s","")+"Mappings(";
			/*for (Entry<String, Set<String>> entry : _checkBoxMap.entrySet()){
				sql = sql + " " + entry.getKey().replaceAll("\\s","") + " TEXT,";
			}*/
			for(int i=0; i<yourLocationTypes.size(); i++){
				sql = sql + " " + yourLocationTypes.get(i).replaceAll("\\s","") + " TEXT,";
			}
			sql = sql.substring(0,sql.length()-1) + ")";
			System.out.println(sql);
			s.executeUpdate(sql);
				
			//}
			s.close();
			System.out.println("CLOSED IT second");
			c3.commit();
			c3.close();
			System.out.println("CLOSED c3");
		


		} catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
		_checkBoxMap.clear();
		_classWithCheckBoxes.makeCheckBoxes(_facilitybox);

	}
	private ArrayList<String> getPositionAtLocation(String sql, ArrayList<String> mappingsList){
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection c;
		try {
			c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			Statement s = c.createStatement ();
			ResultSet rs = s.executeQuery(sql);
			int count = 0;
			while (rs.next ())
			   {
				mappingsList.add(rs.getString(1));
			   }
			s.close();
			c.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return mappingsList;
		
	}

}
