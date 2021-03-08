package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Publication {

    @Exclude
    private String uid;
    private String titre;
    private String videoId;
    private Long likeCount;
    private Long dislikeCount;
    private String groupId;
    private String authorId;
    @ServerTimestamp
    private Date dateCreated;

    //Constructeur vide necessaire pour firebase
    public Publication(){

    }

    public Publication(String titre, String videoId) {
        this.titre = titre;
        this.videoId = videoId;
        this.likeCount = 0L;
        this.dislikeCount = 0L;
    }

    public Publication(String titre, String videoId, String groupId, String authorId) {
        this(titre,videoId);
        this.groupId = groupId;
        this.authorId = authorId;
    }

    @DocumentId
    public String getUid() {
        return uid;
    }

    public String getTitre() {
        return titre;
    }

    public String getVideoId() {
        return videoId;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public Long getDislikeCount() {
        return dislikeCount;
    }

    public Date getDateCreated() {
        return dateCreated;
    };

    public String getGroupId() {
        return groupId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Exclude
    public String getMiniatureUrl(){
        return new StringBuilder("https://img.youtube.com/vi/")
                .append(this.videoId)
                .append("/mqdefault.jpg")
                .toString();
    }


    ////soundcloud link => image
    @Exclude
    public String getVideoUrl(){
        return new StringBuilder("https://www.youtube.com/watch?v=")
                .append(this.videoId)
                .toString();
    }
    ////soundcloud link => video
    public String getVideoUrlSoundCloud(){
     return new StringBuilder("https://soundcloud.com/")
                    .append(this.videoId)
                    .toString();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publication{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", videoId='").append(videoId).append('\'');
        sb.append(", likeCount=").append(likeCount);
        sb.append(", dislikeCount=").append(dislikeCount);
        sb.append('}');
        return sb.toString();
    }
}
