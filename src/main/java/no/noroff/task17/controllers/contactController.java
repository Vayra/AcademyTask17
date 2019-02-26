package no.noroff.task17.controllers;

import no.noroff.task17.Task17Application;
import no.noroff.task17.models.contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class contactController {

    @GetMapping("/contact/{ID}")
    public contact contactFind(@PathVariable String ID){
        System.out.println("Trying to find contact with ID= " + ID);
        contact retContact = null;

        //Search the contact list for the ID
        for (contact con : Task17Application.contacts){
            if (con.getContactID().equals(ID)){
                System.out.println("----- Found contact");
                retContact = con;
            }
        }

        if (retContact == null){
            System.out.println("----- No contact found!");
        }

        return retContact;
    }
}