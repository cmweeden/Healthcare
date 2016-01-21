package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class SubmitHierarchyListener implements ActionListener {
	String _location;
	ArrayList<JComboBox> _logintypeBoxArray;
	public SubmitHierarchyListener(ArrayList<JComboBox> logintypeBoxArray, String location) {
		_logintypeBoxArray = logintypeBoxArray;
		_location = location;
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
			sql = "DELETE FROM " + _location.replaceAll("\\s","")+";";
			s.executeUpdate(sql);

			s.close();
			c.commit();
			c.close();


		} catch (Exception e1) {
			System.out.println("Delete Error: " + e1.getMessage());
		}
		
		
		
		
		s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			for (int i=0; i< _logintypeBoxArray.size(); i++){
				c.setAutoCommit(false);
				s = c.createStatement();
				sql = "INSERT INTO " + _location + " (Ranking, Position) "
						+ "VALUES ('"
						+ (i+1)
						+ "', '"
						+ _logintypeBoxArray.get(i).getSelectedItem()
						+ "');";
				s.executeUpdate(sql);
				s.close();
				c.commit();
			}
			c.close();

		}      
		catch (Exception e1) {
			System.out.println("Insertion Error: " + e1.getMessage());
		}
		
	}

}
