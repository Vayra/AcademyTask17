package no.noroff.task17;

import no.noroff.task17.models.contact;
import no.noroff.task17.models.family;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class Task17Application {
	public static ArrayList<contact> contacts = new ArrayList<>();
	public static ArrayList<family> families = new ArrayList<>();
	private static Connection conn = null;
	private static String URL = "jdbc:sqlite::resource:ContactInformationDB.db";
	public static int lastID = 0;

	public static void main(String[] args) {
		SpringApplication.run(Task17Application.class, args);
		/*
		deleteFromTable("3", "Contact");
		deleteFromTable("3", "Email");
		deleteFromTable("3", "Phone");
		deleteFromTable("4", "Contact");
		deleteFromTable("4", "Email");
		deleteFromTable("4", "Phone");
		deleteContact("3");
		deleteContact("4");
		insertContact("3", "Craig", "Marais", "South Africa", "1/1/1970",
				"craig@marais.com", "craig@noroff.no", "1234567", " ", "98765");
		insertContact("4", "Ola", "Nordmann", "South Africa", "1/1/1970",
				"craig@marais.com", "craig@noroff.no", "1234567", " ", "98765");*/

		//updateTable("4", "Phone", "homePhone", "22334455");

		//insertFamily("3", "4", "3");
		//insertFamily("4","3", "3");
		//insertFamily("2","1","2");

		readContact();
		readFamily();

		showRelatedContacts("1");
		showRelatedContacts("3");

		// Test readContacts()


		/*

		for (contact con :contacts){
			System.out.println("ID: " + con.getContactID());
			System.out.println("Name: " + con.getFirstName() + " " + con.getLastName());
			Map<String, String> phone = con.getPhone();
			System.out.println("Phone: " + phone.get("Personal"));
			System.out.println("Address: " + con.getAddress());
		}
		*/



	}

	/**
	 * Opens connection to DB via static variable conn
	 */
	private static void openConn(){
		try {
			conn = DriverManager.getConnection(URL);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Close connection to DB via static variable conn
	 */
	private static void closeConn(){
		try{
			if (conn != null) conn.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Creates tables if necessary.
	 */
	public static void createTables(){
		String sql = "CREATE TABLE IF NOT EXISTS Contact (\n" +
				"\tcontactID NVARCHAR(50)  NOT NULL PRIMARY KEY,\n" +
				"\tfirstName NVARCHAR(50)  NOT NULL,\n" +
				"\tlastName NVARCHAR(50)  NOT NULL,\n" +
				"\taddress NVARCHAR(50)  NOT NULL,\n" +
				"\tdateOfBirth DATE  NULL\n" +
				");\n" +
				"CREATE TABLE IF NOT EXISTS Relation (\n" +
				"\tcontactID NVARCHAR(50) NOT NULL,\n" +
				"\tkind NVARCHAR(50) NULL\n" +
				"\n" +
				");\n" +
				"CREATE TABLE IF NOT EXISTS Family (\n" +
				"\tcontactID NVARCHAR(50) NOT NULL,\n" +
				"\trelativeID NVARCHAR(50) NULL,\n" +
				"\trelationshipID NVARCHAR(50) NULL\n" +
				"\n" +
				");\n" +
				"CREATE TABLE IF NOT EXISTS Email (\n" +
				"\tcontactID NVARCHAR(50) NOT NULL,\n" +
				"\tpersonalEmail NVARCHAR(50)  NULL,\n" +
				"\tworkEmail NVARCHAR(50)  NULL\n" +
				");\n" +
				"CREATE TABLE IF NOT EXISTS Phone (\n" +
				"\tcontactID NVARCHAR(50) NOT NULL,\n" +
				"\tpersonalPhone NVARCHAR(50)  NULL,\n" +
				"\thomePhone NVARCHAR(50)  NULL,\n" +
				"\tworkPhone NVARCHAR(50)  NULL\n" +
				");";

		try {
			openConn();
			Statement stmt = conn.createStatement();

			stmt.execute(sql);

			closeConn();

		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Reads contact table to contacts
	 */
	public static void readContact(){
		String sql = "SELECT * FROM Contact " +
				"JOIN Email ON Contact.contactID = Email.contactID " +
				"JOIN Phone ON Contact.contactID = Phone.contactID " +
				"ORDER BY Contact.contactID";
		String contactID;
		String firstName;
		String lastName;
		String address;
		String dob;
		Map<String, String> email;
		Map<String, String> phone;
		try {
			openConn();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<contact> updatedContacts = new ArrayList<>();

			while (rs.next()) {

				contactID = rs.getString("contactID");
				firstName = rs.getString("firstName");
				lastName = rs.getString("lastName");
				System.out.println("Read contact: " + firstName + " " + lastName);
				address = rs.getString("address");
				dob = rs.getString("dateOfBirth");
				email = new HashMap<>();
				email.put("Personal", rs.getString("personalEmail"));
				email.put("Work", rs.getString("workEmail"));
				phone = new HashMap<>();
				phone.put("Personal", rs.getString("personalPhone"));
				phone.put("Home", rs.getString("homePhone"));
				phone.put("Work", rs.getString("workPhone"));
				updatedContacts.add(new contact(contactID, firstName, lastName, address, dob, email, phone));
			}
			contacts = updatedContacts;
			lastID = Integer.parseInt(contacts.get(contacts.size() - 1).getContactID());
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			closeConn();
		}
	}

	/**
	 * Updates a single value of a contact in the database
	 *
	 * @param ID ContactID of contact to update
	 * @param tableName name of table to update
	 * @param param column to update
	 * @param value new value of param
	 */
	public static void updateTable(String ID, String tableName, String param, String value){
		String sql = "UPDATE " + tableName + " SET " + param + " = '" + value+ "' WHERE contactID = '"+ID+"'";

		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.executeUpdate();

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Reads family table to families
	 */
	public static void readFamily(){
	    String sql = "SELECT * FROM Family ";

	    String relativeID;
	    String contactID;
	    String relationshipID;

	    try {
	        openConn();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        ArrayList<family> updatedFamilies = new ArrayList<>();

	        while(rs.next()){
	            contactID = rs.getString("contactID");
	            relationshipID = rs.getString("relationshipID");
	            relativeID = rs.getString("relativeID");
	            updatedFamilies.add(new family(contactID, relativeID, relationshipID));
            }
	        families = updatedFamilies;

        } catch (SQLException e){
	        System.out.println(e.getMessage());
        } finally {
	        closeConn();
        }

    }

	/**
	 *  Deletes a table
	 * @param tableName name of table
	 */
	public static void deleteTable(String tableName){
		String sql = "DROP IF EXISTS " + tableName;
		if(execute(sql)) System.out.println("Deleted table " + tableName);
	}

	/**
	 *  Insert a new contact into the DB.
	 * @param contactID contact ID
	 * @param firstName First name of contact
	 * @param lastName Surname of contact
	 * @param address Contact address
	 * @param dateOfBirth Date of birth of contact
	 * @param personalEmail Personal email of contact
	 * @param workEmail Work email of contact
	 * @param personalPhone Personal phone of contact
	 * @param homePhone Home phone of contact
	 * @param workPhone Work phone of contact
	 */
	public static void insertContact(String contactID, String firstName, String lastName, String address,
									 String dateOfBirth, String personalEmail, String workEmail, String personalPhone,
									 String homePhone, String workPhone){
		String sql = "INSERT INTO Contact (contactID, firstName, lastName, address, dateOfBirth) VALUES (?, ?, ?, ?, ?);";
		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, contactID);
			pstmt.setString(2, firstName);
			pstmt.setString(3, lastName);
			pstmt.setString(4, address);
			pstmt.setString(5, dateOfBirth);

			if (pstmt.execute()) System.out.println("Added " + firstName + " " + lastName + " to DB with ID=" + contactID);
			closeConn();
			insertEmail(contactID, personalEmail, workEmail);
			insertPhone(contactID, personalPhone, homePhone, workPhone);


		} catch (SQLException e){
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Insert contact with contact object
	 * @param con Contact object to add
	 */
	public static void insertContact( contact con){
		insertContact(con.getContactID(), con.getFirstName(), con.getLastName(), con.getAddress(), con.getDob(),
				con.getEmail().get("Personal"), con.getEmail().get("Home"), con.getPhone().get("Work"),
				con.getPhone().get("Home"), con.getPhone().get("Work"));
	}

	/**
	 * Delete an entry from the table
	 * @param ID ID of entry to be deleted
	 * @param tableName Name of table to delete from
	 */
	public static void deleteFromTable(String ID, String tableName){
		String sql = "DELETE FROM " + tableName + " WHERE contactID = " + ID;
		if(execute(sql)) System.out.println("Deleted ID=" + ID + " from " + tableName);
	}

	/**
	 * Deletes contact with given ID from Contact, Email and Phone
	 * @param ID ID of contact to remove
	 */
	public static void deleteContact(String ID){
		String[] tables = new String[]{"Contact", "Email", "Phone"};
		for (String tableName:tables) {
			String sql = "DELETE FROM " + tableName + " WHERE contactID = '" + ID + "'";
			if(execute(sql)) System.out.println("Deleted ID=" + ID + " from " + tableName);
		}
	}

	/**
	 *  Insert family relation
	 * @param contactID ID of person to add
	 * @param relationID ID of relative
	 * @param relationshipID ID of relation between them
	 */
	public static void insertFamily(String contactID, String relationID, String relationshipID){
		String sql = "INSERT INTO Family (contactID, relativeID, relationshipID) " +
				"VALUES (?, ?, ?);";
		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, contactID);
			pstmt.setString(2, relationID);
			pstmt.setString(3, relationshipID);

			if(pstmt.execute()) System.out.println("Inserted relation between IDs " + contactID + " and " + relationID);

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Insert entry into email table
	 * @param ID ID of contact to associate email with
	 * @param personalEmail Personal email address
	 * @param workEmail Work email address
	 */
	public static void insertEmail(String ID, String personalEmail, String workEmail){
		String sql = "INSERT INTO Email (contactID, personalEmail, workEmail) " +
				"VALUES (?, ?, ?)";
		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.setString(2, personalEmail);
			pstmt.setString(3, workEmail);

			if(pstmt.execute()) System.out.println("Added emails to ID=" + ID);

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Insert entry into phone table
	 * @param ID ID of contact to associate phone with
	 * @param personalPhone Personal phone number
	 * @param workPhone Work phone number
	 * @param homePhone Home phone number
	 */
	public static void insertPhone(String ID, String personalPhone, String homePhone, String workPhone){
		String sql = "INSERT INTO Phone (contactID, personalPhone, homePhone, workPhone) " +
				"VALUES (?, ?, ?, ?)";
		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ID);
			pstmt.setString(2, personalPhone);
			pstmt.setString(3, homePhone);
			pstmt.setString(4, workPhone);

			if(pstmt.execute()) System.out.println("Added phones to ID=" + ID);

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 *  find contact by ID in contacts
	 * @param ID contactID of contacy
	 * @return contact if found, otherwise null
	 */
    private static contact findContact(String ID){
        for (contact con:contacts){
            if (con.getContactID().equals(ID)) return con;
        }
        return null;
    }

	/**
	 * find type of relation by ID
	 * @param ID relationshipID
	 * @return String of relation
	 */
	private static String getKind(String ID) {
        switch (ID) {
            case "1":
                return "Parent";
            case "2":
                return "Child";
            case "3":
                return "Sibling";
            default:
                return "";
        }
    }

    /**
     * Show relatives to contactID
     * @param id contactID
     */
	private static void showRelatedContacts (String id) {

        String sqlFam = "SELECT * FROM Family WHERE contactID = '" + id + "'";

		try {
			openConn();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlFam);

			while(rs.next()){

			    String contactID = rs.getString("contactID");
                String relativeID = rs.getString("relativeID");

                contact contact = findContact(contactID);
                contact relative = findContact(relativeID);

                String personFirstName = contact.getFirstName();
                String personLastName = contact.getLastName();

                String relativeFirstName = relative.getFirstName();
                String relativeLastName = relative.getLastName();

                String kind = getKind(contactID);
        				System.out.println(personFirstName +" "+ personLastName + " is "+ kind +
                        " to " + relativeFirstName + " " + relativeLastName +".");
			}
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Helper function to execute SQL Statements
	 * @param sql SQL Statement to execute
	 */
	private static boolean execute(String sql){
		boolean success = false;
		try {
			openConn();
			Statement stmt = conn.createStatement();

			if(stmt.execute(sql)) success = true;

			closeConn();
      
		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
		return success;
	}


}
