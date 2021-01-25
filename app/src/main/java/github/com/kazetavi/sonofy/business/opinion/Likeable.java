package github.com.kazetavi.sonofy.business.opinion;

public interface Likeable {
    long getLike();
    void setLike(long like);
    void incrementLike();
    void decrementLike();
    void resetLike();
}
