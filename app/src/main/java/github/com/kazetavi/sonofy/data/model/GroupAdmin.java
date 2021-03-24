package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class GroupAdmin {
    private String uid;
    private String groupId;
    private String userId;
    @ServerTimestamp
    private Date dateCreated;

    public GroupAdmin() {
    }

    public String getUid() {
        return uid;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
