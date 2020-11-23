package github.com.kazetavi.sonofy.ui.addpublication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.main.MainActivity;

public class AddPublicationActivity extends AppCompatActivity {

    AddPublicationViewModel addPublicationViewModel;

    private EditText titreMusiqueEditText;
    private EditText youtubeVideoIdEditText;
    private Button publierButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_publication);

        titreMusiqueEditText = findViewById(R.id.titreMusiqueEditText);
        youtubeVideoIdEditText = findViewById(R.id.youtubeVideoIdEditText);
        publierButton = findViewById(R.id.publierButton);
        progressBar = findViewById(R.id.progressBar);
        addPublicationViewModel = new ViewModelProvider(this).get(AddPublicationViewModel.class);


        //Barre / cercle de chargement lorsqu'on ajoute la publication
        addPublicationViewModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading){
                    progressBar.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        //Check si la publication a pu être ajoutée dans la base de données
        addPublicationViewModel.isPublicationSaved().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean publicationIsSaved) {
                if(publicationIsSaved){
                    //Retour sur la page de la liste des publications
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "L'identifiant de la vidéo n'est pas valide", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Appui sur le bouton publication
        publierButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!youtubeVideoIdEditText.getText().toString().isEmpty() && !titreMusiqueEditText.getText().toString().isEmpty()){
                    String titre;
                    String videoId = null;

                    titre = titreMusiqueEditText.getText().toString().trim();
                    videoId = youtubeVideoIdEditText.getText().toString().trim();

                    addPublicationViewModel.addPublication(titre, videoId);
                }
            }
        });
    }



}
