package no.noroff.task17;

import no.noroff.task17.models.contact;
import no.noroff.task17.models.family;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@SpringBootApplication
public class Task17Application {
	public static ArrayList<contact> contacts = new ArrayList<>();
	public static ArrayList<family> families = new ArrayList<>();
	private static Connection conn = null;
	private static String URL = "jdbc:sqlite::resource:ContactInformationDB.db";

	public static void main(String[] args) {
		readContact();
		for (contact con :contacts){
			System.out.println(con.getFirstName() + " " + con.getLastName());
			Map<String, String> phone = con.getPhone();
			System.out.println(phone.get("Personal"));
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
	 *
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
	 *
	 */
	public static void readContact(){
		String sql = "SELECT * FROM Contact " +
				"JOIN Email ON Contact.contactID = Email.contactID " +
				"JOIN Phone ON Contact.contactID = Phone.contactID";
		String contactID;
		String firstName;
		String lastName;
		String address;
		Date dob;
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
				dob = new Date(10000);//rs.getDate("dateOfBirth");
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
	 *
	 * @param ID
	 * @param param
	 * @param value
	 */
	public static void updateContact(String ID, String param, String value){
		String sql = "UPDATE Contact " +
				"SET ? = ? " +
				"WHERE contactID = ?";

		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, param);
			pstmt.setString(2, value);
			pstmt.setString(3, ID);
			pstmt.executeUpdate();

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	/**
	 *
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

	/**
	 *
	 * @param ID
	 * @param param
	 * @param value
	 */
	public static void updateFamily(String ID, String param, String value){
		String sql = "UPDATE Contact " +
				"SET ? = ? " +
				"WHERE contactID = ?";

		try {
			openConn();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, param);
			pstmt.setString(2, value);
			pstmt.setString(3, ID);
			pstmt.executeUpdate();

			closeConn();

		} catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static void insertContact(){}
	public static void deleteContact(){}
	public static void insertFamily(){}
	public static void deleteFamily(){}


}
