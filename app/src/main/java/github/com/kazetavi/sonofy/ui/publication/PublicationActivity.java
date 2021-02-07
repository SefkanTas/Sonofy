package github.com.kazetavi.sonofy.ui.publication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.user.ProfilViewModel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PublicationActivity extends AppCompatActivity {

    private TextView titreTextView;
    private ImageView miniatureImageView;
    private TextView likeCountTextView;
    private TextView dislikeCountTextView;
    private TextView sadCountTextView;


    private ImageView commentaireEditText;

    private Publication publication;

    private RecyclerView commentaireRecyclerView;
    private RecyclerView.Adapter adapter;

    private User userc;
    private FirebaseAuth currentUser;

    private String pseudoU;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        ProfilViewModel uservm = new ViewModelProvider(this).get(ProfilViewModel.class);
        currentUser = FirebaseAuth.getInstance();

        titreTextView = findViewById(R.id.titrePublicationTextView2);
        miniatureImageView = findViewById(R.id.miniaturePublicationImageView2);
        likeCountTextView = findViewById(R.id.likeCountTextView2);
        dislikeCountTextView = findViewById(R.id.dislikeCountTextView2);
        LinearLayout likeButton = findViewById(R.id.likeButton2);
        LinearLayout dislikeButton = findViewById(R.id.dislikeButton2);
        sadCountTextView = findViewById(R.id.sadCount);
        LinearLayout sadButton = findViewById(R.id.sadButton);

        commentaireRecyclerView = findViewById(R.id.commentaireRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        commentaireRecyclerView.setLayoutManager(layoutManager);

        final Intent intent = getIntent();
        final String publicationId = intent.getStringExtra("PUBLICATION_ID");

        final PublicationViewModel publicationViewModel = new ViewModelProvider(this).get(PublicationViewModel.class);

        publicationViewModel.loadPublication(publicationId);

        //Recupérer l'utilisateur courant pour mettre à jour le pseudo afficher dans les commentaires
       // uservm.getUser(currentUser.getCurrentUser().getUid());
        /*
        uservm.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userc = user;
                pseudoU = userc.getPseudo();
            }
        });

         */


        publicationViewModel.getPublication().observe(this, new Observer<Publication>() {
            @Override
            public void onChanged(Publication publicationLiveData) {
                publication = publicationLiveData;
                Picasso.get().load(publication.getMiniatureUrl()).into(miniatureImageView);
                titreTextView.setText(publication.getTitre());
                likeCountTextView.setText(publication.getLikeCount().toString());
                dislikeCountTextView.setText(publication.getDislikeCount().toString());
            }
        });

        publicationViewModel.getEmotions().observe(this, new Observer<List<Emotion>>() {
            @Override
            public void onChanged(List<Emotion> emotions) {
                adapter = new EmotionAdapter(emotions);
                commentaireRecyclerView.setAdapter(adapter);
            }
        });

        sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = null;
                username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                publicationViewModel.createEmotion(publication.getUid(), username);
                //commentaireEditText.setImageResource(R.drawable.emoji_sad);
            }
        });



        miniatureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getVideoUrl()));
                startActivity(intentYoutube);
            }
        });



    }
}