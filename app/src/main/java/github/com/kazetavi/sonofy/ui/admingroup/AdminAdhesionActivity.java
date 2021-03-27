package github.com.kazetavi.sonofy.ui.admingroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import github.com.kazetavi.sonofy.R;

public class AdminAdhesionActivity extends AppCompatActivity {

    private AdhesionAdapter adapter;
    private RecyclerView adhesionMemberRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_adhesion);

        adhesionMemberRecyclerView = findViewById(R.id.adhesionMemberRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adhesionMemberRecyclerView.setLayoutManager(layoutManager);

        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");

        final GroupMemberViewModel groupMemberViewModel = new ViewModelProvider(this).get(GroupMemberViewModel.class);
        groupMemberViewModel.loadAdhesionRequestMember(groupeId);

        groupMemberViewModel.getUserMutableLiveData().observe(this, members -> {
            adapter = new AdhesionAdapter(members, groupeId);
            adhesionMemberRecyclerView.setAdapter(adapter);
        });
    }
}