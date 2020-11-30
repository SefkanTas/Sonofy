package github.com.kazetavi.sonofy.ui.addgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.addpublication.AddPublicationViewModel;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;

public class AddGroupActivity extends AppCompatActivity {

    private EditText nomGroupEditText;
    private Button creerButton;
    private AddGroupViewModel addGroupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);

        nomGroupEditText = findViewById(R.id.groupeNameEditText);
        creerButton = findViewById(R.id.createGroupButton);

        addGroupViewModel = new ViewModelProvider(this).get(AddGroupViewModel.class);

        addGroupViewModel.isGroupCreated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isGroupCreated) {
                if(isGroupCreated){
                    Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Le groupe existe déjà", Toast.LENGTH_SHORT).show();
                }
            }
        });


        creerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nomGroupEditText.getText().toString().isEmpty()){
                    String nomGroupe;

                    nomGroupe = nomGroupEditText.getText().toString().trim();

                    addGroupViewModel.checkGroupExistsAndCreate(nomGroupe);
                }
            }
        });

    }
}