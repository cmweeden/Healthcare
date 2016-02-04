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
//submit button listener for mappings screen
public class SubmitListener implements ActionListener {
	HashMap<String, Set<String>> _mapOfRoles;
	public SubmitListener(HashMap<String, Set<String>> mapOfRoles) {
		_mapOfRoles = mapOfRoles;
	}
	public void actionPerformed(ActionEvent e) {
		/*
		 * 
		 * Clear exisiting tables from database that are in hashmap, because....why not 
		 */
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
			for (Entry<String, Set<String>> entry : _mapOfRoles.entrySet()){
				ResultSet tables = dbm.getTables(null, null, entry.getKey().replace(" ",""), null);
				if (tables.next()) {
					s = c2.createStatement();
					sql = "DROP TABLE " + entry.getKey().replace(" ","");
					s.executeUpdate(sql);

				}
				else {
				  System.out.println("TABLE DOES NOT EXIST");
				}
				
			}
			s.close();
			System.out.println("CLOSED IT first");
			c.commit();
			c.close();
			System.out.println("CLOSED c");
			c2.commit();
			c2.close();
			System.out.println("CLOSED c2");
		


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
		
		/*
		 * 
		 * Create new tables because im ineffcient. 
		 */
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c3 = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c3.setAutoCommit(false);
			for (Entry<String, Set<String>> entry : _mapOfRoles.entrySet()){
				s = c3.createStatement();
				sql = "CREATE TABLE " + entry.getKey().replace(" ","") +"( SamePolicy TEXT)";
				s.executeUpdate(sql);
				
			}
			s.close();
			System.out.println("CLOSED IT second");
			c3.commit();
			c3.close();
			System.out.println("CLOSED c3");
		


		} catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
		/*
		 * insert into table because i hate efficiency
		 * 
		 */
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:Policy_DB.db");
			for (Entry<String, Set<String>> entry : _mapOfRoles.entrySet()){
				for (String position : entry.getValue()){
					c.setAutoCommit(false);
					s = c.createStatement();
					sql = "INSERT INTO " + entry.getKey().replaceAll(" ", "") + " VALUES ('"+position+"');";
					s.executeUpdate(sql);
					s.close();
					c.commit();
				}
			}
				/*c.setAutoCommit(false);
				s = c.createStatement();
					sql = "INSERT INTO MAPPINGS (Ranking, "+ ((String) _listOfFacilityBoxes.get(0).getSelectedItem()).replaceAll("\\s","")+ ", "
							+ ((String) _listOfFacilityBoxes.get(1).getSelectedItem()).replaceAll("\\s","")+ ", "
							+ ((String) _listOfFacilityBoxes.get(2).getSelectedItem()).replaceAll("\\s","")+ ", "
							+ ((String) _listOfFacilityBoxes.get(3).getSelectedItem()).replaceAll("\\s","")+ ") "
							+ "VALUES ('"
							+ (i+1)
							+ "', '"
							+ _listOfListsOfComboBoxes.get(0).get(i).getSelectedItem()
							+ "', '"
							+ _listOfListsOfComboBoxes.get(1).get(i).getSelectedItem()
							+ "', '"
							+ _listOfListsOfComboBoxes.get(2).get(i).getSelectedItem()
							+ "', '"
							+ _listOfListsOfComboBoxes.get(3).get(i).getSelectedItem()
							+ "');";
					s.executeUpdate(sql);
					s.close();
					c.commit();*/
			c.close();
			
		}catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
	}

}
