package no.noroff.task17.controllers;

import no.noroff.task17.models.family;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static no.noroff.task17.Task17Application.families;

@RestController
public class familyController {

    @GetMapping("/family{ID}")
    public family familyFind(@PathVariable String ID)
    {
        System.out.println("Trying to find relative: " + ID);
        family returnFamily = null;
        for (family fam : families)
        {
            if (fam.getRelativeID().equals(ID))
            {
                System.out.println("----- FAMILY FOUND ---- ");
                returnFamily = fam;
            }
        }
        if(returnFamily == null){
            System.out.println("NOT FOUND");

        }
        return returnFamily;
    }
}
