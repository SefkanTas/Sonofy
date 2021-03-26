package github.com.kazetavi.sonofy.ui.listgroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class ListGroupViewModel extends ViewModel {
    private final MutableLiveData<List<Groupe>> groupesLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Groupe>> getGroupesLiveData() {
        return groupesLiveData;
    }

    public void loadGroupes(){
        final List<Groupe> groupeList = new ArrayList<>();

        GroupeFirestore.getCollectionQueryDesc()
                .addSnapshotListener((value, error) -> {
                    groupeList.clear();
                    for(QueryDocumentSnapshot doc : value){
                        groupeList.add(doc.toObject(Groupe.class));
                    }
                    groupesLiveData.setValue(groupeList);
                });
    }
}
