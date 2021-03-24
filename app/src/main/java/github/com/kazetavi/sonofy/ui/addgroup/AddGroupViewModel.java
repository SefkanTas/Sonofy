package github.com.kazetavi.sonofy.ui.addgroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class AddGroupViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isGroupCreated = new MutableLiveData<>();


    public MutableLiveData<Boolean> isGroupCreated() {
        return isGroupCreated;
    }


    public void checkGroupExistsAndCreate(final String nomGroupe, final String userId, final boolean isPrivate){
        GroupeFirestore.getGroup(nomGroupe).addOnCompleteListener(task -> {
            if (task.getResult().size() == 0){
                createGroupe(nomGroupe, userId, isPrivate).addOnSuccessListener(documentReference -> {
                    addAdmin(documentReference.getId(), userId);
                    isGroupCreated.setValue(true);
                });
            }
            else {
                isGroupCreated.setValue(false);
            }
        });
    }

    private Task<DocumentReference> createGroupe(String nomGroupe, final String userId, boolean isPrivate){
        return GroupeFirestore.create(new Groupe(nomGroupe, isPrivate));
    }

    private void addAdmin(String groupId, String userId){

    }


}
