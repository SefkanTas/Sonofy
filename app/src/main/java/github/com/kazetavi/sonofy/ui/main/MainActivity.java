package github.com.kazetavi.sonofy.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;


import github.com.kazetavi.sonofy.ui.admingroup.AdminAdhesionActivity;
import github.com.kazetavi.sonofy.ui.admingroup.GroupMemberActivity;
import github.com.kazetavi.sonofy.ui.homepage.HomeActivity;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;
import github.com.kazetavi.sonofy.ui.addpublication.AddPublicationActivity;
import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.user.MainProfilActivity;
import github.com.kazetavi.sonofy.ui.search.SearchActivityPublication;


public class MainActivity extends AppCompatActivity {

    private RecyclerView publicationRecyclerView;
    private RecyclerView.Adapter adapter;
    private final FirebaseAuth user = FirebaseAuth.getInstance();
    private Flow adminFlow;
    private Button membreButton;
    private Button demandeAdhesionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");
        ImageButton home = findViewById(R.id.home_page4);
        Button sortTitre = findViewById(R.id.sortByTitre);
        Button sortDate = findViewById(R.id.sortByDate);
        Button sortLike = findViewById(R.id.sortByLike);
        Button logout = findViewById(R.id.logout);
        Button profil = findViewById(R.id.profil_button);
        adminFlow = findViewById(R.id.adminFlow);
        membreButton = findViewById(R.id.membresButton);
        demandeAdhesionButton = findViewById(R.id.adhesionButton);

        FloatingActionButton newPublicationYoutubeButton = findViewById(R.id.newPublicationYoutubeButton);
        FloatingActionButton newPublicationSoundcloudButton = findViewById(R.id.newPublicationSoundcloudButton);
        publicationRecyclerView = findViewById(R.id.publicationRecyclerView);

        FloatingActionButton search_btn = findViewById(R.id.search_activity_pub);

        adminFlow.setVisibility(View.GONE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        publicationRecyclerView.setLayoutManager(layoutManager);

        final MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getGroupeMutableLiveData().observe(this, groupe -> {
            setTitle(groupe.getName());
            if(groupe.isPrivate() && groupe.isAdmin(user.getCurrentUser().getUid())){
                adminFlow.setVisibility(View.VISIBLE);
            }
        });

        mainViewModel.getPublications().observe(this, publications -> {
            adapter = new PublicationAdapter(publications);
            publicationRecyclerView.setAdapter(adapter);
        });

        mainViewModel.getGroupe(groupeId);
        mainViewModel.loadPublicationsDate(groupeId);

        sortTitre.setOnClickListener(view -> mainViewModel.loadPublicationsTitre(groupeId));

        sortDate.setOnClickListener(view -> mainViewModel.loadPublicationsDate(groupeId));

        sortLike.setOnClickListener(view -> mainViewModel.loadPublicationsLike(groupeId));

        membreButton.setOnClickListener(v -> {
            Intent intentMember = new Intent(getBaseContext(), GroupMemberActivity.class);
            intentMember.putExtra("GROUPE_ID", groupeId);
            startActivity(intentMember);
        });

        demandeAdhesionButton.setOnClickListener(v -> {
            Intent intentAdhesion = new Intent(getBaseContext(), AdminAdhesionActivity.class);
            intentAdhesion.putExtra("GROUPE_ID", groupeId);
            startActivity(intentAdhesion);
        });

        newPublicationYoutubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                intent.putExtra("GROUPE_ID", groupeId);
                intent.putExtra("SUPPORT", "youtube");
                startActivity(intent);
            }
        });

        newPublicationSoundcloudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                intent.putExtra("GROUPE_ID", groupeId);
                intent.putExtra("SUPPORT", "soundcloud");
                startActivity(intent);
            }


        search_btn.setOnClickListener(v -> {
            Intent intent12 = new Intent(getBaseContext(), SearchActivityPublication.class);
            intent12.putExtra("GROUPE_ID", groupeId);
            startActivity(intent12);
        });


        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent13 = new Intent(getBaseContext(), LoginActivity.class);
            intent13.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent13);
        });

        profil.setOnClickListener(v -> {
            String uid = user.getUid();
            Intent intent14 = new Intent(getBaseContext(), MainProfilActivity.class);
            intent14.putExtra("userID", uid);
            startActivity(intent14);
        });

        home.setOnClickListener(v -> {
            Intent intent15 = new Intent(getBaseContext(), HomeActivity.class);
            startActivity(intent15);
        });
    }


}