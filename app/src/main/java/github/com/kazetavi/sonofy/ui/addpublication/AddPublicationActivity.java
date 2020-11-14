package github.com.kazetavi.sonofy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import github.com.kazetavi.sonofy.ui.main.MainActivity;

public class AddPublicationActivity extends AppCompatActivity {

    private EditText titreMusiqueEditText;
    private EditText youtubeVideoIdEditText;
    private Button publierButton;
    private ProgressBar progressBar;
    FirebaseFirestore db;

    private int code = 0;

    private NetworkAsyncTask nat;

    class NetworkAsyncTask extends AsyncTask{

        private String urlString;
        private String titre;
        private String videoId;

        @Override
        protected Object doInBackground(Object[] objects) {
            //progressBar.setVisibility(View.VISIBLE);

            titre = titreMusiqueEditText.getText().toString();
            videoId = youtubeVideoIdEditText.getText().toString();

            urlString = "https://i.ytimg.com/vi/" +
                    videoId +
                    "/mqdefault.jpg";

            URL url = null;
            HttpURLConnection connection;
            try {
                url = new URL(urlString);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                code = connection.getResponseCode();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //progressBar.setVisibility(View.GONE);

            if(code == 200){
                Map<String, Object> publication = new HashMap<>();
                publication.put("titre", titre);
                publication.put("video_id", videoId);
                publication.put("like_count", 0);
                publication.put("dislike_count", 0);

                // Add a new document with a generated ID
                db.collection("publications")
                        .add(publication)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error adding document", e);
                            }
                        });
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("TITRE", titre);
                intent.putExtra("VIDEO", videoId);
                startActivity(intent);
            }
            else {
                Toast.makeText(getBaseContext(), "L'identifiant de la vidéo n'est pas valide", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        titreMusiqueEditText = findViewById(R.id.titreMusiqueEditText);
        youtubeVideoIdEditText = findViewById(R.id.youtubeVideoIdEditText);
        publierButton = findViewById(R.id.publierButton);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();

        publierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!youtubeVideoIdEditText.getText().toString().isEmpty() && !titreMusiqueEditText.getText().toString().isEmpty()){
                    nat = new NetworkAsyncTask();
                    nat.execute();
                }
            }
        });

    }

}
