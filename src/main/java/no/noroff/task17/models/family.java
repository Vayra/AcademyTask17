package no.noroff.task17.models;

public class family {
    private String relativeID;
    private String relationshipID;
    private String contactID;

    public family() {
    }

    public family(String contactID, String relativeID, String relationshipID) {
        this.relativeID = relativeID;
        this.relationshipID = relationshipID;
        this.contactID = contactID;
    }

    public String getRelativeID(){
        return relativeID;
    }

    public String getRelationshipID(){
        return relationshipID;
    }

    public String getContactID() {
        return contactID;
    }

    public void setRelativeID(String relativeID) {
        this.relativeID = relativeID;
    }

    public void setRelationshipID(String relationshipID) {
        this.relationshipID = relationshipID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}
