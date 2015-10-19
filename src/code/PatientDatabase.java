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

public class PatientDatabase {
	// private Connection c;

	/**
	 * 
	 */
	public PatientDatabase() {
		//
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
			String sql = "DROP TABLE IF EXISTS PATIENTS ";
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

			String sql = "CREATE TABLE IF NOT EXISTS PATIENTS "
					+ "(PID INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " FIRST_NAME	TEXT	NOT NULL, "
					+ " LAST_NAME	TEXT	NOT NULL, "
					+ " DATE_OF_BIRTH	TEXT	NOT NULL, "
					+ " INSURANCE_PROVIDER	TEXT	NOT NULL) ";
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
	 * @param dob
	 * @param insurance
	 * @return
	 */
	public int insert(String firstname, String lastname, String dob,
			String insurance) {
		// Returns value of id of newly created row: Patient ID
		// OR -1 for failure
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();

			String sql = "INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, INSURANCE_PROVIDER) "
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

	/**
	 * @param where
	 * @return A list of all patients who match the parameter where.
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

			result = s.executeQuery("SELECT * FROM PATIENTS WHERE " + where
					+ ";");

			if (!result.next()) {
				// Need more to do later
				System.out.println("No matches or incorrect permissions");
				s.close();
				result.close();
			} else {
				do {

					results.add(result.getString("PID"));
					results.add(result.getString("FIRST_NAME"));
					results.add(result.getString("LAST_NAME"));
					results.add(result.getString("DATE_OF_BIRTH"));
					results.add(result.getString("INSURANCE_PROVIDER"));
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
			String sql = "UPDATE PATIENTS SET " + colVals + " WHERE " + where
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
	public boolean delete(String where) {
		Statement s = null;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager
					.getConnection("jdbc:sqlite:Policy_DB.db");
			c.setAutoCommit(false);
			s = c.createStatement();
			String sql = "DELETE FROM PATIENTS WHERE " + where + ";";

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
	 * @param fn
	 * @return
	 */
	public ArrayList<String> select(String sentence, String fn) {
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
			if (fn.equalsIgnoreCase("Researcher")) {
				sql = "SELECT PID, DATE_OF_BIRTH, INSURANCE_PROVIDER "
						+ "FROM PATIENTS " + " WHERE " + sentence + ";";

			} else {
				sql = "SELECT PID, FIRST_NAME, LAST_NAME, DATE_OF_BIRTH, INSURANCE_PROVIDER "
						+ "FROM PATIENTS " + " WHERE " + sentence + ";";
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
					Element row = doc.createElement("PATIENT_DATA");
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

}
