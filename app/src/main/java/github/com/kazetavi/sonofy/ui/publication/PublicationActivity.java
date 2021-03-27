package github.com.kazetavi.sonofy.ui.publication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.user.MainProfilActivity;
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

public class PublicationActivity extends AppCompatActivity {

    private TextView titreTextView;
    private ImageView miniatureImageView;
    private TextView likeCountTextView;
    private TextView dislikeCountTextView;
    private TextView authorUsernameTextView;
    private Publication publication;
    private RecyclerView emotionRecyclerView;
    private RecyclerView.Adapter adapter;

    private User userc;

    private String pseudoU;


    private TextView veryHappyCountTV;
    private TextView happyCountTV;
    private TextView sadCountTV;
    private TextView verySadCountTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);

        ProfilViewModel uservm = new ViewModelProvider(this).get(ProfilViewModel.class);
        FirebaseAuth currentUser = FirebaseAuth.getInstance();

        titreTextView = findViewById(R.id.titrePublicationTextView2);
        miniatureImageView = findViewById(R.id.miniaturePublicationImageView2);
        likeCountTextView = findViewById(R.id.likeCountTextView2);
        dislikeCountTextView = findViewById(R.id.dislikeCountTextView2);
        authorUsernameTextView = findViewById(R.id.authorUsernameTextView);
        LinearLayout likeButton = findViewById(R.id.likeButton2);
        LinearLayout dislikeButton = findViewById(R.id.dislikeButton2);

        final Intent intent = getIntent();
        final String publicationId = intent.getStringExtra("PUBLICATION_ID");

        final PublicationViewModel publicationViewModel = new ViewModelProvider(this).get(PublicationViewModel.class);

        publicationViewModel.loadPublication(publicationId);
        publicationViewModel.loadCurrentUser(FirebaseAuth.getInstance().getCurrentUser().getUid());

        //Recupérer l'utilisateur courant pour mettre à jour le pseudo afficher dans les commentaires
        uservm.getUser(currentUser.getCurrentUser().getUid());

        uservm.getUserMutableLiveData().observe(this, user -> {
            userc = user;
            pseudoU = userc.getPseudo();
        });

        publicationViewModel.getPublicationLiveData().observe(this, publicationLiveData -> {
            publication = publicationLiveData;
            UserFirestore.getUser(publication.getAuthorId()).addOnSuccessListener(documentSnapshot -> {
                User user = documentSnapshot.toObject(User.class);
                String res = "@" + user.getPseudo();
                authorUsernameTextView.setText(res);
                intent.putExtra("userId", user.getUserId());
            });
            Picasso.get().load(publication.getMiniatureUrl()).into(miniatureImageView);
            titreTextView.setText(publication.getTitre());
            likeCountTextView.setText(publication.getLikeCount().toString());
            dislikeCountTextView.setText(publication.getDislikeCount().toString());
        });

        authorUsernameTextView.setOnClickListener(v -> {
            Intent intent2 = new Intent(getBaseContext(), MainProfilActivity.class);
            intent2.putExtra("userID", intent.getStringExtra("userId"));
            startActivity(intent2);
        });

        miniatureImageView.setOnClickListener(view -> {
            Intent intentYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getVideoUrl()));
            startActivity(intentYoutube);
        });


        ImageView veryHappyEmotionIV = findViewById(R.id.veryHappyEmotion);
        ImageView happyEmotionIV = findViewById(R.id.happyEmotion);
        ImageView sadEmotionIV = findViewById(R.id.sadEmotion);
        ImageView verySadEmotionIV = findViewById(R.id.verySadEmotion);

        veryHappyCountTV = findViewById(R.id.veryHappyCountTV);
        happyCountTV = findViewById(R.id.happyCountTV);
        sadCountTV = findViewById(R.id.sadCountTV);
        verySadCountTV = findViewById(R.id.verySadCountTV);

        emotionRecyclerView = findViewById(R.id.emotionsRecylcerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        emotionRecyclerView.setLayoutManager(layoutManager);

        publicationViewModel.getEmotionsLiveData().observe(this, emotions -> {
            adapter = new EmotionAdapter(emotions);
            emotionRecyclerView.setAdapter(adapter);

            // Refactoriser -> à ne pas faire ici
            long veryHappyCount = emotions.stream().filter(e -> e.getEmotion().equals("veryHappy")).count();
            long happyCount = emotions.stream().filter(e -> e.getEmotion().equals("happy")).count();
            long sadCount = emotions.stream().filter(e -> e.getEmotion().equals("sad")).count();
            long verySadCount = emotions.stream().filter(e -> e.getEmotion().equals("verySad")).count();

            veryHappyCountTV.setText(String.valueOf(veryHappyCount));
            happyCountTV.setText(String.valueOf(happyCount));
            sadCountTV.setText(String.valueOf(sadCount));
            verySadCountTV.setText(String.valueOf(verySadCount));
        });

        veryHappyEmotionIV.setOnClickListener(v -> {
            publicationViewModel.addEmotion("veryHappy");
        });

        happyEmotionIV.setOnClickListener(v -> {
            publicationViewModel.addEmotion("happy");
        });

        sadEmotionIV.setOnClickListener(v -> {
            publicationViewModel.addEmotion("sad");
        });

        verySadEmotionIV.setOnClickListener(v -> {
            publicationViewModel.addEmotion("verySad");
        });
    }
}
