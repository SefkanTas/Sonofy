package github.com.kazetavi.sonofy.business;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class SoundcloudPublicationFactory {


    public Observable<Boolean> ressourceExists(String id){

        final OkHttpClient client = new OkHttpClient();

        String url = "https://soundcloud.com/" + id;

        final Request request = new Request.Builder()
                .url(url)
                .build();

        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return client.newCall(request).execute().code() == 200;
            }
        });
    }
    public String getVideoIdFromUrl(String url){
        String videoId = url;
        return videoId;
    }

}