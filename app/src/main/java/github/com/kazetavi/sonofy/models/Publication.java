package github.com.kazetavi.sonofy.models;

public class Publication {

    private String uid;
    private String titre;
    private String videoId;
    private Long likeCount;
    private Long dislikeCount;
    private Long numComments;



    public Publication(String uid, String titre, String videoId) {
        this.uid = uid;
        this.titre = titre;
        this.videoId = videoId;
        this.likeCount = 0L;
        this.dislikeCount = 0L;
        this.numComments = 0L;

    }

    public Publication(String uid, String titre, String videoId, Long likes, Long dislikes, Long numComments) {
        this(uid, titre, videoId);
        this.likeCount = likes;
        this.dislikeCount = dislikes;
        this.numComments = numComments;
    }

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

    public Long getNumComments() {
        return numComments;
    }

    public String getMiniatureUrl(){
        return new StringBuilder("https://img.youtube.com/vi/")
                .append(this.videoId)
                .append("/mqdefault.jpg")
                .toString();
    }

    public String getVideoUrl(){
        return new StringBuilder("https://www.youtube.com/watch?v=")
                .append(this.videoId)
                .toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Publication{");
        sb.append("uid='").append(uid).append('\'');
        sb.append(", titre='").append(titre).append('\'');
        sb.append(", videoId='").append(videoId).append('\'');
        sb.append(", likes=").append(likeCount);
        sb.append(", dislikes=").append(dislikeCount);
        sb.append(", numComms=").append(numComments);
        sb.append('}');
        return sb.toString();
    }
}
