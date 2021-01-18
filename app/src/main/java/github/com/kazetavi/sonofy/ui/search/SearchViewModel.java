package github.com.kazetavi.sonofy.ui.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.util.Log;

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

public class SearchViewModel extends ViewModel {

    MutableLiveData<List<Publication>> publications = new MutableLiveData<>();
    MutableLiveData<List<Groupe>> groupes = new MutableLiveData<>();

    MutableLiveData<List<Publication>> getPublications(){
        return publications;
    }

    MutableLiveData<List<Groupe>> getGroupes(){
        return groupes;
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
        void searchGroup(String groupe){
            final List<Groupe> groupeList = new ArrayList<>();

            GroupeFirestore.searchByGroupe(groupe)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            groupeList.clear();
                            for (QueryDocumentSnapshot doc : value) {
                                groupeList.add(doc.toObject(Groupe.class));
                                groupes.setValue(groupeList);
                            }
                        }
                    });
        }
    }
