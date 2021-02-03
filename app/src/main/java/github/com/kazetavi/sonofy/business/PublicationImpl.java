package github.com.kazetavi.sonofy.business;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublicationImpl implements Publication{
    @Override
    public boolean isUrlValid(String url) {
//        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
//        Pattern compiledPattern = Pattern.compile(pattern);
//        Matcher matcher = compiledPattern.matcher(videoId); //url is youtube url for which you want to extract the id.
//        if (matcher.find()) {
//            videoId = matcher.group();
//        }
        return false;
    }
}
