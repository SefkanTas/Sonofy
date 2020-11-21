package github.com.kazetavi.sonofy.auth.ui.register;

import androidx.lifecycle.ViewModel;

import github.com.kazetavi.sonofy.auth.data.model.User;

public class RegisterViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();


    /*
     * Ajoute un utilisateur dans la BD Firestore
     */

    void addUser(String nom, String prenom, String pseudo, String email, String role){
        User user = new User(nom, prenom, pseudo, email, role);

        
    }
}
