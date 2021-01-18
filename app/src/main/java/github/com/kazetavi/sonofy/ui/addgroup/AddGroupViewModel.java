package github.com.kazetavi.sonofy.ui.addgroup;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class AddGroupViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    private MutableLiveData<Boolean> isGroupCreated = new MutableLiveData<>();


    public MutableLiveData<Boolean> isGroupCreated() {
        return isGroupCreated;
    }

    private void checkGroupExists(final String nomGroupe){
        GroupeFirestore.getGroup(nomGroupe).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().size() == 0){
                    createGroupe(nomGroupe);
                    isGroupCreated.setValue(true);
                }
                else {
                    isGroupCreated.setValue(false);
                }
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
