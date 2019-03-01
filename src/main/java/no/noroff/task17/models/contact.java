package no.noroff.task17.models;

import com.fasterxml.jackson.databind.util.JSONPObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class contact {
    private String contactID;
    private String firstName;
    private String lastName;
    private String address;
    private String dob;
    private Map<String, String> email;
    private Map<String, String> phone;

    public contact() {}


    public contact(String contactID, String firstName, String lastName, String address, String dob, Map<String, String> email, Map<String, String> phone) {
        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
    }

    public contact(String contactID, String firstName, String lastName, String address, String dob,
                   String personalPhone, String workPhone, String homePhone,
                   String workEmail, String personalEmail){
        Map<String, String> mail = new Hashtable<>();
        Map<String, String> phone = new Hashtable<>();

        System.out.println("Creating Contact from only string values");
        System.out.println(firstName);
        System.out.println(lastName);
        System.out.println(address);
        System.out.println(dob);
        System.out.println(personalPhone);
        System.out.println(homePhone);
        System.out.println(workPhone);
        System.out.println(personalEmail);
        System.out.println(workEmail);


        phone.put("Personal", personalPhone);
        phone.put("Home", homePhone);
        phone.put("Work", workPhone);

        System.out.println("Email: " + personalEmail);
        System.out.println("Email: " + workEmail);
        mail.put("Personal", personalEmail);
        mail.put("Work", workEmail);

        this.contactID = contactID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.email = mail;
        this.phone = phone;

    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Map<String, String> getEmail() {
        return email;
    }

    public void setEmail(Map<String, String> email) {
        this.email = email;
    }

    public Map<String, String> getPhone() {
        return phone;
    }

    public void setPhone(Map<String, String> phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
