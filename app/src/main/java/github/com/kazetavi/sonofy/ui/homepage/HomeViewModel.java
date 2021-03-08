package github.com.kazetavi.sonofy.ui.homepage;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;

public class HomeViewModel extends ViewModel {


    private final MutableLiveData<List<Publication>> publications = new MutableLiveData<>();

    public MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    public void loadPublicationsDate() {
        final List<Publication> publicationsList = new ArrayList<>();
        PublicationFirestore.getAllPublicationsCollectionDesc()
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
