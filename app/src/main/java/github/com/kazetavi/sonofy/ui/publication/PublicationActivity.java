package github.com.kazetavi.sonofy.ui.publication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PublicationActivity extends AppCompatActivity {

    TextView titreTextView;
    ImageView miniatureImageView;
    TextView likeCountTextView;
    TextView dislikeCountTextView;
    LinearLayout likeButton;
    LinearLayout dislikeButton;
    LinearLayout sadButton;
    LinearLayout angryButton;
    LinearLayout happyButton;
    LinearLayout superrButton;
    LinearLayout heoButton;

    TextView sadCountTextView;
    TextView happyCountTextView;
    TextView heoCountTextView;
    TextView superrCountTextView;
    TextView angryCountTextView;

    EditText commentaireEditText;
    Button commenterButton;

    Publication publication;

    private RecyclerView commentaireRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        titreTextView = findViewById(R.id.titrePublicationTextView2);
        miniatureImageView = findViewById(R.id.miniaturePublicationImageView2);
        likeCountTextView = findViewById(R.id.likeCountTextView2);
        dislikeCountTextView = findViewById(R.id.dislikeCountTextView2);
        likeButton = findViewById(R.id.likeButton2);
        dislikeButton = findViewById(R.id.dislikeButton2);

        //commentaireEditText = findViewById(R.id.commentaireEditText);
        //commenterButton = findViewById(R.id.commenterButton);

        sadButton = findViewById(R.id.sadButton);
        angryButton = findViewById(R.id.angryButton);
        superrButton = findViewById(R.id.superrButton);
        happyButton = findViewById(R.id.happyButton);
        heoButton = findViewById(R.id.heoButton);
        sadCountTextView = findViewById(R.id.sadCount);
        angryCountTextView = findViewById(R.id.angryCount);
        superrCountTextView = findViewById(R.id.superrCount);
        heoCountTextView = findViewById(R.id.heoCount);
        happyCountTextView = findViewById(R.id.happyCount);

        commentaireRecyclerView = findViewById(R.id.commentaireRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        commentaireRecyclerView.setLayoutManager(layoutManager);

        final Intent intent = getIntent();
        final String publicationId = intent.getStringExtra("PUBLICATION_ID");

        final PublicationViewModel publicationViewModel = new ViewModelProvider(this).get(PublicationViewModel.class);

        publicationViewModel.loadPublication(publicationId);

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

        publicationViewModel.getCommentaires().observe(this, new Observer<List<Commentaire>>() {
            @Override
            public void onChanged(List<Commentaire> commentaires) {

                adapter = new CommentaireAdapter(commentaires);
                commentaireRecyclerView.setAdapter(adapter);
            }
        });


        /*
        commenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentaireEditText.getText().toString();
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                publicationViewModel.createCommentaire(publication.getUid(), content, username);
                commentaireEditText.setText("");
            }
        });

         */

        miniatureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getVideoUrl()));
                startActivity(intentYoutube);
            }
        });



    }
}