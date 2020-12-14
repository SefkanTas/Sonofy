package github.com.kazetavi.sonofy.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.auth.FirebaseAuth;


import java.util.List;

import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;
import github.com.kazetavi.sonofy.ui.addpublication.AddPublicationActivity;
import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.search.SearchActivity;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newPublicationButton, search_btn;
    private RecyclerView publicationRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private Button sortTitre,sortDate, sortLike, logout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");

        sortTitre = findViewById(R.id.sortByTitre);
        sortDate = findViewById(R.id.sortByDate);
        sortLike = findViewById(R.id.sortByLike);
        logout = findViewById(R.id.logout);

        newPublicationButton = findViewById(R.id.newPublicationButton);
        publicationRecyclerView = findViewById(R.id.publicationRecyclerView);

        search_btn = findViewById(R.id.search_activity);

        layoutManager = new LinearLayoutManager(this);
        publicationRecyclerView.setLayoutManager(layoutManager);

        final MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getGroupeMutableLiveData().observe(this, new Observer<Groupe>() {
            @Override
            public void onChanged(Groupe groupe) {
                setTitle(groupe.getName());
            }
        });

        mainViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                publicationRecyclerView.setAdapter(adapter);
            }
        });

        mainViewModel.getGroupe(groupeId);
        mainViewModel.loadPublicationsDate(groupeId);

        sortTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsTitre(groupeId);
            }
        });

        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsDate(groupeId);
            }
        });

        sortLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsLike(groupeId);
            }
        });

        newPublicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                intent.putExtra("GROUPE_ID", groupeId);
                startActivity(intent);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                intent.putExtra("GROUPE_ID", groupeId);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


}