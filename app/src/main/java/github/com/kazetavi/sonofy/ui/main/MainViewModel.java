package github.com.kazetavi.sonofy.ui.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;

public class MainViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();

    MutableLiveData<List<Publication>> publications = new MutableLiveData<>();

    MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    void loadPublications(){
        final List<Publication> publicationsList = new ArrayList<>();

        PublicationFirestore.getAllPublicationsCollectionDesc()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        publicationsList.clear();
                        for(QueryDocumentSnapshot doc : value){
                            publicationsList.add(doc.toObject(Publication.class));
                            publications.setValue(publicationsList);
                        }
                    }
                });

    }
}
