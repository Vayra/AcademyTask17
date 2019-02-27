package no.noroff.task17.controllers;

import no.noroff.task17.Task17Application;
import no.noroff.task17.models.contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class contactController {

    @GetMapping("/contact")
    public ArrayList<contact> contactFindDefault(){
        System.out.println("Returning default search of ALL");
        ArrayList<contact> retContacts = new ArrayList<>();
        Map<String, String> phone;

        for (contact con : Task17Application.contacts){
            if (!retContacts.contains(con)) retContacts.add(con);
        }

        return retContacts;
    }

    @GetMapping("/contact/{ID}")
    public ArrayList<contact> contactFind(@PathVariable String ID){
        System.out.println("Trying to find contact with ID= " + ID);
        ArrayList<contact> retContacts = new ArrayList<>();
        Map<String, String> phone;
        System.out.println("ID: " + ID);
        if (ID.equals(" ")) return  retContacts; // avoid error if empty ID

        //Search the contact list for the ID
        for (contact con : Task17Application.contacts){
            if (    con.getFirstName().contains(ID) ||
                    con.getLastName().contains(ID)    ){
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
