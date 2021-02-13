package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

public class Emotion {

    private String uid;
    private String emotion;
    private String userId;
    private String publicationId;

    @ServerTimestamp
    private Timestamp dateCreated;

    public Emotion() {}

    public Emotion(String emotion, String userId, String publicationId) {
        this.emotion = emotion;
        this.userId = userId;
        this.publicationId = publicationId;
    }

    @DocumentId
    public String getUid() {
        return uid;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getUserId() {
        return userId;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }
}
