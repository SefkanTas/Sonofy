package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Groupe {

    private String uid;
    private String name;
    @ServerTimestamp
    private Date dateCreated;

    public Groupe() {
    }

    public Groupe(String name) {
        this.name = name;
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
}
