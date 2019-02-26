package no.noroff.task17;

import no.noroff.task17.models.contact;
import no.noroff.task17.models.family;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

@SpringBootApplication
public class Task17Application {
	public static ArrayList<contact> contacts = new ArrayList<>();
	public static ArrayList<family> families = new ArrayList<>();
	private static Connection conn = null;
	private static String URL = "jdbc:sqlite::resource:ContactInformationDB.db";

	public static void main(String[] args) {

		SpringApplication.run(Task17Application.class, args);
	}

	public static void openConn(){
		try {
			conn = DriverManager.getConnection(URL);
		}
		catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static void closeConn(){
		try{
			if (conn != null) conn.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public void readContact(){
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
				dob = rs.getDate("dateOfBirth");
				email = new Hashtable<>();
				email.put("Personal", rs.getString("personalEmail"));
				email.put("Work", rs.getString("workEmail"));
				phone = new Hashtable<>();
				phone.put("Personal", rs.getString("personalPhone"));
				phone.put("Home", rs.getString("homePhone"));
				phone.put("Work", rs.getString("workPhone"));
				updatedContacts.add(new contact(contactID, firstName, lastName, address, dob, email, phone));
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		} finally {

		}
	}

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

        } catch (SQLException e){
	        System.out.println(e.getMessage());
        } finally {
	        closeConn();
        }


    }

}
