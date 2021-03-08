package github.com.kazetavi.sonofy.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;
import github.com.kazetavi.sonofy.ui.recommandation.RecommandationActivity;
import github.com.kazetavi.sonofy.ui.user.MainProfilActivity;
import github.com.kazetavi.sonofy.ui.user.ProfilActivity;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView publicationRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        final Intent intent = getIntent();

        Button logout = findViewById(R.id.logout1);
        Button profil = findViewById(R.id.profil_button1);
        Button groupes = findViewById(R.id.listeGroupe);
        Button recommandations = findViewById(R.id.recommendation1);


        publicationRecyclerView = findViewById(R.id.publicationRecyclerView1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        publicationRecyclerView.setLayoutManager(layoutManager);

        final HomeViewModel mainViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        mainViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                publicationRecyclerView.setAdapter(adapter);
            }
        });

        mainViewModel.loadPublicationsDate();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainProfilActivity.class);
                startActivity(intent);
            }
        });

        recommandations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RecommandationActivity.class);
                startActivity(intent);
            }
        });

        groupes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
                startActivity(intent);
            }
        });
    }


}

