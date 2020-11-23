package github.com.kazetavi.sonofy.ui.addpublication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;


public class AddPublicationViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();

    final OkHttpClient client = new OkHttpClient();

    private MutableLiveData<Boolean> isPublicationSaved = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

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
    void savePublication(String titre, String videoId){
        Publication publication = new Publication(titre, videoId);
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
     * @param titre
     * @param videoId
     */
    void addPublication(String titre, String videoId){
        isLoading.postValue(true);
        final String fTitre = titre;

        String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(videoId); //url is youtube url for which you want to extract the id.
        if (matcher.find()) {
            videoId = matcher.group();
        }

        final String fVideoId = videoId;
        String videoUrl = "https://i.ytimg.com/vi/" + videoId + "/mqdefault.jpg";
        Log.d(TAG, "addPublication: fetching video : " + videoUrl);
        final Request request = new Request.Builder()
                .url(videoUrl)
                .build();

        // Vérifie si la musique existe, en utilisant le lien
        // et les codes http
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                response.code();
                if(response.code() == 200){
                    savePublication(fTitre, fVideoId);
                    isPublicationSaved.postValue(true);
                    Log.d(TAG, "client.onResponse: code is 200 : " + request.urlString());
                }
                else {
                    isPublicationSaved.postValue(false);
                    Log.d(TAG, "client.onResponse: code is not 200 : " + request.urlString());
                }
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Request request, IOException e) {
                Log.w(TAG, "onFailure: requestFailed");
                isLoading.postValue(false);
            }

        });
    }
}
