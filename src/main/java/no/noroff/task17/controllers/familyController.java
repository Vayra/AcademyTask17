package no.noroff.task17.controllers;

import no.noroff.task17.models.family;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static no.noroff.task17.Task17Application.families;

@RestController
public class familyController {

    @GetMapping("/family")
    public ArrayList<family>  familyFindDefault(){
        System.out.println("Returning default search of ALL families");
        ArrayList<family> returnFamilies = new ArrayList<>();
        for (family fam : families)
        {
            returnFamilies.add(fam);
        }
        return returnFamilies;
    }

    @GetMapping("/family/{ID}")
    public ArrayList<family> familyFind(@PathVariable String ID)
    {
        System.out.println("Trying to find relative: " + ID);
        ArrayList<family> returnFamilies = new ArrayList<>();

        for (family fam : families)
        {
            if (fam.getRelativeID().equals(ID) || fam.getContactID().equals(ID))
            {
                System.out.println("----- FAMILY FOUND ---- ");
                if (returnFamilies.contains(fam)) returnFamilies.add(fam);
            }
        }
        if(returnFamilies == null){
            System.out.println("NOT FOUND");

        }
        return returnFamilies;
    }
}
