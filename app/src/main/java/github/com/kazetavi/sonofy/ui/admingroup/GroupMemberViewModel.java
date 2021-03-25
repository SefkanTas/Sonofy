package github.com.kazetavi.sonofy.ui.admingroup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.User;

public class GroupMemberViewModel extends ViewModel {

    private MutableLiveData<List<User>> userMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<User>> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void loadGroupMembers(String groupId){
        GroupeFirestore.getGroupWithId(groupId).addOnSuccessListener(documentSnapshot -> {
            Groupe groupe = documentSnapshot.toObject(Groupe.class);
            List<User> users = new ArrayList<>();
            groupe.getMembersId().forEach(memberId -> {
                UserFirestore.getUser(memberId).addOnSuccessListener(documentSnapshotUser ->{
                            User u = documentSnapshotUser.toObject(User.class);
                            users.add(u);
                            if(users.size() == groupe.getMembersId().size()){
                                userMutableLiveData.setValue(users);
                            }
                        }
                );
            });
        });
    }

}
