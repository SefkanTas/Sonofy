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

public class AddCommentActivity extends AppCompatActivity {
    private EditText comment_edit_text;
    private Button comment_button;
    FirebaseFirestore db;

    private AddCommentActivity.NetworkAsyncTask nat;

    class NetworkAsyncTask extends AsyncTask {

        private String urlString;
        private String comment;
        private String videoId;
        private int code = 0;

        @Override
        protected Object doInBackground(Object[] objects) {

            comment = comment_edit_text.getText().toString();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //progressBar.setVisibility(View.GONE);

            if (code == 200) {
                Map<String, Object> comments = new HashMap<>();
                comments.put("video_id", videoId);
                comments.put("comment", comment);
                comments.put("comm_count", 0);

                db.collection("commentaire")
                        .add(comments)
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
                intent.putExtra("COMMENT", comment);
                intent.putExtra("VIDEO", videoId);
                startActivity(intent);

            }
        }
    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_comment);

            comment_edit_text = findViewById(R.id.titreMusiqueEditText);
            comment_button = findViewById(R.id.publierButton);

            db = FirebaseFirestore.getInstance();

            comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!comment_edit_text.getText().toString().isEmpty()) {
                        nat = new NetworkAsyncTask();
                        nat.execute();
                    }
                }
            });

        }

}