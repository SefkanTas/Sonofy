package github.com.kazetavi.sonofy.ui.publication;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.CommentaireFirestore;
import github.com.kazetavi.sonofy.data.api.EmotionFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;

public class PublicationViewModel extends ViewModel {

    private final MutableLiveData<Publication> publicationLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Commentaire>> commentaires = new MutableLiveData<>();
    private MutableLiveData<User> authorUserLiveData = new MutableLiveData<>();
    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();

    public MutableLiveData<Publication> getPublicationLiveData() {
        return publicationLiveData;
    }

    public MutableLiveData<List<Commentaire>> getCommentaires() {
        return commentaires;
    }

    public MutableLiveData<User> getAuthorUserLiveData() {
        return authorUserLiveData;
    }

    public void loadPublication(final String publicationId){
        PublicationFirestore.getPublication(publicationId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Publication publication = documentSnapshot.toObject(Publication.class);
                publicationLiveData.postValue(publication);
                loadAuthorUser(publication);
            }
        });
    }

    public void loadAuthorUser(Publication publication){
        UserFirestore.getUser(publication.getAuthorId()).addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            authorUserLiveData.postValue(user);
        });
    }

    public void loadCurrentUser(String userId){
        UserFirestore.getUser(userId).addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            currentUserLiveData.postValue(user);
        });
    }

    public void removeOldEmotion(String userId, String publicationId, String newEmotionId){
        EmotionFirestore.deleteByUserAndPublication(userId, publicationId, newEmotionId);
    }

    public void addEmotion(String emotionName){
        if(publicationLiveData.getValue() == null || currentUserLiveData.getValue() == null){
            return;
        }
        Publication pub = publicationLiveData.getValue();
        User currentUser = currentUserLiveData.getValue();

        Emotion emotion = new Emotion(emotionName, currentUser.getUserId(), pub.getUid());
        EmotionFirestore.create(emotion).addOnSuccessListener(documentReference ->
                removeOldEmotion(currentUser.getUserId(), pub.getUid(), documentReference.getId()));

    }


//    public void loadCommentaires(String publicationId){
//        final List<Commentaire> commentaireList = new ArrayList<>();
//
//        CommentaireFirestore.getCollectionQueryByPublication(publicationId)
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        commentaireList.clear();
//                        for(QueryDocumentSnapshot doc : value){
//                            commentaireList.add(doc.toObject(Commentaire.class));
//                        }
//                        commentaires.setValue(commentaireList);
//                    }
//                });
//    }
//
//    public void createCommentaire(String publicationId, String content,String username ,String userId){
//        Commentaire commentaire = new Commentaire(publicationId, content,username, userId);
//        content = content.trim();
//        if(!content.isEmpty()){
//            CommentaireFirestore.create(commentaire);
//        }
//    }

}
