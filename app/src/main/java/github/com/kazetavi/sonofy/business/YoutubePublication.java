package github.com.kazetavi.sonofy.business;

import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;

public class YoutubePublication {

    /**
     * Vérifie que la vidéo youtube existe en faisant un appel http vers la miniature
     * de la vidéo youtube.
     * Si le code de retour est 200 alors la miniature existe et donc la vidéo existe.
     * @param ressourceId id de la vidéo youtube
     * @return si la video existe ou non
     */
    public boolean ressourceExists(String ressourceId){

        boolean ressourceExists;
        final OkHttpClient client = new OkHttpClient();
        Response response = null;

        String videoUrl = "https://i.ytimg.com/vi/" + ressourceId + "/mqdefault.jpg";

        final Request request = new Request.Builder()
                .url(videoUrl)
                .build();


        Observable.defer(new Supplier<ObservableSource<Response>>() {
            @Override
            public ObservableSource<Response> get() throws Throwable {
                Response response = client.newCall(request).execute();
                return Observable.just(response);            }
        });

        Observable.fromCallable(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return client.newCall(request).execute();
            }
        });

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.code() == 200){
            return true;
        }
        return false;


    }

    public String getVideoIdFromUrl(String url){

        String videoId;

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(url); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            videoId = matcher.group();
        }
        else {
            videoId = "";
        }

        return videoId;
    }
}
