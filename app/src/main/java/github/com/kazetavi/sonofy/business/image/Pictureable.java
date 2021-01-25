package github.com.kazetavi.sonofy.business.image;

public interface Pictureable {

    String getPicturePath(String id);
    void setPicturePath(String path);
    void removePicturePath();
}
