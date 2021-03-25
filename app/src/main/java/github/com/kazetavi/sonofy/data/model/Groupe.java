package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class Groupe {

    private String uid;
    private String name;
    @ServerTimestamp
    private Date dateCreated;
    private Boolean isPrivate;

    private List<String> adminsId;
    private List<String> membersId;

    public Groupe() {
    }

    public Groupe(String name, Boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public Groupe(String name, Boolean isPrivate, List<String> adminsId, List<String> membersId) {
        this(name, isPrivate);
        this.adminsId = adminsId;
        this.membersId = membersId;
    }

    @DocumentId
    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<String> getAdminsId() {
        return adminsId;
    }

    public List<String> getMembersId() {
        return membersId;
    }

    public Boolean isAdmin(String userId){
        return adminsId.contains(userId);
    }

    public Boolean isMember(String userId){
        return membersId.contains(userId);
    }

}
