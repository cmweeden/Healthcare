package code;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PolicyDatabase {
	// Connection c;

	/**
	 * 
	 */
	public PolicyDatabase() {

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
			String sql = "DROP TABLE IF EXISTS POLICIES ";
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

			String sql = "CREATE TABLE IF NOT EXISTS POLICIES "
					+ "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " PID	TEXT	NOT NULL, " + " SUBJECT	TEXT	NOT NULL, "
					+ " ORGANIZATION	TEXT	NOT NULL, "
					+ " READ	BOOLEAN	NOT NULL, " + " WRITE	BOOLEAN	NOT NULL, "
					+ " TOOL	TEXT	NOT NULL, " + " RULE	TEXT	NOT NULL, "
					+ " RESPONSIBILITY 	TEXT 	NOT NULL) ";

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
			String sql = "UPDATE POLICIES SET " + colVals + " WHERE " + where
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
	 * @param pid
	 * @param subject
	 * @param org
	 * @param read
	 * @param write
	 * @param tool
	 * @param rule
	 * @param resp
	 * @return
	 */
	public int insert(String pid, String subject, String org, boolean read,
			boolean write, String tool, String rule, String resp) {
		// Returns value of id of newly created row: Policy ID
		// OR -1 for failure
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();

			String sql = "INSERT INTO POLICIES (PID, SUBJECT, ORGANIZATION, READ, WRITE, TOOL, RULE, RESPONSIBILITY) "
					+ "VALUES ('"
					+ pid
					+ "', '"
					+ subject
					+ "', '"
					+ org
					+ "', '"
					+ read
					+ "', '"
					+ write
					+ "', '"
					+ tool
					+ "', '"
					+ rule + "', '" + resp + "');";

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

			result = s.executeQuery("SELECT * FROM POLICIES WHERE " + where
					+ ";");

			if (!result.next()) {
				// Need more to do later
				// System.out.println("No matches or incorrect permissions");
				s.close();
				result.close();
			} else {
				do {

					results.add(result.getString("PID"));
					results.add(result.getString("SUBJECT"));
					results.add(result.getString("ORGANIZATION"));
					results.add(result.getString("READ"));
					results.add(result.getString("WRITE"));
					results.add(result.getString("TOOL"));
					results.add(result.getString("RULE"));
					results.add(result.getString("RESPONSIBILITY"));

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
			String sql = "DELETE FROM POLICIES WHERE " + where + ";";
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

	/**
	 * @param sentence
	 *            Comparison for where clause, needs to be in the format of
	 *            "U.TYPE = 'Doctor'"
	 * @return A list containing all the patient ids where the patient has a
	 *         policy that fits the parameter sentence.
	 */
	public ArrayList<String> checkCred(String sentence) {

		Statement s = null;

		ResultSet results = null;

		ArrayList<String> p_name = new ArrayList<String>();

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();

			String sql = "SELECT P.PID " + "FROM POLICIES AS X "
					+ "INNER JOIN PATIENTS AS P " + "ON P.PID = X.PID "
					+ "INNER JOIN USERS AS U " + "ON X.SUBJECT = U.TYPE"
					+ " WHERE " + sentence + ";";

			// System.out.println(sql);
			results = s.executeQuery(sql);

			do {
				p_name.add(results.getString("PID"));
				// System.out.println(results.getString("PID"));
			} while (results.next());

			s.close();
			results.close();
			c.close();

		} catch (Exception e) {

		}

		return p_name;

	}

	/**
	 * @param sentence
	 * @return
	 */
	public ArrayList<String> checkWrite(String sentence) {

		Statement s = null;

		ResultSet results = null;

		ArrayList<String> p_name = new ArrayList<String>();

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();

			String sql = "SELECT X.WRITE " + "FROM POLICIES AS X "
					+ "INNER JOIN PATIENTS AS P " + "ON P.PID = X.PID "
					+ "INNER JOIN USERS AS U " + "ON X.SUBJECT = U.TYPE"
					+ " WHERE " + sentence + ";";

			// System.out.println(sql);
			results = s.executeQuery(sql);

			do {
				p_name.add(results.getString("WRITE"));
			} while (results.next());

			s.close();
			results.close();
			c.close();

		} catch (Exception e) {

		}

		return p_name;

	}

	/**
	 * @param sentence
	 * @param t
	 * @return
	 */
	public ArrayList<String> select(String sentence, String t) {
		Statement s = null;
		Statement s2 = null;
		ResultSet results = null;
		String xml = "";
		// System.out.println(sentence);
		boolean access = false;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();
			s2 = c.createStatement();
			String sql;
			if (t.equalsIgnoreCase("Researcher")) {
				sql = "SELECT U.TYPE AS SUBJECT, X.ORGANIZATION AS COMMUNITY, X.READ, X.WRITE, X.PID AS OBJECT, P.DATE_OF_BIRTH, P.INSURANCE_PROVIDER "
						+ "FROM POLICIES AS X "
						+ "INNER JOIN PATIENTS AS P "
						+ "ON P.PID = X.PID "
						+ "INNER JOIN USERS AS U "
						+ "ON X.SUBJECT = U.TYPE" + " WHERE " + sentence + ";";

			} else {
				sql = "SELECT U.TYPE AS SUBJECT, X.ORGANIZATION AS COMMUNITY, X.READ, X.WRITE, X.PID AS OBJECT, P.FIRST_NAME, P.LAST_NAME, P.DATE_OF_BIRTH, P.INSURANCE_PROVIDER "
						+ "FROM POLICIES AS X "
						+ "INNER JOIN PATIENTS AS P "
						+ "ON P.PID = X.PID "
						+ "INNER JOIN USERS AS U "
						+ "ON X.SUBJECT = U.TYPE" + " WHERE " + sentence + ";";
			}

			System.out.println(sql);
			results = s.executeQuery(sql);

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element resultsE = doc.createElement("Results");
			doc.appendChild(resultsE);

			ResultSetMetaData rsmd = results.getMetaData();
			int colCount = rsmd.getColumnCount();

			if (!results.next()) {
				System.out.println("No results");
				access = false;
			} else {

				access = true;

				do {
					Element row = doc.createElement("Policy");
					resultsE.appendChild(row);
					for (int i = 1; i <= colCount; i++) {
						String columnName = rsmd.getColumnName(i);

						Object value = results.getObject(i);
						Element node = doc.createElement(columnName);
						node.appendChild(doc.createTextNode(value.toString()));
						row.appendChild(node);
					}
				} while (results.next());
			}
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", 4);
			Transformer transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);

			xml = sw.toString();
			// System.out.println(xml);

			sw.flush();
			builder.reset();

			results.close();
			s.close();
			c.close();

		} catch (Exception e) {
			System.out.println("FAIL");
			e.getMessage();
			System.out.println(e.toString());
		}

		ArrayList<String> returnVal = new ArrayList<String>();
		returnVal.add(Boolean.toString(access));
		returnVal.add(xml);
		return returnVal;
	}

	/**
	 * @param sentence
	 * @param t
	 * @param tid
	 * @return
	 */
	public ArrayList<String> selectPol(String sentence, String t, int tid) {
		Statement s = null;
		Statement s2 = null;
		ResultSet results = null;
		String xml = "";
		// System.out.println(sentence);
		boolean access = false;

		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			s = c.createStatement();
			s2 = c.createStatement();
			String sql;
			if (t.equalsIgnoreCase("Researcher")) {
				sql = "SELECT X.PID AS OBJECT, U.TYPE AS SUBJECT, X.ORGANIZATION AS COMMUNITY, "
						+ "X.TOOL, X.RULE, X.RESPONSIBILITY "
						+ "FROM POLICIES AS X "
						+ "INNER JOIN PATIENTS AS P "
						+ "ON P.PID = X.PID "
						+ "INNER JOIN USERS AS U "
						+ "ON X.SUBJECT = U.TYPE" + " WHERE " + sentence + ";";

			} else {
				sql = "SELECT X.PID AS OBJECT, U.TYPE AS SUBJECT, X.ORGANIZATION AS COMMUNITY, "
						+ "X.TOOL, X.RULE, X.RESPONSIBILITY "
						+ "FROM POLICIES AS X "
						+ "INNER JOIN PATIENTS AS P "
						+ "ON P.PID = X.PID "
						+ "INNER JOIN USERS AS U "
						+ "ON X.SUBJECT = U.TYPE" + " WHERE " + sentence + ";";
			}

			// System.out.println(sql);
			results = s.executeQuery(sql);

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element resultsE = doc.createElement("XML");
			doc.appendChild(resultsE);

			ResultSetMetaData rsmd = results.getMetaData();
			int colCount = rsmd.getColumnCount();

			if (!results.next()) {
				access = false;
			} else {

				access = true;

				do {
					Element row = doc.createElement("POLICY");
					resultsE.appendChild(row);
					for (int i = 1; i <= colCount; i++) {
						String columnName = rsmd.getColumnName(i);

						Object value;
						if (i == 1) {
							value = Integer.toString(tid);
						} else {
							value = results.getObject(i);
						}
						Element node = doc.createElement(columnName);
						node.appendChild(doc.createTextNode(value.toString()));
						row.appendChild(node);
					}
				} while (results.next());
			}
			DOMSource domSource = new DOMSource(doc);
			TransformerFactory tf = TransformerFactory.newInstance();
			tf.setAttribute("indent-number", 4);
			Transformer transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			StringWriter sw = new StringWriter();
			StreamResult sr = new StreamResult(sw);
			transformer.transform(domSource, sr);

			xml = sw.toString();
			// System.out.println(xml);

			sw.flush();
			builder.reset();

			results.close();
			s.close();
			c.close();

		} catch (Exception e) {
			System.out.println("FAIL");
			e.getMessage();
			System.out.println(e.toString());
		}

		ArrayList<String> returnVal = new ArrayList<String>();
		returnVal.add(Boolean.toString(access));
		returnVal.add(xml);
		return returnVal;
	}

}
