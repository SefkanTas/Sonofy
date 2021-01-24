package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;

public class CommentaireFirestore {
    private static final String COLLECTION_NAME = "commentaires";
    public static final String CONTENT = "content";
    public static final String LIKE_COUNT = "likeCount";
    public static final String DISLIKE_COUNT = "dislikeCount";
    public static final String DATE_CREATED = "dateCreated";
    public static final String PUBLICATION_ID = "publicationId";
    public static final String USERNAME = "username";

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

    private static Task<Void> incrementValueByN(Commentaire publication, String field, int n){
        return PublicationFirestore.getPublicationRef(publication.getUid())
                .update(field, FieldValue.increment(n));
    }

    public static Task<Void> incrementSad(Commentaire publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }

    public static Task<Void> incrementAngry(Commentaire publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }

    public static Task<Void> incrementHappy(Commentaire publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }

    public static Task<Void> incrementHeo(Commentaire publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }

    public static Task<Void> incrementSuperr(Commentaire publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }


}
