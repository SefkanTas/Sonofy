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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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


        mainViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                publicationRecyclerView.setAdapter(adapter);
            }
        });

        mainViewModel.loadPublicationsDate();

        sortTitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsTitre();
            }
        });

        sortDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsDate();
            }
        });

        sortLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.loadPublicationsLike();
            }
        });

        newPublicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                startActivity(intent);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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