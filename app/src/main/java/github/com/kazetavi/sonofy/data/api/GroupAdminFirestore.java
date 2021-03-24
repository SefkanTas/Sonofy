package github.com.kazetavi.sonofy.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import github.com.kazetavi.sonofy.data.model.GroupAdmin;

public class GroupAdminFirestore {

    private static final String COLLECTION_NAME = "groupAdmin";
    public static final String GROUP_ID = "groupId";
    public static final String USER_ID = "userId";
    public static final String DATE_CREATED = "dateCreated";

    //for sonar code smell
    private GroupAdminFirestore() {
    }

    public static CollectionReference getCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static DocumentReference getRef(String uid){
        return getCollection().document(uid);
    }

    public static Task<DocumentReference> create(GroupAdmin groupAdmin){
        return getCollection().add(groupAdmin);
    }

    public static Task<DocumentSnapshot> get(String uid){
        return getRef(uid).get();
    }

    public static Task<QuerySnapshot> getAll(){
        return getCollection().get();
    }


    public static Task<QuerySnapshot> getByUser(String userId){
        return getCollection()
                .whereEqualTo(USER_ID, userId)
                .get();
    }

    public static Task<Void> delete(String uid) {
        return getCollection().document(uid).delete();
    }



}
