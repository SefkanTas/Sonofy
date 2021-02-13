package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.ListeEmoji;

public class ListeEmojiFirestore {
    private static final String COLLECTION_NAME = "listeemotions";
    public static final String DATE_CREATED = "dateCreated";
    public static final String PUBLICATION_ID = "publicationId";
    public static final String USERNAME = "username";
    public static final String TYPE_EMOJI = "typeEmoji";

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
    public static Task<DocumentReference> create(ListeEmoji emotion){
        return getCollection().add(emotion);
    }


}
