package github.com.kazetavi.sonofy.ui.publication;

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

import github.com.kazetavi.sonofy.data.api.CommentaireFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;

public class PublicationViewModel extends ViewModel {

    private final MutableLiveData<Publication> publication = new MutableLiveData<>();
    public MutableLiveData<Publication> getPublication() {
        return publication;
    }

    private final MutableLiveData<List<Commentaire>> commentaires = new MutableLiveData<>();
    public MutableLiveData<List<Commentaire>> getCommentaires() {
        return commentaires;
    }


    public void loadPublication(final String publicationId){
        PublicationFirestore.getPublication(publicationId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                publication.postValue(documentSnapshot.toObject(Publication.class));
                loadCommentaires(publicationId);
            }
        });
    }

    public void loadCommentaires(String publicationId){
        final List<Commentaire> commentaireList = new ArrayList<>();

        CommentaireFirestore.getCollectionQueryByPublication(publicationId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        commentaireList.clear();
                        for(QueryDocumentSnapshot doc : value){
                            commentaireList.add(doc.toObject(Commentaire.class));
                        }
                        commentaires.setValue(commentaireList);
                    }
                });
    }

    public void createCommentaire(String publicationId, String content,String username ,String userId){
        Commentaire commentaire = new Commentaire(publicationId, content,username, userId);
        content = content.trim();
        if(!content.isEmpty()){
            CommentaireFirestore.create(commentaire);
        }
    }

}
