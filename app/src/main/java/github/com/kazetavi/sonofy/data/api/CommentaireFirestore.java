package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import github.com.kazetavi.sonofy.data.model.Commentaire;

public class CommentaireFirestore {
    private static final String COLLECTION_NAME = "commentaires";
    public static final String CONTENT = "content";
    public static final String LIKE_COUNT = "likeCount";
    public static final String DISLIKE_COUNT = "dislikeCount";
    public static final String DATE_CREATED = "dateCreated";
    public static final String PUBLICATION_ID = "publicationId";
    public static final String USERNAME_ID = "userid";

    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    //GET
    public static Query getCollectionQueryDesc(){
        return getCollection().orderBy(DATE_CREATED);
    }

    public static Query getCollectionQueryByPublication(String publicationId){
        return getCollectionQueryDesc().whereEqualTo(PUBLICATION_ID, publicationId);
    }

    // CREATE
    public static Task<DocumentReference> create(Commentaire commentaire){
        return getCollection().add(commentaire);
    }

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserFirestore.getUserRef(uid).get();
    }
}
