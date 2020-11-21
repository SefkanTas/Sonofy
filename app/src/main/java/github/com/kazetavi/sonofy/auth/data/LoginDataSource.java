package github.com.kazetavi.sonofy.auth.data;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import github.com.kazetavi.sonofy.MainActivity;
import github.com.kazetavi.sonofy.auth.data.model.User;

import java.io.IOException;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public Result<User> login(String username, String password) {

        try {
            mAuth.signInWithEmailAndPassword(username,password);
            db.collection("Users").document(mAuth.getCurrentUser().getDisplayName());
            User user = new User();
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Erreur de connexion ", e));
        }
    }

    public void logout() {
         FirebaseAuth.getInstance().signOut();
    }
}