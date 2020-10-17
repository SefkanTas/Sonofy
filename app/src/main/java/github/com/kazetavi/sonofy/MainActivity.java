package github.com.kazetavi.sonofy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

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
                    .append("/0.jpg");
            Picasso.get().load(imgLink.toString()).into(miniatureImageView);
        }
        /*else {
            Picasso.get().load("https://img.youtube.com/vi/L8YQ3w_5Gr0/0.jpg").into(miniatureImageView);
            "https://img.youtube.com/vi/553_WqA9-Qs/0.jpg"
            mnN6eSya8yQ
        }*/



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