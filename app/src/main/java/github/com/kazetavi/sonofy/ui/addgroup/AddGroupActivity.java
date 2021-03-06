package github.com.kazetavi.sonofy.ui.addgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;

public class AddGroupActivity extends AppCompatActivity {

    private EditText nomGroupEditText;
    private AddGroupViewModel addGroupViewModel;
    private Switch privateGroupSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        nomGroupEditText = findViewById(R.id.groupeNameEditText);
        privateGroupSwitch = findViewById(R.id.privateGroupSwitch);
        Button creerButton = findViewById(R.id.createGroupButton);

        addGroupViewModel = new ViewModelProvider(this).get(AddGroupViewModel.class);

        addGroupViewModel.isGroupCreated().observe(this, isGroupCreated -> {
            if(isGroupCreated){
                Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Toast.makeText(getBaseContext(), "Le groupe existe déjà", Toast.LENGTH_SHORT).show();
            }
        });


        creerButton.setOnClickListener(view -> {
            if(!nomGroupEditText.getText().toString().isEmpty()){
                String nomGroupe;

                nomGroupe = nomGroupEditText.getText().toString().trim();
                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                addGroupViewModel.checkGroupExistsAndCreate(nomGroupe, currentUserId, privateGroupSwitch.isChecked());
            }
        });

    }
}