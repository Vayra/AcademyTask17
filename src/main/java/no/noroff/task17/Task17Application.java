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

	public static void main(String[] args) {
		readContact();
		readFamily();

		// Test readContacts()
		for (contact con :contacts){
			System.out.println("Name: " + con.getFirstName() + " " + con.getLastName());
			Map<String, String> phone = con.getPhone();
			System.out.println("Phone: " + phone.get("Personal"));
			System.out.println("Address: " + con.getAddress());
		}
		SpringApplication.run(Task17Application.class, args);
	}

	private static void openConn(){
		try {
			conn = DriverManager.getConnection(URL);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

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
				"JOIN Phone ON Contact.contactID = Phone.contactID";
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
				contacts = updatedContacts;
			}
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
	            updatedFamilies.add(new family(contactID, relationshipID, relativeID));
            }
	        families = updatedFamilies;

        } catch (SQLException e){
	        System.out.println(e.getMessage());
        } finally {
	        closeConn();
        }

    }



	public static void insertContact(){}
	public static void deleteContact(){}
	public static void insertFamily(){}
	public static void deleteFamily(){}
	public static void insertEmail(){}
	public static void deleteEmail(){}
	public static void insertPhone(){}
	public static void deletePhone(){}


}
