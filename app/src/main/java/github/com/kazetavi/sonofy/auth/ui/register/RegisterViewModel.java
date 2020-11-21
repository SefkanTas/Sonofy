package github.com.kazetavi.sonofy.auth.ui.register;

import android.util.Patterns;

import androidx.lifecycle.ViewModel;

import github.com.kazetavi.sonofy.auth.data.model.User;

public class RegisterViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();


    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 7;
    }
    /*
     * Ajoute un utilisateur dans la BD Firestore
     */

    void addUser(String nom, String prenom, String pseudo, String email, String role){
        User user = new User(nom, prenom, pseudo, email, role);

        
    }
}
