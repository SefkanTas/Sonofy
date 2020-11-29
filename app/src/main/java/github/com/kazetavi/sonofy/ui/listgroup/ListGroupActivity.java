package github.com.kazetavi.sonofy.ui.listgroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.addgroup.AddGroupActivity;


public class ListGroupActivity extends AppCompatActivity {

    private FloatingActionButton addGroupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_group);

        addGroupButton = findViewById(R.id.addGroupButton);

        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddGroupActivity.class);
                startActivity(intent);
            }
        });

    }
}