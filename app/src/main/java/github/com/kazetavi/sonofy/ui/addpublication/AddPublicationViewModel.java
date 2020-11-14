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


public class AddPublicationViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();

    final OkHttpClient client = new OkHttpClient();
    final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MutableLiveData<Boolean> isPublicationSaved = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> isPublicationSaved() {
        return isPublicationSaved;
    }

    public MutableLiveData<Boolean> isLoading(){
        return isLoading;
    }

    void savePublication(String titre, String videoId){
        Map<String, Object> publication = new HashMap<>();
        publication.put("titre", titre);
        publication.put("video_id", videoId);
        publication.put("like_count", 0);
        publication.put("dislike_count", 0);

        Log.d(TAG, "savePublication: saving publication : " + titre);
        db.collection("publications")
                .add(publication)
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

    void addPublication(String titre, String videoId){
        isLoading.postValue(true);
        final String fTitre = titre;
        final String fVideoId = videoId;
        String videoUrl = "https://i.ytimg.com/vi/" + videoId + "/mqdefault.jpg";

        Log.d(TAG, "addPublication: fetching video : " + videoUrl);
        Request request = new Request.Builder()
                .url(videoUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.w(TAG, "onFailure: requestFailed");
                isLoading.postValue(false);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                response.code();
                if(response.code() == 200){
                    savePublication(fTitre, fVideoId);
                    isPublicationSaved.postValue(true);
                    Log.d(TAG, "client.onResponse: code 200");
                }
                else {
                    isPublicationSaved.postValue(false);
                    Log.d(TAG, "client.onResponse: code not 200");
                }
                isLoading.postValue(false);

            }
        });
    }
}
