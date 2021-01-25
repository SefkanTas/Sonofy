package github.com.kazetavi.sonofy.business.opinion;

public interface Dislikeable {

    long getDislike();
    void setDislike(long dislike);
    void incrementDislike();
    void decrementDislike();
    void resetDislike();
}
