package github.com.kazetavi.sonofy.ui.listgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.addgroup.AddGroupActivity;


public class ListGroupActivity extends AppCompatActivity {

    private FloatingActionButton addGroupButton;
    private RecyclerView groupeRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListGroupViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

//        Publication pub = new Publication("Un titre", "_4kHxtiuML0", "MHEt0SI2hTedMlakfJqJ");
//        PublicationFirestore.createPublication(pub);

        addGroupButton = findViewById(R.id.addGroupButton);
        groupeRecyclerView = findViewById(R.id.groupeRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        groupeRecyclerView.setLayoutManager(layoutManager);

        viewModel = new ViewModelProvider(this).get(ListGroupViewModel.class);

        viewModel.getGroupesLiveData().observe(this, new Observer<List<Groupe>>() {
            @Override
            public void onChanged(List<Groupe> groupes) {
                adapter = new GroupeAdapter(groupes);
                groupeRecyclerView.setAdapter(adapter);
            }
        });

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });

        viewModel.loadGroupes();

    }
}