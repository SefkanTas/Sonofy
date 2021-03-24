package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.model.Groupe;

public class GroupeFirestore {
    private static final String COLLECTION_NAME = "groups";
    public static final String NAME = "name";
    public static final String DATE_CREATED = "dateCreated";

    private GroupeFirestore() {
    }

    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Query getCollectionQueryDesc(){
        return getCollection().orderBy(NAME);
    }


    public static Task<QuerySnapshot> getGroup(String nom){
        return getCollection().whereEqualTo(NAME, nom).get();
    }

    public static Task<DocumentSnapshot> getGroupWithId(String uid){
        return getCollection().document(uid).get();
    }



    //SEARCH

    public static Query searchByGroupe(String groupe){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME).whereEqualTo(NAME,groupe);
    }



    // CREATE
    public static Task<DocumentReference> create(Groupe groupe){
        return getCollection().add(groupe);
    }
}
