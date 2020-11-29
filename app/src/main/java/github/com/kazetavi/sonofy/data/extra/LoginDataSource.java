package github.com.kazetavi.sonofy.data.extra;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.model.User;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //On authentifie l'utilisateur avec Firebase puis on recupere l'utilisateur courant avec Firestore pour reéuperer ses données
    public Result<User> login(String username, String password){

        try {
            final User[] u = new User[1];
            mAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        db.collection("Users")
                                .whereEqualTo("email",mAuth.getCurrentUser().getEmail())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for(QueryDocumentSnapshot res : task.getResult()){
                                                u[0] = res.toObject(User.class);
                                            }
                                        }
                                    }
                                });
                    }
                });

            return new Result.Success<>(u[0]);
        } catch (Exception e) {
            return new Result.Error(new IOException("Authentification échouée, votre email ou mot de passe est incorrect ", e));
        }
    }

    public void logout() {
         FirebaseAuth.getInstance().signOut();
    }
}