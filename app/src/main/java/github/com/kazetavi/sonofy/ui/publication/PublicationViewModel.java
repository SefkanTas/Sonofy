package github.com.kazetavi.sonofy.ui.publication;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.EmotionFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;

public class PublicationViewModel extends ViewModel {

    private final MutableLiveData<Publication> publication = new MutableLiveData<>();
    public MutableLiveData<Publication> getPublication() {
        return publication;
    }

    private final MutableLiveData<Emotion> emotion = new MutableLiveData<>();
    public MutableLiveData<Emotion> getEmotion() {
        return emotion;
    }

    private final MutableLiveData<List<Emotion>> emotions = new MutableLiveData<>();
    public MutableLiveData<List<Emotion>> getEmotions() {
        return emotions;
    }


    public void loadPublication(final String publicationId){
        PublicationFirestore.getPublication(publicationId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                publication.postValue(documentSnapshot.toObject(Publication.class));
                loadEmotions(publicationId);
            }
        });
    }

    public void loadEmotions(String publicationId){
        final List<Emotion> emotionsList = new ArrayList<>();

        EmotionFirestore.getCollectionQueryByPublication(publicationId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        emotionsList.clear();
                        for(QueryDocumentSnapshot doc : value){
                            emotionsList.add(doc.toObject(Emotion.class));
                        }
                        emotions.setValue(emotionsList);
                    }
                });
    }

    public void createEmotion(String publicationId){
        Emotion emotion = new Emotion(publicationId);

    }

}
