package no.noroff.task17;

import no.noroff.task17.models.contact;
import no.noroff.task17.models.family;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.ArrayList;

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

	public static void readContact(){}

	public static void readFamily(){
	    String sql = "SELECT * FROM Family ";

	    String relativeID;
	    String contactID;
	    String relationshipID;

	    try {
	        openConn();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        ArrayList<family> udatedFamilies = new ArrayList<>();

	        while(rs.next()){
	            contactID = rs.getString("contactID");
	            relationshipID = rs.getString("relationshipID");
	            relativeID = rs.getString("relativeID");
	            udatedFamilies.add(new family(contactID, relationshipID, relativeID));
            }

        } catch (SQLException e){
	        System.out.println(e.getMessage());
        } finally {
	        closeConn();
        }


    }

}
