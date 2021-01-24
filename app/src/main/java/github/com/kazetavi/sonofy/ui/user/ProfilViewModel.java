package github.com.kazetavi.sonofy.ui.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.User;

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

    public User getUser(final String uid){
        User user = new User();
        user = Objects.requireNonNull(UserFirestore.getUser(uid).getResult()).toObject(User.class);
        if(user == null){
            Log.w(TAG, "Erreur utilisateur vide");
        }
        /*UserFirestore.getUser(uid)
                .addOnSuccessListener(
                new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        user[0] = documentSnapshot.toObject(User.class);
                        Log.w(TAG, "utilisateur trouvé avec cet ID" + uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Pas d'utilisateur trouvé avec cet ID" + uid);
                    }
        });*/

        return user;
    }

}
