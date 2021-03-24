package github.com.kazetavi.sonofy.ui.listgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.ui.addgroup.AddGroupActivity;
import github.com.kazetavi.sonofy.ui.homepage.HomeActivity;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;
import github.com.kazetavi.sonofy.ui.user.MainProfilActivity;
import github.com.kazetavi.sonofy.ui.search.SearchActivity;



public class ListGroupActivity extends AppCompatActivity {

    private RecyclerView groupeRecyclerView;
    private RecyclerView.Adapter adapter;
    private final FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

        Button logoutButton = findViewById(R.id.logoutButton);
        FloatingActionButton addGroupButton = findViewById(R.id.addGroupButton);
        Button profil = findViewById(R.id.button_p);
        groupeRecyclerView = findViewById(R.id.groupeRecyclerView);
        ImageButton home = findViewById(R.id.home_page1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        groupeRecyclerView.setLayoutManager(layoutManager);

        ListGroupViewModel viewModel = new ViewModelProvider(this).get(ListGroupViewModel.class);

        FloatingActionButton search_btn = findViewById(R.id.search_activity_group);

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

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = user.getUid();
                Intent intent = new Intent(getBaseContext(), MainProfilActivity.class);
                intent.putExtra("userID", uid);
                startActivity(intent);
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(intent);
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        viewModel.loadGroupes();

    }
}