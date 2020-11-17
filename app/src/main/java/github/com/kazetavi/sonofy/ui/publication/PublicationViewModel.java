package github.com.kazetavi.sonofy.ui.publication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;

public class PublicationViewModel extends ViewModel {

    private MutableLiveData<Publication> publication = new MutableLiveData<>();

    public MutableLiveData<Publication> getPublication() {
        return publication;
    }

    public void loadPublication(String publicationId){
        PublicationFirestore.getPublication(publicationId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                publication.postValue(documentSnapshot.toObject(Publication.class));

            }
        });
    }
}
