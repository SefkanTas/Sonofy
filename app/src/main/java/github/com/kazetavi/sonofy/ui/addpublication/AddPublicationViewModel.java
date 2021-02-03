package github.com.kazetavi.sonofy.ui.addpublication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import github.com.kazetavi.sonofy.business.YoutubePublication;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;


public class AddPublicationViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private final MutableLiveData<Boolean> isPublicationSaved = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> isPublicationSaved() {
        return isPublicationSaved;
    }

    public MutableLiveData<Boolean> isLoading(){
        return isLoading;
    }

    /**
     * Sauvegarde une publication dans Firestore (notre base de données)
     * @param titre
     * @param videoId
     */
    public void savePublication(String titre, String videoId, String groupId){
        Publication publication = new Publication(titre, videoId, groupId);
        Log.d(TAG, "savePublication: saving publication : " + titre);
        PublicationFirestore.createPublication(publication)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: publication saved : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Vérifie si la vidéo existe et fait appel savePublication pour créer la publication
     */
    public void addPublication(String titre, String sourceUrl, final String groupId){
        isLoading.postValue(true);

        YoutubePublication youtubePublication = new YoutubePublication();

        String videoId = youtubePublication.getVideoIdFromUrl(sourceUrl);
        boolean videoExists = youtubePublication.ressourceExists(videoId);

        if(videoExists){
            Log.i(TAG, "video exists, saving publication");
            savePublication(titre, videoId, groupId);
            Log.i(TAG, "addPublication: publication saved");
        }
        else {
            isPublicationSaved.postValue(false);
        }

        isLoading.postValue(false);


//        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
//        Pattern compiledPattern = Pattern.compile(pattern);
//        Matcher matcher = compiledPattern.matcher(videoId); //url is youtube url for which you want to extract the id.
//        if (matcher.find()) {
//            videoId = matcher.group();
//        }

//        final String fVideoId = videoId;
//        String videoUrl = "https://i.ytimg.com/vi/" + videoId + "/mqdefault.jpg";
//        Log.d(TAG, "addPublication: fetching video : " + videoUrl);
//        final Request request = new Request.Builder()
//                .url(videoUrl)
//                .build();
//
//        // Vérifie si la musique existe, en utilisant le lien
//        // et les codes http
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onResponse(Response response) throws IOException {
//                response.code();
//                if(response.code() == 200){
//                    savePublication(fTitre, fVideoId, groupId);
//                    isPublicationSaved.postValue(true);
//                    Log.d(TAG, "client.onResponse: code is 200 : " + request.urlString());
//                }
//                else {
//                    isPublicationSaved.postValue(false);
//                    Log.d(TAG, "client.onResponse: code is not 200 : " + request.urlString());
//                }
//                isLoading.postValue(false);
//            }
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.w(TAG, "onFailure: requestFailed");
//                isLoading.postValue(false);
//            }
//
//        });
    }
}
