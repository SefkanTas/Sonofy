package github.com.kazetavi.sonofy.ui.main;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;

public class MainViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    Button sortTitre;

    MutableLiveData<List<Publication>> publications = new MutableLiveData<>();
    MutableLiveData<Groupe> groupeMutableLiveData = new MutableLiveData<>();


    MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    public MutableLiveData<Groupe> getGroupeMutableLiveData() {
        return groupeMutableLiveData;
    }

    void getGroupe(String uid){
        GroupeFirestore.getGroupWithId(uid).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                groupeMutableLiveData.setValue(documentSnapshot.toObject(Groupe.class));
            }
        });
    }

    void loadPublicationsTitre(String groupeId){
        final List<Publication> publicationsList = new ArrayList<>();


            PublicationFirestore.getAllPublicationsCollectionTitreByGroupe(groupeId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            publicationsList.clear();
                            if(value != null){
                                for (QueryDocumentSnapshot doc : value) {
                                    publicationsList.add(doc.toObject(Publication.class));
                                    publications.setValue(publicationsList);
                                }
                            }
                        }
                    });
    }

    void loadPublicationsDate(String groupId) {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getPublicationsByGroup(groupId)
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

    void loadPublicationsLike(String groupId) {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getAllPublicationsCollectionLikeByGroup(groupId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        publicationsList.clear();
                        if(value != null){
                            for (QueryDocumentSnapshot doc : value) {
                                publicationsList.add(doc.toObject(Publication.class));
                                publications.setValue(publicationsList);
                            }
                        }
                    }
                });
    }
}
