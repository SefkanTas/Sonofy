package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import github.com.kazetavi.sonofy.data.model.Publication;

public class PublicationFirestore {

    private static final String COLLECTION_NAME = "publicationsTest";
    public static final String LIKE_COUNT = "likeCount";
    public static final String DISLIKE_COUNT = "dislikeCount";
    public static final String DATE_CREATED = "dateCreated";
    public static final String TITRE = "titre";
    public static final String VIDEO_ID = "videoId";

    public static CollectionReference getPublicationsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }


    // CREATE
    public static Task<DocumentReference> createPublication(Publication publication){
        return PublicationFirestore.getPublicationsCollection().add(publication);
    }

    // GET
    public static Task<QuerySnapshot> getAllPublications(){
        return PublicationFirestore.getPublicationsCollection().get();
    }

    public static Query getAllPublicationsCollectionDesc(){
        return PublicationFirestore.getPublicationsCollection().orderBy(DATE_CREATED, Query.Direction.DESCENDING);
    }

    public static Task<QuerySnapshot> getAllPublicationsDesc(){
        return PublicationFirestore.getAllPublicationsCollectionDesc().get();
    }

    public static DocumentReference getPublicationRef(String uid){
        return PublicationFirestore.getPublicationsCollection().document(uid);
    }

    public static DocumentReference getPublicationRef(Publication publication){
        return getPublicationRef(publication.getUid());
    }

    public static Task<DocumentSnapshot> getPublication(String uid){
        return PublicationFirestore.getPublicationRef(uid).get();
    }


    // UPDATE

    public static Task<Void> updateTitre(String uid, String titre) {
        return PublicationFirestore.getPublicationsCollection()
                .document(uid)
                .update(TITRE, titre);
    }

    public static Task<Void> updateTitre(Publication publication, String titre) {
        return PublicationFirestore.updateTitre(publication.getUid(), titre);
    }

    public static Task<Void> updateVideoId(String uid, String videoId) {
        return PublicationFirestore.getPublicationsCollection()
                .document(uid)
                .update(VIDEO_ID, videoId);
    }

    public static Task<Void> updateVideoId(Publication publication, String videoId) {
        return PublicationFirestore.updateVideoId(publication.getUid(), videoId);
    }


    private static Task<Void> incrementValueByN(Publication publication, String field, int n){
        return PublicationFirestore.getPublicationRef(publication.getUid())
                .update(field, FieldValue.increment(n));
    }

    public static Task<Void> incrementLike(Publication publication){
        return incrementValueByN(publication, LIKE_COUNT, 1);
    }

    public static Task<Void> incrementDislike(Publication publication){
        return incrementValueByN(publication, DISLIKE_COUNT, 1);
    }

    // DELETE

    public static Task<Void> deletePublication(String uid) {
        return PublicationFirestore.getPublicationsCollection().document(uid).delete();
    }


}
