package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class ListeEmoji {
    private String publicationId;
    @ServerTimestamp
    private Date dateCreated;
    private String uidUser;
    private String typeEmoji;

    public ListeEmoji(String publicationId, String typeEmoji, String uidUser) {
        this.publicationId = this.publicationId;
        this.typeEmoji = typeEmoji;
        this.uidUser = uidUser;
        this.dateCreated = dateCreated;
    }

    @DocumentId
    public String getPublicationId() {
        return publicationId;
    }
    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getUsername() {
        return uidUser;
    }
    public void setUsername(String uidUser) { this.uidUser = uidUser; }

    public String getTypeEmoji() {
        return typeEmoji;
    }
    public void setTypeEmoji(String typeEmoji) { this.typeEmoji =  typeEmoji; }

    public Date getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


}

