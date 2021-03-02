package github.com.kazetavi.sonofy.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;
import github.com.kazetavi.sonofy.ui.publication.EmotionMainProfileAdapter;

public class MainProfilActivity extends AppCompatActivity {
    private RecyclerView resultats;
    private RecyclerView.Adapter adapter, adapter2;
    private TextView id_user;
    private TextView pseudo_user;
    private User donnesUser;
    private FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profil);

        ProfilViewModel profilViewModel = new ViewModelProvider(this).get(ProfilViewModel.class);

        user = FirebaseAuth.getInstance();
        resultats = findViewById(R.id.publication_user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        resultats.setLayoutManager(layoutManager);

        ImageButton home = findViewById(R.id.home_page);
        resultats = findViewById(R.id.publication_user);
        ImageButton modification = findViewById(R.id.imageEdit);
        id_user = findViewById(R.id.np_user);
        pseudo_user = findViewById(R.id.p_user);
        Button mesPublications = findViewById(R.id.button_publication_user);
        Button mesEmotions = findViewById(R.id.button_emotion_user);

        profilViewModel.getUserMutableLiveData().observe(this , new Observer<User>() {
            @Override
            public void onChanged(User user) {
                donnesUser = user;
                String np = donnesUser.getPrenom() + "  " + donnesUser.getNom();
                String p = " @" + donnesUser.getPseudo();
                id_user.setText(np);
                pseudo_user.setText(p);
            }
        });

        profilViewModel.getUser(user.getCurrentUser().getUid());

        profilViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                resultats.setAdapter(adapter);
            }
        });

        profilViewModel.getEmotionsLiveData().observe(this, new Observer<List<Emotion>>() {
            @Override
            public void onChanged(List<Emotion> emotions) {
                adapter2 = new EmotionMainProfileAdapter(emotions);
                resultats.setAdapter(adapter2);
            }
        });

        //Permet d'afficher les publications de l'utilisateur courant directement
        profilViewModel.loadPublicationsAuthor(user.getUid());

        //Retour à la liste des groupes --> à modifier lorsque la page d'accueil sera OK
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
                startActivity(intent);
            }
        });

        //Redicrection vers la page du profil pour modifier ses données
        modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProfilActivity.class);
                startActivity(intent);
            }
        });

        mesPublications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilViewModel.loadPublicationsAuthor(user.getUid());
            }
        });

        mesEmotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilViewModel.loadEmotionsAuthor(user.getUid());
            }
        });
    }
}