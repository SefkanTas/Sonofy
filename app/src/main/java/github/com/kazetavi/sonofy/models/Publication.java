package github.com.kazetavi.sonofy.models;

public class Publication {

    private String uid;
    private String titre;
    private String videoId;
    private int likes;
    private int dislikes;


    public Publication(String uid, String titre, String videoId) {
        this.uid = uid;
        this.titre = titre;
        this.videoId = videoId;
        this.likes = 0;
        this.dislikes = 0;
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

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
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
        sb.append(", likes=").append(likes);
        sb.append(", dislikes=").append(dislikes);
        sb.append('}');
        return sb.toString();
    }
}
