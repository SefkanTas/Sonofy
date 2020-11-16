package github.com.kazetavi.sonofy.auth.data.model;

import android.widget.TextView;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    private String userId;
    private String nom, prenom, pseudo, email, password;
    private String role;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.nom = displayName;
    }

    public User(){

    }

    public User(String nom, String prenom, String pseudo, String email){
        this.nom = nom;
        this.prenom = prenom;
        this.pseudo = pseudo;
        this.email = email;
    }


    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return nom;
    }
}