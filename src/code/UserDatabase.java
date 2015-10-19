package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDatabase {
	// private Connection c;

	/**
	 * 
	 */
	public UserDatabase() {

		// try {
		// Class.forName("org.sqlite.JDBC");
		// c = DriverManager.getConnection("jdbc:sqlite:Policy_DB.db");
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
			String sql = "DROP TABLE IF EXISTS USERS ";
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

			String sql = "CREATE TABLE IF NOT EXISTS USERS "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " FIRST_NAME	TEXT	NOT NULL, "
					+ " LAST_NAME	TEXT	NOT NULL, " + " TITLE	TEXT	NOT NULL, "
					+ " USERNAME	TEXT	NOT NULL UNIQUE, "
					+ " TYPE	TEXT	NOT NULL) ";

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
	 * @param firstname
	 * @param lastname
	 * @param title
	 * @param username
	 * @param type
	 * @return
	 */
	public int insert(String firstname, String lastname, String title,
			String username, String type) {
		// Returns value of id of newly created row: User ID
		// OR -1 for failure

		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();

			String sql = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, TITLE, USERNAME, TYPE) "
					+ "VALUES ('"
					+ firstname
					+ "', '"
					+ lastname
					+ "', '"
					+ title + "', '" + username + "', '" + type + "');";

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
			String sql = "UPDATE USERS SET " + colVals + " WHERE " + where
					+ ";";
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

			result = s.executeQuery("SELECT * FROM USERS WHERE " + where + ";");

			if (!result.next()) {
				// Need more to do later
				// System.out.println("No matches or incorrect permissions");
				s.close();
				result.close();
			} else {
				do {

					results.add(result.getString("ID"));
					results.add(result.getString("FIRST_NAME"));
					results.add(result.getString("LAST_NAME"));
					results.add(result.getString("TITLE"));
					results.add(result.getString("USERNAME"));
					results.add(result.getString("TYPE"));

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
			String sql = "DELETE FROM USERS WHERE " + where + ";";
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
