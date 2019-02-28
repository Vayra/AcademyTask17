package no.noroff.task17.controllers;

import no.noroff.task17.Task17Application;
import no.noroff.task17.models.contact;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static no.noroff.task17.Task17Application.*;

@RestController
public class contactController {
    AtomicInteger id = new AtomicInteger(lastID);
    @PostMapping("/contact")
    public contact createContact(@RequestBody contact newContact){
        newContact.setContactID(""+id.incrementAndGet());
        insertContact(newContact);
        readContact();
        return newContact;
    }

    @GetMapping("/contact")
    public List<contact> contactFindDefault(){
        System.out.println("Returning default search of ALL contacts");

        ArrayList<contact> retContacts = new ArrayList<>(contacts);

        return retContacts;
    }

    @GetMapping("/contact/{search}/{ID}")
    public List<contact> contactFind(@PathVariable String search, @PathVariable String ID) {

        System.out.println("Trying to find contact with ID= " + ID);
        ArrayList<contact> retContacts = new ArrayList<>();
        Map<String, String> phone;

        if (search.equalsIgnoreCase("id")){
            System.out.println("Searching by ID");

            for (contact con : Task17Application.contacts){
                if (con.getContactID().equalsIgnoreCase(ID)) {
                    System.out.println("---- Found contact by ID");
                    if (!retContacts.contains(con))
                        retContacts.add(con);
                }
            }
        }

        if (search.toLowerCase().contains("first")){
            System.out.println("Searching by first name");

            for (contact con : Task17Application.contacts){
                if (con.getFirstName().toLowerCase().contains(ID.toLowerCase())) {
                    System.out.println("---- Found contact by first name");
                    if (!retContacts.contains(con))
                        retContacts.add(con);
                }
            }
        }
        if (search.toLowerCase().contains("last")){
            System.out.println("Searching by last name");

            for (contact con : Task17Application.contacts){
                if (con.getLastName().toLowerCase().contains(ID.toLowerCase())) {
                    System.out.println("---- Found contact by last name");
                    if (!retContacts.contains(con))
                        retContacts.add(con);
                }
            }
        }

        if (search.toLowerCase().contains("phone")){
            System.out.println("Searching by phone number");

            for (contact con : Task17Application.contacts) {
                phone = con.getPhone();

                for (String num :phone.values()){
                    if (num != null) {
                        if (num.contains(ID)) {
                            System.out.println("----- Found contact by Phone");
                            if (!retContacts.contains(con)) retContacts.add(con);
                        }
                    }
                }
            }
        }

        return retContacts;
    }
    @GetMapping("/contact/{ID}")
    public List<contact> contactFind(@PathVariable String ID){

        System.out.println("Trying to find contact with ID= " + ID);
        ArrayList<contact> retContacts = new ArrayList<>();
        Map<String, String> phone;
        System.out.println("ID: " + ID);
        if (ID.equals(" ")) return  retContacts; // avoid error if empty ID

        //Search the contact list for the ID
        for (contact con : Task17Application.contacts){
            if (    con.getFirstName().toLowerCase().contains(ID.toLowerCase()) ||
                    con.getLastName().toLowerCase().contains(ID.toLowerCase())  ||
                    (con.getFirstName().toLowerCase() + " " +
                            con.getLastName().toLowerCase()).contains(ID.toLowerCase())){
                System.out.println("----- Found contact by Name");
                if (!retContacts.contains(con)) retContacts.add(con);
            }


            phone = con.getPhone();

            for (String num :phone.values()){
                if (num != null) {
                    if (num.contains(ID)) {
                        System.out.println("----- Found contact by Phone");
                        if (!retContacts.contains(con)) retContacts.add(con);
                    }
                }
            }

            /*
            if (con.getContactID().equals(ID)){
                System.out.println("----- Found contact by ID");
                if (!retContacts.contains(con)) retContacts.add(con);
            }*/

        }

        if (retContacts == null){
            System.out.println("----- No contact found!");
        }

        return retContacts;
    }
}
