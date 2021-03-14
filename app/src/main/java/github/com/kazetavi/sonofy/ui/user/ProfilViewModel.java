package github.com.kazetavi.sonofy.ui.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
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
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;

public class ProfilViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private final MutableLiveData<List<Publication>> publications = new MutableLiveData<>();

    private final MutableLiveData<List<Emotion>> emotionsLiveData = new MutableLiveData<>();

    private final MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }


    public MutableLiveData<List<Emotion>> getEmotionsLiveData() {
        return emotionsLiveData;
    }

    public MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    public void updateNom(String uid, String nom){
        UserFirestore.updateLastName(uid,nom);
    }

    public void updatePrenom(String uid, String prenom){
        UserFirestore.updateFirstName(uid,prenom);
    }

    /*public void updateEmail(String uid, String email){
    }*/

    public void updatePseudo(String uid, String pseudo){
        UserFirestore.updatePseudo(uid, pseudo);
    }

    /*public boolean pseudoAlreadyExists(final String uid, final String p){
        final boolean[] b = {true};
        UserFirestore.getPseudo(p).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size() == 0){
                    updatePseudo(uid, p);
                    b[0] = false;
                }else{
                    b[0] = true;
                }
            }
        });
        return b[0];
    }*/

    public void getUser(final String uid){
        UserFirestore.getUser(uid)
                .addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        userMutableLiveData.postValue(user);
                        Log.w(TAG, "utilisateur trouvé avec cet ID : " + uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Pas d'utilisateur trouvé avec cet ID :" + uid);
                    }
        });
    }


    public void loadPublicationsAuthor(String authorId) {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getPublicationByAuthorId(authorId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        publicationsList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            publicationsList.add(doc.toObject(Publication.class));
                            publications.setValue(publicationsList);
                        }
                    }
                });
    }


    public void loadEmotionsAuthor(String authorId) {
        final List<Emotion> emotionsList = new ArrayList<>();
        EmotionFirestore.getEmotionByAuthorId(authorId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        emotionsList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            emotionsList.add(doc.toObject(Emotion.class));
                            emotionsLiveData.setValue(emotionsList);
                        }
                    }
                });
    }

}
