package github.com.kazetavi.sonofy.data.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;
import java.util.function.Consumer;

import github.com.kazetavi.sonofy.data.model.Emotion;

public class EmotionFirestore {
    private static final String COLLECTION_NAME = "emotions-bis";
    public static final String EMOTION = "emotion";
    public static final String USER_ID = "userId";
    public static final String PUBLICATION_ID = "publicationId";
    public static final String DATE_CREATED = "dateCreated";


    //for sonar code smell
    private EmotionFirestore() {
    }

    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Query getAllEmotionsCollectionDesc(){
        return EmotionFirestore.getCollection().orderBy(DATE_CREATED, Query.Direction.DESCENDING);
    }

    public static DocumentReference getRef(String uid){
        return getCollection().document(uid);
    }

    public static Task<DocumentReference> create(Emotion emotion){
        return getCollection().add(emotion);
    }

    public static Task<DocumentSnapshot> get(String uid){
        return getRef(uid).get();
    }

    public static Task<QuerySnapshot> getAll(){
        return getCollection().get();
    }

    public static Query getByPublication(String publicationId){
        return getCollection()
                .whereEqualTo(PUBLICATION_ID, publicationId)
                .orderBy(DATE_CREATED, Query.Direction.DESCENDING);
    }

    public static Task<QuerySnapshot> getByUser(String userId){
        return getCollection()
                .whereEqualTo(USER_ID, userId)
                .get();
    }
    public static Query getEmotionByAuthorId(String authorId){
        return EmotionFirestore.getAllEmotionsCollectionDesc()
                .whereEqualTo(USER_ID, authorId);
    }

    public static Task<Void> delete(String uid) {
        return getCollection().document(uid).delete();
    }


    public static void deleteByPublicationId(String publicationId){
        getByPublication(publicationId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot query : Objects.requireNonNull(task.getResult())){
                            delete(query.getId());
                        }
                    }
            }
        });
    }

    /*
     * La méthode supprime toutes les émotions hormis celle qui a l'id de la nouvelle émotion
     * C'est moche et à refactoriser asap.
     * */
    public static Task<QuerySnapshot> deleteByUserAndPublication(String userId, String publicationId, String newEmotionId) {

        return getCollection()
                .whereEqualTo(USER_ID, userId)
                .whereEqualTo(PUBLICATION_ID, publicationId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if (!documentSnapshot.getId().equals(newEmotionId)){
                                delete(documentSnapshot.getId());
                            }
                        }
                    }
                });

    }}
