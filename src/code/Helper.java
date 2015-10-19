package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Helper {
	// Various helpful functions

	/**
	 * Method to get the distinct elements from a specified column in the given
	 * table. Used throughout the current program to populate drop-down menus.
	 * 
	 * @param db
	 *            The database which the function should be performed on
	 * @param table
	 *            The table in the database which the information is desired
	 *            from
	 * @param column
	 *            The name of the column which the data should be returned from
	 * @return An array containing all the distinct elements of the selected
	 *         column
	 */
	public static ArrayList<String> getColumns(String db, String table,
			String column) {
		Connection c = null;
		ArrayList<String> results = null;
		try {
			Class.forName("org.sqlite.JDBC");

			c = DriverManager.getConnection("jdbc:sqlite:" + db + ".db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement s = null;
		ResultSet result = null;
		try {
			c.setAutoCommit(false);
			s = c.createStatement();
			results = new ArrayList<String>();
			results.add("");
			result = s.executeQuery("SELECT DISTINCT " + column + " FROM "
					+ table + ";");
			while (result.next()) {
				results.add(result.getString(column));
			}

			result.close();
			s.close();
			c.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	/**
	 * Method to determine whether the specified string contains a
	 * non-whitespace value. Used in this program to prevent null pointer
	 * exceptions. Used by checking to see if user has provided input on a given
	 * line.
	 * 
	 * @param s
	 *            The string to check against.
	 * @return True if the string is only whitespace. False otherwise.
	 */
	public static boolean isEmptyString(String s) {
		if ((s == null) || ((s.trim()).matches(""))
				|| ((s.trim()).matches("null"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to retrieve a specific element from a table, based on the provided
	 * information. Will look up the provided element in the chosen column, and
	 * return the match. Is expected to only have one match, although can be
	 * modified to return more than one match.
	 * 
	 * @param xGiven
	 *            The element used to find the correct row in the table
	 * @param colGiven
	 *            The column which the given element should be found in
	 * @param colWanted
	 *            The column which desired element will be from
	 * @param table
	 *            The table in the database which the information is desired
	 *            from
	 * @return
	 */
	public static String selectItem(String xGiven, String colGiven,
			String colWanted, String table) {

		// TODO Change to return more than one result if applicable?

		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");

			c = DriverManager.getConnection("jdbc:sqlite:" + "Policy_DB"
					+ ".db");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		Statement s = null;
		ResultSet result = null;
		try {
			c.setAutoCommit(false);
			s = c.createStatement();
			result = s.executeQuery("SELECT " + colWanted + " FROM " + table
					+ " WHERE " + colGiven + "='" + xGiven + "';");
			if (!result.next()) {
				System.out.println("no data");
				s.close();
				c.close();
				result.close();
				return "";
			} else {
				String type2 = result.getString(colWanted);
				s.close();
				c.close();
				result.close();
				return type2;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	/**
	 * Method to concatenate a given string and the string " AND ". Used to
	 * slightly decrease the amount of text in certain methods. Entirely
	 * unnecessary, except a small text decrease.
	 * 
	 * @param input
	 *            The string which needs concatenation.
	 * @return The modified string.
	 */
	public static String addAnd(String input) {
		input = input + " AND ";
		return input;
	}

}
