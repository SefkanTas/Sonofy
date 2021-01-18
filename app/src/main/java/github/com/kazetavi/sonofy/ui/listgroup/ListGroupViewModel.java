package github.com.kazetavi.sonofy.ui.listgroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class ListGroupViewModel extends ViewModel {
    private MutableLiveData<List<Groupe>> groupesLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Groupe>> getGroupesLiveData() {
        return groupesLiveData;
    }

    public void loadGroupes(){
        final List<Groupe> groupeList = new ArrayList<>();

        GroupeFirestore.getCollectionQueryDesc()
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        groupeList.clear();
                        for(QueryDocumentSnapshot doc : value){
                            groupeList.add(doc.toObject(Groupe.class));
                        }
                        groupesLiveData.setValue(groupeList);
                    }
                });
    }
}
