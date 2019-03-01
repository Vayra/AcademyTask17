package no.noroff.task17.controllers;

import no.noroff.task17.models.family;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static no.noroff.task17.Task17Application.*;

@RestController
public class familyController {

    @PostMapping("/family")
    public family createFammily(@RequestBody family fam){
        System.out.println("ID: " + fam.getContactID());
        System.out.println("RelativeID: " + fam.getRelativeID());
        System.out.println("RelationshipID: " + fam.getRelationshipID());
        insertFamily(fam);
        String reciprocRelation;
        switch(fam.getRelationshipID()) {
            case "1":
                reciprocRelation = "2";
                break;
            case "2":
                reciprocRelation = "1";
                break;
            case "3":
                reciprocRelation = "3";
                break;
            default:
                reciprocRelation = fam.getRelationshipID();
        }
        insertFamily(fam.getRelativeID(), fam.getContactID(), reciprocRelation);

        return fam;
    }


    @GetMapping("/family")
    public ArrayList<family>  familyFindDefault(){
        System.out.println("Returning default search of ALL families");
        ArrayList<family> returnFamilies = new ArrayList<>(families);
        return returnFamilies;
    }

    @GetMapping("/family/{ID}")
    public ArrayList<family> familyFind(@PathVariable String ID)
    {
        System.out.println("Trying to find relative: " + ID);
        ArrayList<family> returnFamilies = new ArrayList<>();

        for (family fam : families)
        {
            String cID = fam.getContactID().split("/")[fam.getContactID().split("/").length - 1];
            String rID = fam.getRelativeID().split("/")[fam.getRelativeID().split("/").length - 1];
            System.out.println(cID);
            System.out.println(rID);
            if (cID.equals(ID) || rID.equals(ID))
            {
                System.out.println("----- FAMILY FOUND ---- ");
                if (!returnFamilies.contains(fam)) returnFamilies.add(fam);
            }
        }
        if(returnFamilies == null){
            System.out.println("NOT FOUND");

        }
        return returnFamilies;
    }
}
