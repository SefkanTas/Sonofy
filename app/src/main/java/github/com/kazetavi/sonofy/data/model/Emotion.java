package github.com.kazetavi.sonofy.data.model;

import android.widget.ImageView;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Emotion {

    private String publicationId;
    private ImageView content;
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
    private String typeEmoji;



    public Emotion(String publicationId) {
        this.publicationId = this.publicationId;
        this.likeCount = 0L;
        this.dislikeCount = 0L;
        this.sadCount = 0L;
        this.angryCount = 0L;
        this.happyCount = 0L;
        this.superrCount = 0L;
        this.heoCount = 0L;
        this.typeEmoji = typeEmoji;
    }

    @DocumentId
    public String getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public String getTypeEmoji() {
        return typeEmoji;
    }

    public void setTypeEmoji(String typeEmoji) {
        this.typeEmoji = typeEmoji;
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

    public Long getHappyCount() {
        return happyCount;
    }


    public Long getAngryCount() {
        return angryCount;
    }


    public Long getSadCount() {
        return sadCount;
    }

    public void setSadCont(Long sadCount) { this.sadCount = sadCount; }


    public Long getSuperrCount() {
        return superrCount;
    }


    public Long getHeoCount() {
        return heoCount;
    }
}
