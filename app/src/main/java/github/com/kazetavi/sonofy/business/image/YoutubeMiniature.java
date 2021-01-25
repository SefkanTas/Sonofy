package github.com.kazetavi.sonofy.business.image;

public class YoutubeMiniature implements Pictureable{

    @Override
    public String getPicturePath(String id) {
        return new StringBuilder("https://img.youtube.com/vi/")
                .append(id)
                .append("/mqdefault.jpg")
                .toString();
    }

    @Override
    public void setPicturePath(String path) {
        //TODO
    }

    @Override
    public void removePicturePath() {
        //TODO
    }

}
