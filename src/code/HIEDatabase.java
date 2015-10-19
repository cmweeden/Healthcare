package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HIEDatabase {
	// private Connection c;

	/**
	 * 
	 */
	public HIEDatabase() {

		// try {
		// Class.forName("org.sqlite.JDBC");
		// Connection c =
		// DriverManager.getConnection("jdbc:sqlite:Policy_DB.db");
		// } catch (Exception e) {
		// System.err.println(e.getClass().getName() + ": " + e.getMessage());
		// System.exit(0);
		// }
		createTable();
	}

	/**
	 * 
	 */
	public void dropTables() {
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();
			String sql = "DROP TABLE IF EXISTS HIE ";
			s.executeUpdate(sql);
			s.close();
			c.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * 
	 */
	public void createTable() {
		Statement s = null;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();

			String sql = "CREATE TABLE IF NOT EXISTS HIE "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " DATA	BLOB	NOT NULL, " + " CKEY	BLOB	NOT NULL, "
					+ " POLICY 	BLOB 	NOT NULL, " + " CTEXT	BLOB	NOT NULL) ";
			s.executeUpdate(sql);
			s.close();
			c.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param data
	 * @param ckey
	 * @param pol
	 * @param ctext
	 * @return
	 */
	public int insert(String data, String ckey, String pol, String ctext) {
		// Returns id of newly created element. Used to display TID to user.
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			String sql = "INSERT INTO HIE (DATA, CKEY, POLICY, CTEXT) "
					+ "VALUES ('" + data + "', '" + ckey + "', '" + pol
					+ "', '" + ctext + "');";
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

	/**
	 * @param where
	 * @return
	 */
	public ArrayList<String> view(String where) {

		ArrayList<String> results = null;

		Statement s = null;

		ResultSet result = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			results = new ArrayList<String>();

			result = s.executeQuery("SELECT * FROM HIE WHERE " + where + ";");

			if (!result.next()) {
				// Need more to do later
				// System.out.println("No matches or incorrect permissions");
				s.close();
				result.close();
			} else {
				do {

					results.add(result.getString("ID"));
					results.add(result.getString("DATA"));
					results.add(result.getString("CKEY"));
					results.add(result.getString("POLICY"));
					results.add(result.getString("CTEXT"));
				} while (result.next());
				result.close();
				s.close();
			}
			// }

			c.commit();
			c.close();

		} catch (Exception e) {
			System.out.println("Update Error: " + e.getMessage());
		}

		return results;
	}

	/**
	 * @param colVals
	 * @param where
	 * @return
	 */
	public boolean update(String colVals, String where) {
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			String sql = "UPDATE HIE SET " + colVals + " WHERE " + where + ";";
			s.executeUpdate(sql);

			s.close();
			c.commit();
			c.close();

			return true;

		} catch (Exception e) {
			System.out.println("Update Error: " + e.getMessage());
			return false;
		}
	}

	/**
	 * @param where
	 * @return
	 */
	public boolean delete(String where) {
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			String sql = "DELETE FROM HIE WHERE " + where + ";";
			s.executeUpdate(sql);

			s.close();
			c.commit();
			c.close();
			return true;

		} catch (Exception e) {
			System.out.println("Delete Error: " + e.getMessage());
			return false;
		}
	}

}
