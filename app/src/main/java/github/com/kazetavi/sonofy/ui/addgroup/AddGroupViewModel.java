package github.com.kazetavi.sonofy.ui.addgroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class AddGroupViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isGroupCreated = new MutableLiveData<>();


    public MutableLiveData<Boolean> isGroupCreated() {
        return isGroupCreated;
    }

    private void checkGroupExists(final String nomGroupe){
        GroupeFirestore.getGroup(nomGroupe).addOnCompleteListener(task -> {
            if (task.getResult().size() == 0){
                createGroupe(nomGroupe);
                isGroupCreated.setValue(true);
            }
            else {
                isGroupCreated.setValue(false);
            }
        });
    }

    private void createGroupe(String nomGroupe){
        GroupeFirestore.create(new Groupe(nomGroupe));
    }

    public void checkGroupExistsAndCreate(String nomGroupe){
        checkGroupExists(nomGroupe);
    }




}
