package no.noroff.task17.controllers;

import no.noroff.task17.models.contact;
import no.noroff.task17.models.family;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

import static no.noroff.task17.Task17Application.*;

@RestController
public class familyController {

    @GetMapping("delete/family/{contactID}/{relativeID}")
    public String deleteFamily(@PathVariable String contactID, @PathVariable String relativeID){

        if(deleteFamilyFromTable(contactID,relativeID)) return "Delete successful";
        return "Delete failed";
    }

    @PostMapping("update/family")
    public family updateFamily(@RequestBody Map<String, String> updateBody){
        String conID = updateBody.get("contactID");
        String relID = updateBody.get("relativeID");
        String kind = updateBody.get("relationshipID");
        if(updateFamilyTable(conID, relID,kind) && updateFamilyTable(relID, conID, getReciprocalRel(kind))){
            System.out.println("Update successful");
        }
        else System.out.println("Update failed");
        readFamily();
        return getFamily(conID, relID);

    }

    @RequestMapping("/familySearch/")
    public ArrayList<family> findFamilyByName(@RequestParam(value="search", defaultValue ="Stian") String ID){
        ArrayList<contact> cons = new ArrayList<>();
        System.out.println("ID: " + ID);
        for (contact c:contacts){
            if ((c.getFirstName() + " " + c.getLastName()).toLowerCase().contains(ID.toLowerCase())){
                if(!cons.contains(c)) cons.add(c);
            }
            Map<String, String> phone = c.getPhone();
            for (String num : phone.values()){

                if (num != null){
                    if (num.contains(ID)){
                        System.out.println("I should not be here");
                        if (!cons.contains(c)) cons.add(c);
                    }
                }
            }
        }

        System.out.println("Trying to find relative: " + ID);
        ArrayList<family> returnFamilies = new ArrayList<>();

        if (cons.size() == 0) return returnFamilies;

        for (family fam : families)
        {
            String cID = fam.getContactID().split("/")[fam.getContactID().split("/").length - 1];
            //System.out.println(cID);
            for (contact con: cons) {
                //System.out.println(con.getFirstName());
                if (cID.equals(con.getContactID())) {
                    System.out.println("----- FAMILY FOUND ---- ");
                    if (!returnFamilies.contains(fam)) returnFamilies.add(fam);
                }
            }
        }
        if(returnFamilies == null){
            System.out.println("NOT FOUND");

        }
        return returnFamilies;

    }

    @PostMapping("/family")
    public family createFammily(@RequestBody family fam){
        System.out.println("ID: " + fam.getContactID());
        System.out.println("RelativeID: " + fam.getRelativeID());
        System.out.println("RelationshipID: " + fam.getRelationshipID());
        insertFamily(fam);
        String reciprocRelation = getReciprocalRel(fam.getRelationshipID());

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

            if (cID.equals(ID) || rID.equals(ID))
            {
                System.out.println("----- FAMILY FOUND ---- ");
                if (!returnFamilies.contains(fam)) returnFamilies.add(fam);
            }
        }
        if(returnFamilies.isEmpty()){
            System.out.println("NOT FOUND");

        }
        return returnFamilies;
    }
}
