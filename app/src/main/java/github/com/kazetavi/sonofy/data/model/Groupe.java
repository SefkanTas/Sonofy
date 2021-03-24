package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Groupe {

    private String uid;
    private String name;
    @ServerTimestamp
    private Date dateCreated;
    private boolean isPrivate;

    public Groupe() {
    }

    public Groupe(String name, boolean isPrivate) {
        this.name = name;
        this.isPrivate = isPrivate;
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

    public boolean isPrivate() {
        return isPrivate;
    }
}
