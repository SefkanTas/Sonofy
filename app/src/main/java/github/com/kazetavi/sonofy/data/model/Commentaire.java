package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Commentaire {

    private String uid;
    private String publicationId;
    private String content;
    private Long likeCount;
    private Long dislikeCount;
    @ServerTimestamp
    private Date dateCreated;
    private String username;
    private String userId;

    public Commentaire() {

    }

    public Commentaire(String publicationId, String content, String username, String userId) {
        this.publicationId = publicationId;
        this.content = content;
        this.username = username;
        this.userId = userId;
        likeCount = 0L;
        dislikeCount = 0L;
    }


    @DocumentId
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUsername() {
        return username;
    }
}
