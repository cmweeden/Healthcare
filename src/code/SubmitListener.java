package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JComboBox;

public class SubmitListener implements ActionListener {
	ArrayList<JComboBox> _comboBoxArray,  _secondComboBoxArray, _thirdComboBoxArray, _fourthComboBoxArray;
	public SubmitListener(ArrayList<JComboBox> comboBoxArray, ArrayList<JComboBox> secondComboBoxArray,
			ArrayList<JComboBox> thirdComboBoxArray, ArrayList<JComboBox> fourthComboBoxArray) {
		_comboBoxArray = comboBoxArray;
		_secondComboBoxArray = secondComboBoxArray;
		_thirdComboBoxArray = thirdComboBoxArray;
		_fourthComboBoxArray = fourthComboBoxArray;
	}

	public void actionPerformed(ActionEvent e) {
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();

			String sql = "INSERT INTO Mappings (Ranking, Hospital, DATE_OF_BIRTH, INSURANCE_PROVIDER) "
					+ "VALUES ('"
					+ firstname
					+ "', '"
					+ lastname
					+ "', '"
					+ dob + "', '" + insurance + "');";

			s.executeUpdate(sql);

			ResultSet rs = s.getGeneratedKeys();
			s.close();
			c.commit();

			if (rs.next()) {
				int result = rs.getInt(1);
				rs.close();
				c.close();
				return result;
			}

		} catch (Exception e) {
			System.out.println("Insertion Error: " + e.getMessage());

		}
		return -1;
	}

}
