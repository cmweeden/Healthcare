package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HIEDBListener implements ActionListener {

	// Common frame and panel for the program
	static JFrame _frame;
	ImagePanel _panel;

	// Height and width, used for setting the size of various elements of the UI
	static int height;
	static int width;

	// Enumeration of user roles
	static ArrayList<String> _role_list = new ArrayList<String>() {{
		add("Researcher");
		add("Administrator");
		add("Insurance Agent");
		add("Physician");
	}};

	static int _role_index;

	// User log-on type (subject)
	static String _login_type;

	// User log-on location (community)
	static String _loc;

	JTextField _first;
	JTextField _last;
	JTextField _birth;

	String _pidS;
	JComboBox _pid;
	JComboBox _ins;
	String _action;

	String _colvals;
	String _where;

	String _docId;

	JComboBox _did;
	String _pt;

	boolean whichId;

	ArrayList<String> _array;
	int _i;

	JLabel _error;

	/**
	 * @param f
	 *            The display frame for the program
	 * @param login_type
	 *            The user log-on type (subject)
	 * @param loc
	 *            The user log-on location (community)
	 * @param action
	 * @param pid
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public HIEDBListener(JFrame f, String login_type, String loc, String action,
			JComboBox pid, JTextField firstname, JTextField lastname,
			JTextField dob, JComboBox insurance, JLabel error) {
		// From DataExportScreen view patient data when not researcher
		_frame = f;
		_login_type = login_type;
		_role_index = _role_list.indexOf(_login_type);
		_pid = pid;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = true;
		_loc = loc;

		_error = error;

	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param action
	 * @param pid
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public HIEDBListener(JFrame f, String login_type, String loc, String action,
			JComboBox pid, JTextField dob, JComboBox insurance, JLabel error) {
		// From DataExportScreen view patient data when researcher
		_frame = f;
		_login_type = login_type;
		_role_index = _role_list.indexOf(_login_type);
		_pid = pid;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = true;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param action
	 * @param did
	 * @param error
	 */
	public HIEDBListener(JFrame f, String login_type, String loc, String action,
			JComboBox did, JLabel error) {
		// From data import screen when view imported data or delete db element
		_frame = f;
		_login_type = login_type;
		_role_index = _role_list.indexOf(_login_type);
		_did = did;
		_action = action;
		_loc = loc;

		_error = error;
	}

	/**
	 * @param f
	 * @param login_type
	 * @param loc
	 * @param action
	 * @param pid
	 * @param firstname
	 * @param lastname
	 * @param dob
	 * @param insurance
	 * @param error
	 */
	public HIEDBListener(JFrame f, String login_type, String loc, String action,
			String pid, JTextField firstname, JTextField lastname,
			JTextField dob, JComboBox insurance, JLabel error) {
		// From DataExportScreen when is data export
		_frame = f;
		_login_type = login_type;
		_role_index = _role_list.indexOf(_login_type);
		_pidS = pid;
		_first = firstname;
		_last = lastname;
		_birth = dob;
		_ins = insurance;
		_action = action;
		whichId = false;
		_loc = loc;

		_error = error;

	}

	/**
	 * @param where
	 * @param frame
	 * @param login_type
	 * @param loc
	 * @param action
	 * @param array
	 * @param i
	 * @param error
	 */
	public HIEDBListener(String where, JFrame frame, String login_type, String loc,
			String action, ArrayList<String> array, int i, JLabel error) {
		// For next and previous on multiple patients from DataExportScreen
		_where = where;
		_frame = frame;
		_login_type = login_type;
		_role_index = _role_list.indexOf(_login_type);
		_action = action;
		_array = array;
		_i = i;
		_loc = loc;

		_error = error;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {

		if (_action.equalsIgnoreCase("view")) {
			if (_login_type.equalsIgnoreCase("Administrator")) {

				String where = addWhere((String) _pid.getSelectedItem(),
						_first.getText(), _last.getText(), _birth.getText(),
						(String) _ins.getSelectedItem());
				PatientDatabase db = new PatientDatabase();
				ArrayList<String> array = db.view(where);

				if (array.size() > 0) {
					String pid = array.get(0);
					String f = array.get(1);
					String l = array.get(2);
					String dob = array.get(3);
					String ins = array.get(4);

					Boolean back = false;
					Boolean next;

					if (6 < array.size()) {
						next = true;
					} else {
						next = false;
					}

					DataExportScreen screen = new DataExportScreen(where,
							_frame, _login_type, _loc, pid, f, l, dob, ins, array, 5,
							next, back);
					screen.createFrame(2);
				} else {
					_error.setText("No matching patients found");
				}

			} else if (_login_type.equals("Researcher")) {

				String where = addWhere((String) _pid.getSelectedItem(), null,
						null, _birth.getText(), (String) _ins.getSelectedItem());
				PatientDatabase db = new PatientDatabase();
				ArrayList<String> array = db.view(where);
				if (array.size() > 0) {
					String pid = array.get(0);
					String dob = array.get(3);
					String ins = array.get(4);

					Boolean back = false;
					Boolean next;

					if (6 < array.size()) {
						next = true;
					} else {
						next = false;
					}
					DataExportScreen screen = new DataExportScreen(where,
							_frame, _login_type, _loc, pid, dob, ins, array, 5, next,
							back);
					screen.createFrame(2);
				} else {
					_error.setText("No matching patients found");
				}

			} else if (_login_type.equals("Physician") || _login_type.equals("Surgeon") || _login_type.equals("Nurse") || _login_type.equals("Receptionist")) {

				// TODO Promulgate the change to add ' ' around the _login_type
				// everywhere this is used.
				String sentence = "U.TYPE = '" + _login_type + "'";
				PolicyDatabase p_db = new PolicyDatabase();
				ArrayList<String> p_id = p_db.checkCred(sentence);

				int i = 0;
				if (!(p_id.isEmpty())) {

					while (i < p_id.size()) {
						String pid2 = p_id.get(i);

						if (_pid.getSelectedItem().equals(pid2)) {
							String where = addWhere(
									(String) _pid.getSelectedItem(),
									_first.getText(), _last.getText(),
									_birth.getText(),
									(String) _ins.getSelectedItem());
							PatientDatabase db = new PatientDatabase();
							ArrayList<String> array = db.view(where);
							String pid = array.get(0);
							String f = array.get(1);
							String l = array.get(2);
							String dob = array.get(3);
							String ins = array.get(4);
							DataExportScreen screen = new DataExportScreen(
									_frame, _login_type, _loc, pid, f, l, dob, ins);
							screen.createFrame(2);
						}
						i++;
					}
				}

				_error.setText("ACCESS DENIED");
			} else if (_login_type.equals("Insurance Agent")) {

				String sentence = "U.TYPE = '" + _login_type + "'";
				PolicyDatabase p_db = new PolicyDatabase();
				ArrayList<String> p_id = p_db.checkCred(sentence);

				int i = 0;
				if (!(p_id.isEmpty())) {

					while (i < p_id.size()) {
						String pid2 = p_id.get(i);
						if (_pid.getSelectedItem().equals(pid2)) {
							PatientDatabase db = new PatientDatabase();
							ArrayList<String> array = db.view(addWhere(
									(String) _pid.getSelectedItem(),
									_first.getText(), _last.getText(),
									_birth.getText(),
									(String) _ins.getSelectedItem()));
							String pid = array.get(0);
							String f = array.get(1);
							String l = array.get(2);
							String dob = array.get(3);
							String ins = array.get(4);
							DataExportScreen screen = new DataExportScreen(
									_frame, _login_type, _loc, pid, f, l, dob, ins);
							screen.createFrame(2);

						}
						i++;
					}
				}

				_error.setText("ACCESS DENIED");

			} else {
				_error.setText("Must select a valid type to view: " + _login_type);
			}
		} else if (_action.equalsIgnoreCase("next")) {
			if (!_login_type.equals("researcher")) {

				String pid = _array.get(_i);
				String f = _array.get(_i + 1);
				String l = _array.get(_i + 2);
				String dob = _array.get(_i + 3);
				String ins = _array.get(_i + 4);

				_where = addWhere(pid, f, l, dob, ins);

				Boolean back;
				Boolean next;
				if (_i < 2) {
					back = false;
				} else {
					back = true;
				}
				if ((_i + 6) < _array.size()) {
					next = true;
				} else {
					next = false;
				}

				DataExportScreen screen = new DataExportScreen(_where, _frame,
						_login_type, _loc, pid, f, l, dob, ins, _array, _i + 5, next,
						back);
				screen.createFrame(2);

			} else if (_login_type.equals("researcher")) {

				String pid = _array.get(_i);
				String dob = _array.get(_i + 3);
				String ins = _array.get(_i + 4);
				_where = addWhere(pid, null, null, dob, ins);

				Boolean back;
				Boolean next;
				if (_i < 2) {
					back = false;
				} else {
					back = true;
				}
				if ((_i + 6) < _array.size()) {
					next = true;
				} else {
					next = false;
				}

				DataExportScreen screen = new DataExportScreen(_where, _frame,
						_login_type, _loc, pid, dob, ins, _array, _i + 5, next, back);
				screen.createFrame(2);

			}

		} else if (_action.equalsIgnoreCase("export")) {

			// Pass patient data to text blob to save

			String eol = System.getProperty("line.separator");
			String text = "Patient ID: " + _pidS;

			String ckey = "Empty Key";
			String pol = "Empty Policy";

			String ctext = "Unable to encrypt data";
			HIEDatabase hie = new HIEDatabase();
			int tid = hie.insert(text, ckey, pol, ctext);

			if (tid < 0) {
				// Unable to insert
				// Load up new screen with error message
				DataExportScreen screen = new DataExportScreen(_frame, _login_type,
						_loc);
				screen.createFrame(1);
			} else {
				String wh;
				if (!(_login_type.equalsIgnoreCase("Researcher"))) {
					// text += eol + "Patient Name: " + _first.getText() + " "
					// + _last.getText();
					wh = addWhere(_pidS, _first.getText(), _last.getText(),
							_birth.getText(), (String) _ins.getSelectedItem());
				} else {
					wh = addWhere(_pidS, "", "", _birth.getText(),
							(String) _ins.getSelectedItem());
				}
				PatientDatabase db = new PatientDatabase();
				ArrayList<String> val = db.select(wh, _login_type);
				text = val.get(1);

				// TODO save this for later, use for multiple key/policy.
				// Difficult to implement
				PolicyDatabase p_db = new PolicyDatabase();
				wh = "X.PID= " + _pidS;
				ArrayList<String> val1 = p_db.selectPol(wh, _login_type, tid);
				// System.out.println(val1.get(1));

				pol = val1.get(1);
				// text += eol + "Date of Birth: " + _birth.getText();
				// text += eol + "Insurance Provider: "
				// + (String) _ins.getSelectedItem();

				// if ((_login_type.equalsIgnoreCase("researcher"))) {
				// ckey = "Blank for now";
				//
				// } else if ((_login_type.equals("Physician"))
				// || (_login_type.equals("Insurance"))) {
				// ckey = tid + " " + _docId + " " + _loc;
				//
				// } else {
				// ckey = "Also blank for now";
				//
				// }
				ckey = createXML(tid, _login_type, _loc);

				byte[] key;
				try {
					key = (ckey).getBytes("UTF-8");
					MessageDigest sha = MessageDigest.getInstance("SHA-1");
					key = sha.digest(key);
					key = Arrays.copyOf(key, 16); // use only first 128 bit

					SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
					Cipher cipher = Cipher.getInstance("AES");
					cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
					// https://www.owasp.org/index.php/Using_the_Java_Cryptographic_Extensions
					byte[] byteDataToEncrypt = text.getBytes();
					byte[] byteCipherText = cipher.doFinal(byteDataToEncrypt);
					byte[] encodedBytes = Base64.encodeBase64(byteCipherText);
					ctext = new String(encodedBytes);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}

				String colvals = "CKEY = '" + ckey + "', POLICY = '" + pol
						+ "', CTEXT = '" + ctext + "'";
				String where = "ID = " + tid;

				hie.update(colvals, where);
				// TODO Display Tid
				// DataExportScreen screen = new DataExportScreen(_frame, _login_type,
				// _loc, tid);
				// screen.createFrame(3);
				_error.setText("Data Exported with TID " + tid);

			}

			// //Display text blob
			// JFrame frame = new JFrame("Patient Data");
			// frame.setSize(350, 250);
			// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// JPanel panel = new JPanel();
			// JTextArea textbox = new JTextArea(text);
			// panel.add(textbox);
			// frame.add(panel);
			// frame.setVisible(true);
			// frame.pack();
			// frame.setLocationRelativeTo(null);

			// Solicit name for document via new screen pop up
			// if ((_login_type.equals("physician")) || (_login_type.equals("insurance"))) {
			// DocumentScreen dscreen = new DocumentScreen(_frame, _login_type,
			// _docId, _loc, text);
			// DocumentScreen.createFrame();
			// } else {
			// DocumentScreen dscreen = new DocumentScreen(_frame, _login_type, text);
			// DocumentScreen.createFrame();
			// }

			// Query policy db for all policies concerning pid
			// Attach policies as credentials to view

		} else if (_action.equalsIgnoreCase("import")) {
			String decryptionID = (String) _did.getSelectedItem();
			String where = "ID = " + decryptionID;
			HIEDatabase hie = new HIEDatabase();
			ArrayList<String> array = hie.view(where);

			String text = array.get(1);
			String ckey = array.get(2);
			String pol = array.get(3);
			String ctext = array.get(4);
			ArrayList<String> yourLocationTypes = new ArrayList<String>();
			String sql = "SELECT " + _loc.replaceAll("\\s","") + " FROM MAPPINGS";
			getPositionAtLocation(sql,yourLocationTypes);
			System.out.println(yourLocationTypes);
			String decryptedMessage = "Unable to decrypt";
			InputSource source = new InputSource(new StringReader(ckey));
			ArrayList<String> allLocations = new ArrayList<String>();
			allLocations.add("ResearchFacility");
			allLocations.add("InsuranceOffice");
			allLocations.add("Hospital");
			allLocations.add("Clinic");
			/*DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			String tempLocation = null;
			//ArrayList<String>
			try {
				db = dbf.newDocumentBuilder();
				Document document = db.parse(source);
				XPathFactory xpathFactory = XPathFactory.newInstance();
				XPath xpath = xpathFactory.newXPath();
				tempLocation = xpath.evaluate("/KEY/COMMUNITY", document);
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			/*sql = "SELECT " + tempLocation.replaceAll("\\s","") + " FROM MAPPINGS";
			ArrayList<String> theirLocationTypes = new ArrayList<String>();
			getMappings(sql, theirLocationTypes);*/
			int roleIndex = 0;
			for(int i=0; i<yourLocationTypes.size();i++){
				if (_login_type.equals(yourLocationTypes.get(i))){
					roleIndex=i;
				}
			}
			for(int i=0; i<allLocations.size(); i++){
				sql = "SELECT " + allLocations.get(i) + " FROM MAPPINGS";
				ArrayList<String> theirLocationTypes = new ArrayList<String>();
				getPositionAtLocation(sql, theirLocationTypes);
				for(int j=roleIndex; j<theirLocationTypes.size(); j++){
					String role = theirLocationTypes.get(j);
				
					String dkey = createXML(Integer.valueOf(decryptionID), role, allLocations.get(i));
					System.out.println(dkey);
					byte[] key;
					try {
						key = (dkey).getBytes("UTF-8");

						MessageDigest sha = MessageDigest.getInstance("SHA-1");
						key = sha.digest(key);
						key = Arrays.copyOf(key, 16); // use only first 128 bit

						SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
						Cipher cipher = Cipher.getInstance("AES");

						cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
						byte[] byteArray = Base64.decodeBase64(ctext.getBytes());

						byte[] byteDecryptedText = cipher.doFinal(byteArray);

						decryptedMessage = new String(byteDecryptedText);	
						if (checkDecrypt(decryptedMessage)) {
							break;
						}

					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					} catch (IllegalBlockSizeException e) {
						e.printStackTrace();
					} catch (BadPaddingException e) {
						e.printStackTrace();
					} catch (InvalidKeyException e) {
						e.printStackTrace();
					} catch (NoSuchPaddingException e) {
						e.printStackTrace();
					}
				}
			}
			// decrypt
			String message;
			String eol = System.getProperty("line.separator");

			if (checkDecrypt(decryptedMessage)) {
				message = decryptedMessage;
			}

			else {
				String mitigation = "";
				mitigation += StringUtils.substringBetween(pol,
						"<RESPONSIBILITY>", "</RESPONSIBILITY>");
				mitigation += " will be contacted by ";
				mitigation += StringUtils.substringBetween(pol, "<TOOL>",
						"</TOOL>");
				mitigation += " due to authentication ";
				mitigation += StringUtils.substringBetween(pol, "<RULE>",
						"</RULE>");
				System.out.println("Mitigation Strategy: " + mitigation); // good
				message = mitigation;

			}

			DataImportScreen screen = new DataImportScreen(_frame, _login_type, _loc,
					decryptionID, message);
			screen.createFrame(2);

		} else if (_action.equalsIgnoreCase("delete")) {
			if (_login_type.equalsIgnoreCase("administrator")) {

				String did = (String) _did.getSelectedItem();
				String where = "ID = " + did;
				HIEDatabase hie = new HIEDatabase();

				boolean success = hie.delete(where);

				if (success) {
					DataImportScreen screen = new DataImportScreen(_frame, _login_type,
							_loc);
					screen.createFrame(1);
				} else {
					_error.setText("Unable to delete element");
				}
			} else {
				_error.setText("Must be an admin to delete from the hie database.");
			}

			_did.setSelectedItem("");
		}

	}

	/**
	 * @param message
	 * @return
	 */
	private boolean checkDecrypt(String message) {
		if (message.startsWith("<XML>")) {
			System.out.println("Decrypted: " + message);
			return true;
		}
		System.out.println("Unable to decrypt: " + message);
		return false;
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
	/**
	 * @param p
	 * @param f
	 * @param l
	 * @param d
	 * @param i
	 * @return
	 */
	public String addWhere(String p, String f, String l, String d, String i) {

		String where = "";
		boolean addAnd = false;

		if (!Helper.isEmptyString(p)) {
			where += "PID=" + p;
			addAnd = true;
		}
		if (!Helper.isEmptyString(f)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "FIRST_NAME='" + f + "'";
			} else {
				where += "FIRST_NAME='" + f + "'";
				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(l)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "LAST_NAME='" + l + "'";
			} else {
				where += "LAST_NAME='" + l + "'";

				addAnd = true;
			}
		}

		if (!Helper.isEmptyString(d)) {

			if (addAnd) {
				where = Helper.addAnd(where);
				where += "DATE_OF_BIRTH='" + d + "'";

			} else {
				where += "DATE_OF_BIRTH='" + d + "'";

				addAnd = true;
			}
		}
		if (!Helper.isEmptyString(i)) {
			if (addAnd) {
				where = Helper.addAnd(where);
				where += "INSURANCE_PROVIDER='" + i + "'";

			} else {
				where += "INSURANCE_PROVIDER='" + i + "'";

				addAnd = true;
			}
		}

		// System.out.println(where);
		return where;
	}

	/**
	 * @return
	 */
	public String addCols() {

		String col = "";

		String firstN = _first.getText();

		col += "FIRST_NAME = '" + firstN + "'";

		String lastN = _last.getText();

		col += ", LAST_NAME = '" + lastN + "'";

		String dob = _birth.getText();

		col += ", DATE_OF_BIRTH = '" + dob + "'";

		String insu = (String) _ins.getSelectedItem();

		col += ", INSURANCE_PROVIDER = '" + insu + "'";

		return col;
	}

	/**
	 * @param tid
	 * @return
	 */
	public String createXML(int tid, String login_type, String loc) {
		String xml = "";
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element resultsE = doc.createElement("KEY");
			doc.appendChild(resultsE);

			Element node = doc.createElement("SUBJECT");
			
			node.appendChild(doc.createTextNode(login_type));
			resultsE.appendChild(node);

			node = doc.createElement("OBJECT");
			node.appendChild(doc.createTextNode(Integer.toString(tid)));
			resultsE.appendChild(node);

			node = doc.createElement("COMMUNITY");
			node.appendChild(doc.createTextNode(loc));
			resultsE.appendChild(node);

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
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

}
