package github.com.kazetavi.sonofy.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.listgroup.GroupeAdapter;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

public class SearchActivity extends AppCompatActivity{
    private EditText recherche;
    private ImageButton btn_recherche, accueil;
    private RecyclerView liste_groupe;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager1;

    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recherche = findViewById(R.id.search_field);
        btn_recherche = findViewById(R.id.search_button);
        accueil = findViewById(R.id.back_home);
        liste_groupe = findViewById(R.id.groupe_list);
        layoutManager1 = new LinearLayoutManager(this);
        liste_groupe.setLayoutManager(layoutManager1);


        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");


        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        searchViewModel.getGroupes().observe(this, new Observer<List<Groupe>>() {
            @Override
            public void onChanged(List<Groupe> groupes) {
                adapter = new GroupeAdapter(groupes);
                liste_groupe.setAdapter(adapter);
            }
        });

        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nameg = recherche.getText().toString().trim();


                if(!nameg.isEmpty()) {
                    searchViewModel.searchGroup(nameg);
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