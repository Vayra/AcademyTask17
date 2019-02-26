package no.noroff.task17;

import no.noroff.task17.models.contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.util.ArrayList;

@SpringBootApplication
public class Task17Application {
	public static ArrayList<contact> contacts;
	private static Connection conn = null;
	private static String URL = "jdbc:sqlite::resource:ContactInformationDB.db";

	public static void main(String[] args) {

		SpringApplication.run(Task17Application.class, args);
	}

	public static void openConn(){}

	public static void readContact(){}

	public static void readFamily(){}

}
