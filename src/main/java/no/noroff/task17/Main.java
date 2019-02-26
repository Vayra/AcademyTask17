package no.noroff.task17;


import java.text.SimpleDateFormat;
import java.util.*;

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

        /*firstNameTable = new Table<>("First Name");
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

        Scanner in = new Scanner(System.in);
        String command = "";
        String[] com;

        System.out.println("Welcome to crappyDB");
        System.out.println("Type 'help' for help or a command to get started");
        while (true)
        {
            command = in.next();
            if (command.equalsIgnoreCase("q")) break;
            com = command.split(" ");
            //ADD to Person
            if (com[0].equals("ADD")){
                addSetup();
            }
            if (com[0].equalsIgnoreCase("help")){
                System.out.println("/-----------------------------------------------");
                System.out.println("|HELP menu:");
                System.out.println("| ADD                 adds a new person to the DB");
                System.out.println("| SELECT  arg1, arg2... FROM db WHERE conditional");
                System.out.println("|               selects the argument rows from db");
                System.out.println("|               where condition is met");
                System.out.println("| UPDATE arg value IN db WHERE condition");
                System.out.println("|               updates value for arg where condition");
                System.out.println("| DROP arg (FROM db WHERE conndition) ");
                System.out.println("|               deletes field arg");
                System.out.println("\\------------------------------------------------");
            }
            if (com[0].equalsIgnoreCase("SELECT")){

            }
        } */

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

        //System.out.println("firstnametable: "+firstNameTable.get(key));

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


    public static void addSetup() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter first name: ");
        String firstName = input.nextLine();

        System.out.println("Please enter last name: ");
        String lastName = input.nextLine();

        Hashtable<String, String> phone = new Hashtable<>();
        System.out.println("Would you like to add a number for work-phone? yes/no");
        while (true) {
            String nextInput = input.nextLine();
            if (nextInput.equals("yes")) {
                System.out.println("Please enter number: ");
                phone.put("Work", input.nextLine());
                break;
            } else if (nextInput.equals("no")) {
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        System.out.println("Would you like to add a number for home-phone? yes/no");
        while (true) {
            String nextInput = input.nextLine();
            if (nextInput.equals("yes")) {
                System.out.println("Please enter number: ");
                phone.put("Personal", input.nextLine());
                break;
            } else if (nextInput.equals("no")) {
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        Hashtable<String, String> email = new Hashtable<>();
        System.out.println("Would you like to add a work-email? yes/no");
        while (true) {
            String nextInput = input.nextLine();
            if (nextInput.equals("yes")) {
                System.out.println("Please enter email: ");
                email.put("Work", input.nextLine());
                break;
            } else if (nextInput.equals("no")) {
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        System.out.println("Would you like to add a home-email? yes/no");
        while (true) {
            String nextInput = input.nextLine();
            if (nextInput.equals("yes")) {
                System.out.println("Please enter email: ");
                email.put("Personal", input.nextLine());
                break;
            } else if (nextInput.equals("no")) {
                break;
            } else {
                System.out.println("Invalid answer");
            }
        }

        System.out.println("Please enter address: ");
        String address = input.nextLine();

        System.out.println("Please enter birthdate mm/dd/yyyy");
        String date = input.nextLine();

        addPerson(firstName, lastName, phone, email, address, new SimpleDateFormat(date));
    }

    public static void search(String searchWord){

        boolean found = false;

        System.out.println("You searched for: " + searchWord);

        for (int id: ids){

            if (firstNameTable.get(id).toUpperCase()
                    .contains(searchWord.toUpperCase()) ||
                (lastNameTable.get(id).toUpperCase()
                        .contains(searchWord.toUpperCase())) ||
                (Arrays.toString(phoneTable.get(id).entrySet().toArray()).toUpperCase()
                        .contains(searchWord.toUpperCase()))) {

                System.out.println("Search worked!\n");
                printPerson(id);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Did not find what you were searching for.");
        }
    }
}
