package github.com.kazetavi.sonofy.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;

public class MainViewModel extends ViewModel {


    private final MutableLiveData<List<Publication>> publications = new MutableLiveData<>();
    private final MutableLiveData<Groupe> groupeMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    public MutableLiveData<Groupe> getGroupeMutableLiveData() {
        return groupeMutableLiveData;
    }

    public void getGroupe(String uid){
        GroupeFirestore.getGroupWithId(uid).addOnSuccessListener(
                documentSnapshot -> groupeMutableLiveData.setValue(documentSnapshot.toObject(Groupe.class))
        );
    }


    public void loadPublicationsTitre(String groupeId){
        final List<Publication> publicationsList = new ArrayList<>();


            PublicationFirestore.getAllPublicationsCollectionTitreByGroupe(groupeId)
                    .addSnapshotListener((value, error) -> {
                        publicationsList.clear();
                        if(value != null){
                            for (QueryDocumentSnapshot doc : value) {
                                publicationsList.add(doc.toObject(Publication.class));
                                publications.setValue(publicationsList);
                            }
                        }
                    });
    }

    public void loadPublicationsDate(String groupId) {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getPublicationsByGroup(groupId)
                .addSnapshotListener((value, error) -> {
                    publicationsList.clear();
                    for (QueryDocumentSnapshot doc : value) {
                        publicationsList.add(doc.toObject(Publication.class));
                        publications.setValue(publicationsList);
                    }
                });
    }

    void loadPublicationsLike(String groupId) {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getAllPublicationsCollectionLikeByGroup(groupId)
                .addSnapshotListener((value, error) -> {
                    publicationsList.clear();
                    if(value != null){
                        for (QueryDocumentSnapshot doc : value) {
                            publicationsList.add(doc.toObject(Publication.class));
                            publications.setValue(publicationsList);
                        }
                    }
                });
    }
}
