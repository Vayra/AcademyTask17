package no.noroff.task17;

import javafx.scene.control.Tab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

public class Main {
    private static Table<Integer, String> firstNameTable;
    private static Table<Integer, String> lastNameTable;
    private static Table<Integer, Map<String, String>> phoneTable; // Might need to change table for this
    private static Table<Integer, SimpleDateFormat> dateOfBirthTable;
    private static Table<Integer, Map<String, String>> emailTable; // might need to change table for this
    private static Table<Integer, String> addressTable;
    private static ArrayList<Integer> ids;
    private static int id = 0;

    public static void main(String[] args) {
        firstNameTable = new Table<>("First Name");
        lastNameTable = new Table<>("Last Name");
        phoneTable = new Table<>("Phone number");
        dateOfBirthTable = new Table<>("Date of Birth");
        emailTable = new Table<>("Email");
        addressTable = new Table<>("Address");
        ids = new ArrayList<>();

        Hashtable<String, String> phone = new Hashtable<>();
        Hashtable<String, String> email = new Hashtable<>();
        phone.put("Personal", "93055717");
        email.put("Personal", "thomas.grefsrud@gmail.com");
        email.put("Work", "thomas.grefsrud@no.experis.com");
        addPerson("Thomas", "Grefsrud", phone, email, "Haakon Tveters Vei 8, 0682 Oslo", new SimpleDateFormat("11/24/1989"));

        phone = new Hashtable<>();
        email = new Hashtable<>();
        phone.put("Personal", "48126991");
        email.put("Personal", "helene.harmens@gmail.com");
        email.put("Work", "helene.hunding.harmens@no.experis.com");
        addPerson("Helene", "Harmens", phone, email, "Bjerregaardsgate 24B, 0172 Oslo", new SimpleDateFormat("08/16/1991"));


        for (int id: ids){
            printPerson(id);
        }
        phone.put("Home", "13494524");
        phoneTable.update(1, phone);

        printPerson(1);


    }
    public static void printName(int key){
        System.out.println(firstNameTable.get(key) + " " + lastNameTable.get(key));
    }

    public static void printPerson(int key) {
        System.out.println("Printing personal info: ");
        System.out.println("Name: " + firstNameTable.get(key) + " " + lastNameTable.get(key));
        System.out.println("Phone: ");
        for (Map.Entry<String, String> p:phoneTable.get(key).entrySet())
            System.out.println("\t" + p.getKey() + ":\t" + p.getValue());
        System.out.println("Date of birth: " + dateOfBirthTable.get(key).toLocalizedPattern());
        System.out.println("Email: ");
        for (Map.Entry<String, String> p:emailTable.get(key).entrySet())
            System.out.println("\t" +p.getKey() + ":\t" + p.getValue());
        System.out.println("Address: " + addressTable.get(key));
    }

    public static boolean addPerson(String firstName, String lastName, Map<String, String> phone,
                                    Map<String, String> email, String address, SimpleDateFormat dob)
    {
        int newID = id++;
        ids.add(newID);
        firstNameTable.add(newID, firstName);
        lastNameTable.add(newID, lastName);
        phoneTable.add(newID, phone);
        emailTable.add(newID, email);
        addressTable.add(newID, address);
        dateOfBirthTable.add(newID, dob);

        return true;
    }
}