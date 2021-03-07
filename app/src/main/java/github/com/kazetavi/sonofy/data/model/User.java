package github.com.kazetavi.sonofy.data.model;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    @Exclude
    private String userId;
    private String nom;
    private String prenom;
    private String pseudo;
    private String email;
    private String role;

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

    @DocumentId
    public String getUserId() {
        return userId;
    }

    private void setUserId(String userId){
        this.userId = userId;
    }

    public String getDisplayName() {
        return getPrenom() + " " + getNom();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role='").append(role).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
