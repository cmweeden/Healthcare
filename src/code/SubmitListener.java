package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;
//submit button listener for mappings screen
public class SubmitListener implements ActionListener {
	ArrayList<JComboBox> _listOfFacilityBoxes;
	ArrayList< ArrayList<JComboBox> > _listOfListsOfComboBoxes;
	public SubmitListener(ArrayList<JComboBox> listOfFacilityBoxes, ArrayList< ArrayList<JComboBox> > listOfListsOfComboBoxes) {
		_listOfListsOfComboBoxes = listOfListsOfComboBoxes;
		_listOfFacilityBoxes = listOfFacilityBoxes;
	}
	public void actionPerformed(ActionEvent e) {
		Statement s = null;
		String sql="";
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			sql = "DROP TABLE MAPPINGS";
			s.executeUpdate(sql);

			s.close();
			c.commit();
			c.close();


		} catch (Exception e1) {
			System.out.println("Delete Error: " + e1.getMessage());
		}
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			sql = "CREATE TABLE MAPPINGS( "
					+ "Ranking INT, "
					+ ((String) _listOfFacilityBoxes.get(0).getSelectedItem()).replaceAll("\\s","") + " TEXT, "
					+ ((String) _listOfFacilityBoxes.get(1).getSelectedItem()).replaceAll("\\s","") + " TEXT, "
					+ ((String) _listOfFacilityBoxes.get(2).getSelectedItem()).replaceAll("\\s","") + " TEXT, "
					+ ((String) _listOfFacilityBoxes.get(3).getSelectedItem()).replaceAll("\\s","") + " TEXT)";
			s.executeUpdate(sql);
			s.close();
			c.commit();
			c.close();


		} catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			for (int i=0; i<4; i++){
				c.setAutoCommit(false);
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
					c.commit();
			}
			c.close();
			
		}catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());

		}
		
	}

}
