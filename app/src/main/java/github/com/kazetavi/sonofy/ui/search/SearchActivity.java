package github.com.kazetavi.sonofy.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

public class SearchActivity extends AppCompatActivity{
    private EditText recherche;
    private ImageButton btn_recherche, accueil;
    private RecyclerView liste_pub;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recherche = findViewById(R.id.search_field);
        btn_recherche = findViewById(R.id.search_button);
        accueil = findViewById(R.id.back_home);
        liste_pub = findViewById(R.id.publication_list);

        layoutManager = new LinearLayoutManager(this);
        liste_pub.setLayoutManager(layoutManager);


        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        searchViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                liste_pub.setAdapter(adapter);
            }
        });

        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String titre = recherche.getText().toString().trim();
                if(!titre.isEmpty()) {
                    searchViewModel.searchPublicationsTitle(titre);
                }
            }
        });

        accueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}