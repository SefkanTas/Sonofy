package github.com.kazetavi.sonofy.ui.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.api.UserFirestore;

public class ProfilViewModel extends ViewModel {
    private final String TAG = this.getClass().getSimpleName();

    public void updateNom(String uid, String nom){
        UserFirestore.updateLastName(uid,nom);
    }

    public void updatePrenom(String uid, String prenom){
        UserFirestore.updateFirstName(uid,prenom);
    }

    public void updateEmail(String uid, String email){
    }

    public void updatePseudo(String uid, String pseudo){
        UserFirestore.updatePseudo(uid, pseudo);
    }

    public boolean pseudoAlreadyExists(final String uid, final String p){
        final boolean[] b = {true};
        UserFirestore.getPseudo(p).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size() == 0){
                    updatePseudo(uid, p);
                    b[0] = false;
                }else{
                    b[0] = true;
                }
            }
        });
        return b[0];
    }


}
