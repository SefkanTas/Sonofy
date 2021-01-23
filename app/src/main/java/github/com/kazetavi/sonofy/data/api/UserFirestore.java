package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.model.User;

public class UserFirestore {
    private static final String COLLECTION_NAME = "Users";
    public static final String DISPLAY_NAME = "displayName";
    public static final String EMAIL = "email";
    public static final String NOM = "nom";
    public static final String PRENOM = "prenom";
    public static final String PSEUDO = "pseudo";
    public static final String ROLE = "role";


    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    public static DocumentReference getUserRef(String uid){
        return UserFirestore.getUsersCollection().document(uid);
    }

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserFirestore.getUserRef(uid).get();
    }

    public static Task<QuerySnapshot> getPseudo(String pseudo){
        return getUsersCollection().whereEqualTo(PSEUDO, pseudo).get();
    }

    //MODIFICATION PRENOM
    public static Task<Void> updateFirstName(String uid, String prenom) {
        return UserFirestore.getUsersCollection()
                .document(uid)
                .update(PRENOM, prenom);
    }

    public static Task<Void> updateFirstName(User user, String prenom) {
        return UserFirestore.updateFirstName(user.getUserId(), prenom);
    }

    //MODIFICATION NOM
    public static Task<Void> updateLastName(String uid, String nom) {
        return UserFirestore.getUsersCollection()
                .document(uid)
                .update(NOM, nom);
    }

    public static Task<Void> updateLastName(User user, String nom) {
        return UserFirestore.updateLastName(user.getUserId(), nom);
    }

    //MODIFICATION PSEUDO
    public static Task<Void> updatePseudo(String uid, String pseudo) {
        return UserFirestore.getUsersCollection()
                .document(uid)
                .update(PSEUDO, pseudo);
    }

    public static Task<Void> updatePseudo(User user, String pseudo) {
        return UserFirestore.updatePseudo(user.getUserId(), pseudo);
    }
}