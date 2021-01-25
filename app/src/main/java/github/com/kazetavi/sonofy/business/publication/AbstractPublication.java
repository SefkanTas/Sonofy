package github.com.kazetavi.sonofy.business.publication;

import java.util.Set;

import github.com.kazetavi.sonofy.business.User;
import github.com.kazetavi.sonofy.business.image.Pictureable;

public abstract class AbstractPublication implements Publication{

    private User author;
    private Pictureable miniature;

    private long likeCount;
    private long dislikeCount;

    private long likedUserSet;
    private long dislikedUserSet;

    @Override
    public User getAuthor() {
        return author;
    }

    @Override
    public String getPicturePath(String id) {
        return miniature.getPicturePath(id);
    }

    @Override
    public void setPicturePath(String id) {
        miniature.setPicturePath(id);
    }

    @Override
    public void removePicturePath() {
        miniature.removePicturePath();
    }

    @Override
    public long getDislike() {
        return dislikeCount;
    }

    @Override
    public void setDislike(long dislike) {
        this.dislikeCount = dislike;
    }

    @Override
    public void incrementDislike() {
        dislikeCount++;
    }

    @Override
    public void resetDislike() {
        dislikeCount = 0;
    }

    @Override
    public long getLike() {
        return likeCount;
    }

    @Override
    public void setLike(long like) {
        likeCount = like;
    }

    @Override
    public void incrementLike() {
        likeCount++;
    }

    @Override
    public void decrementLike() {
        likeCount++;
    }

    @Override
    public void resetLike() {
        likeCount = 0;
    }
}
