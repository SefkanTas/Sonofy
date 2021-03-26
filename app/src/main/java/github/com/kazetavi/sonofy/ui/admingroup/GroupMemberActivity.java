package github.com.kazetavi.sonofy.ui.admingroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import github.com.kazetavi.sonofy.R;

public class GroupMemberActivity extends AppCompatActivity {

    private GroupMemberAdapter adapter;
    private RecyclerView groupMemberRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);

        groupMemberRecyclerView = findViewById(R.id.membreRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        groupMemberRecyclerView.setLayoutManager(layoutManager);

        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");

        final GroupMemberViewModel groupMemberViewModel = new ViewModelProvider(this).get(GroupMemberViewModel.class);
        groupMemberViewModel.loadGroupMembers(groupeId);

        groupMemberViewModel.getUserMutableLiveData().observe(this, members -> {
            adapter = new GroupMemberAdapter(members, groupeId);
            groupMemberRecyclerView.setAdapter(adapter);
        });


    }


}