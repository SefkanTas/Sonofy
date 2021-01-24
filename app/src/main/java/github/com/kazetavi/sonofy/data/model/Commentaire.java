package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
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
    private Long sadCount;
    private Long angryCount;
    private Long happyCount;
    private Long superrCount;
    private Long heoCount;

    public Commentaire() {

    }

    public Commentaire(String publicationId, String content, String username) {
        this.publicationId = publicationId;
        this.content = content;
        this.username = username;
        likeCount = 0L;
        dislikeCount = 0L;
        sadCount = 0L;
        angryCount = 0L;
        happyCount = 0L;
        superrCount = 0L;
        heoCount = 0L;
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

    public Long getHappyCount() {
        return happyCount;
    }

    public void setHappyCount(Long likeCount) {
        this.happyCount = happyCount;
    }

    public Long getAngryCount() {
        return angryCount;
    }

    public void setAngryCount(Long likeCount) {
        this.angryCount = angryCount;
    }

    public Long getSadCount() {
        return sadCount;
    }

    public void setSadCount(Long likeCount) {
        this.sadCount = sadCount;
    }

    public Long getSuperrCount() {
        return superrCount;
    }

    public void setSupperrCount(Long likeCount) {
        this.superrCount = superrCount;
    }

    public Long getHeoCount() {
        return heoCount;
    }

    public void setHeoCount(Long likeCount) {
        this.heoCount = heoCount;
    }
}
