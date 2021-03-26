package github.com.kazetavi.sonofy.ui.addpublication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

import github.com.kazetavi.sonofy.business.SoundcloudPublicationFactory;
import github.com.kazetavi.sonofy.business.YoutubePublicationFactory;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class AddPublicationViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();

    private final MutableLiveData<Boolean> isPublicationSaved = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> isPublicationSaved() {
        return isPublicationSaved;
    }

    public MutableLiveData<Boolean> isLoading() {
        return isLoading;
    }

    /**
     * Sauvegarde une publication dans Firestore (notre base de données)
     *
     * @param titre
     * @param videoId
     */
    public void savePublication(String titre, String videoId, String groupId, String authorId, String support) {
        Publication publication = new Publication(titre, videoId, groupId, authorId, support);
        Log.d(TAG, "savePublication: saving publication : " + titre);
        PublicationFirestore.createPublication(publication)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        isPublicationSaved.postValue(true);
                        Log.d(TAG, "onSuccess: publication saved : " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        isPublicationSaved.postValue(true);
                    }
                });
    }

    /**
     * Vérifie si la vidéo existe et fait appel savePublication pour créer la publication
     */
    public void addPublication(final String titre, final String sourceUrl, final String groupId, final String support) {
        isLoading.postValue(true);

        YoutubePublicationFactory youtubePublicationFactory = new YoutubePublicationFactory();
        SoundcloudPublicationFactory soundcloudPublicationFactory = new SoundcloudPublicationFactory();

        final String videoId;
        final String authorId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (support.equals("youtube")) {
            videoId = youtubePublicationFactory.getVideoIdFromUrl(sourceUrl);
            youtubePublicationFactory.ressourceExists(videoId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean soundExist) throws Throwable {
                            if (soundExist) savePublication(titre, videoId, groupId, authorId, support);
                            else isPublicationSaved.postValue(false);
                            isLoading.postValue(false);
                        }
                    });
        } else {
            videoId = soundcloudPublicationFactory.getVideoIdFromUrl(sourceUrl);
            soundcloudPublicationFactory.ressourceExists(videoId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean soundExist) throws Throwable {
                            if (soundExist)
                                savePublication(titre, videoId, groupId, authorId, support);
                            else isPublicationSaved.postValue(false);
                            isLoading.postValue(false);
                        }
                    });
        }
    }
}
