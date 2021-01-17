package github.com.kazetavi.sonofy.ui.search;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;

public class SearchViewModelPublication extends ViewModel {

    MutableLiveData<List<Publication>> publications = new MutableLiveData<>();

    MutableLiveData<List<Publication>> getPublications() {
        return publications;
    }


    void searchPublicationsTitle(String titre, String groupeId) {
        final List<Publication> publicationsList = new ArrayList<>();

        PublicationFirestore.searchByTitleAndGroupe(titre, groupeId)
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
}
