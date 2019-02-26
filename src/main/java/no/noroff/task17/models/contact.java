package no.noroff.task17.models;

import java.text.SimpleDateFormat;
import java.util.Map;

public class contact {
    private String contactID;


    private String firstName;
    private String lastName;
    private String address;
    private SimpleDateFormat dob;
    private Map<String, String> email;
    private Map<String, String> phone;

    public contact(String firstName, String lastName, String address, SimpleDateFormat dob, Map<String, String> email, Map<String, String> phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dob = dob;
        this.email = email;
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

    public SimpleDateFormat getDob() {
        return dob;
    }

    public void setDob(SimpleDateFormat dob) {
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
