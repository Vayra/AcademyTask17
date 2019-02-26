package no.noroff;

public class Relation {
    private String relationshipID;
    private String kind;

    public Relation(String relationshipID, String kind) {
        this.relationshipID = relationshipID;
        this.kind = kind;

    }

    public String getRelationshipID(){
        return relationshipID;
    }

    public String getKind(){
        return kind;
    }

    public void setRelationshipID(String relationshipID) {
        this.relationshipID = relationshipID;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}

