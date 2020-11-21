package github.com.kazetavi.sonofy.auth.data.model;

import android.widget.TextView;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    private String userId;
    private String nom, prenom, pseudo, email, password,role;

    public User(String userId, String displayName) {
        this.userId = userId;
        this.nom = displayName;
    }

    public User(){

    }

    public User(String nom, String p, String pseudo, String email, String r){
        this.nom = nom;
        this.prenom = p;
        this.pseudo = pseudo;
        this.email = email;
        this.role = r;
    }


    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}