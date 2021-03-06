package github.com.kazetavi.sonofy.business;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class SoundcloudPublicationFactory {

    public String getVideoIdFromUrl(String url){

        String videoId;

        String pattern = "https://soundcloud.com/";
        if(url.contains(pattern))
            videoId = url;
        else
            videoId = pattern + url;
        //soundcloud
        return videoId;
    }

}
