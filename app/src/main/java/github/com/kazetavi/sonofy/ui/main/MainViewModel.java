package github.com.kazetavi.sonofy.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.model.Publication;

public class MainViewModel extends ViewModel {
    MutableLiveData<List<Publication>> publications = new MutableLiveData<>();

    MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    void loadPublications(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<Publication> publicationsList = new ArrayList<>();

        db.collection("publications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String uid = document.getId();
                                String titre = document.getData().get("titre").toString();
                                String videoId = document.getData().get("video_id").toString();
                                long likeCount = (long) document.getData().get("like_count");
                                long dislikeCount = (long) document.getData().get("dislike_count");
                                publicationsList.add(new Publication(uid, titre, videoId, likeCount, dislikeCount));
                            }
                            publications.setValue(publicationsList);
                        }
                    }
                });

    }
}
