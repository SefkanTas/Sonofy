package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;

public class EmotionFirestore {
    private static final String COLLECTION_NAME = "emotions";
    public static final String LIKE_COUNT = "likeCount";
    public static final String DISLIKE_COUNT = "dislikeCount";
    public static final String SAD_COUNT = "sadCount";
    public static final String SUPERR_COUNT = "superrCount";
    public static final String ANGRY_COUNT = "angryCount";
    public static final String HAPPY_COUNT = "happyCount";
    public static final String HEO_COUNT = "heoCount";
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
    public static Task<DocumentReference> create(Emotion emotion){
        return getCollection().add(emotion);
    }

    public static DocumentReference getEmotionRef(Emotion commentaire){
        return EmotionFirestore.getCollection().document();
    }

    private static Task<Void> incrementValueByN(Emotion publication, String field, int n){
        return EmotionFirestore.getEmotionRef(publication).update(field, FieldValue.increment(n));
    }

    public static Task<Void> incrementSad(Emotion publication){
        return incrementValueByN(publication, SAD_COUNT, 1);
    }

    public static Task<Void> incrementAngry(Emotion publication){
        return incrementValueByN(publication, ANGRY_COUNT, 1);
    }

    public static Task<Void> incrementHappy(Emotion publication){
        return incrementValueByN(publication, HAPPY_COUNT, 1);
    }

    public static Task<Void> incrementHeo(Emotion publication){
        return incrementValueByN(publication, HEO_COUNT, 1);
    }

    public static Task<Void> incrementSuperr(Emotion publication){
        return incrementValueByN(publication, SUPERR_COUNT, 1);
    }




}
