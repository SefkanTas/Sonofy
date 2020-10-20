package github.com.kazetavi.sonofy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newPublicationButton;

    private ImageView miniatureImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPublicationButton = findViewById(R.id.newPublicationButton);
        miniatureImageView = findViewById(R.id.miniatureImageView);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String videoId = extras.getString("VIDEO");
            StringBuilder imgLink = new StringBuilder("https://img.youtube.com/vi/")
                    .append(videoId)
                    .append("/mqdefault.jpg");
            Picasso.get().load(imgLink.toString()).into(miniatureImageView);
        }
        /*else {
            Picasso.get().load("https://img.youtube.com/vi/L8YQ3w_5Gr0/0.jpg").into(miniatureImageView);
            "https://img.youtube.com/vi/553_WqA9-Qs/0.jpg"
            mnN6eSya8yQ
        }*/

       /* Firestore db = FirestoreClient.getFirestore();
        CollectionReference colRef = db.collection("publication");
        Log.d("SEFKAN COLREF", colRef.toString());

*/

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        ////Read
        db.collection("publications")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("SEFKAN", document.getId() + " => " + document.getData());
                            }
                        } else {
                            //Log.w("SEFKAN", "Error getting documents.", task.getException());
                        }
                    }
                });
        ////Read

        newPublicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SEFKAN", "onClick: testest");
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                startActivity(intent);
            }
        });
    }


}