package github.com.kazetavi.sonofy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPublicationActivity extends AppCompatActivity {

    private EditText titreMusiqueEditText;
    private EditText youtubeVideoIdEditText;
    private Button publierButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        titreMusiqueEditText = findViewById(R.id.titreMusiqueEditText);
        youtubeVideoIdEditText = findViewById(R.id.youtubeVideoIdEditText);
        publierButton = findViewById(R.id.publierButton);

        publierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titre = titreMusiqueEditText.getText().toString();
                String videoId = youtubeVideoIdEditText.getText().toString();
                Log.d("SEFKAN", "titre " + titreMusiqueEditText.getText() + "\n id : " + youtubeVideoIdEditText.getText());

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("TITRE", titre);
                intent.putExtra("VIDEO", videoId);
                startActivity(intent);
            }
        });


    }
}