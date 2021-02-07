package github.com.kazetavi.sonofy.business;


import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class YoutubePublicationFactory {

    /**
     * Vérifie que la vidéo youtube existe en faisant un appel http vers la miniature
     * de la vidéo youtube.
     * Si le code de retour est 200 alors la miniature existe et donc la vidéo existe.
     * @param ressourceId id de la vidéo youtube
     * @return Observable<Boolean>
     */
    public Observable<Boolean> ressourceExists(String ressourceId){

        final OkHttpClient client = new OkHttpClient();

        String videoUrl = "https://i.ytimg.com/vi/" + ressourceId + "/mqdefault.jpg";

        final Request request = new Request.Builder()
                .url(videoUrl)
                .build();

        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return client.newCall(request).execute().code() == 200;
            }
        });

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
            videoId = url;
        }

        return videoId;
    }

}
