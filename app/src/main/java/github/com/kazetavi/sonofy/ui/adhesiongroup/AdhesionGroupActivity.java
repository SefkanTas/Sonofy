package github.com.kazetavi.sonofy.ui.adhesiongroup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;

public class AdhesionGroupActivity extends AppCompatActivity {

    private Button ouiButton;
    private Button nonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhesion_group);

        final Intent intent = getIntent();
        final String groupeId = intent.getStringExtra("GROUPE_ID");

        ouiButton = findViewById(R.id.ouiButton);
        nonButton = findViewById(R.id.nonButton);

        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ouiButton.setOnClickListener(v -> GroupeFirestore.getGroupWithId(groupeId).addOnSuccessListener(documentSnapshot -> {
            Groupe groupe = documentSnapshot.toObject(Groupe.class);
            groupe.getWaitingApprovalUserId().add(currentUserId);
            GroupeFirestore.getCollection().document(groupeId)
                    .update("waitingApprovalUserId", groupe.getWaitingApprovalUserId());
            Toast.makeText(this, "Vous venez de faire une demander d'adhésion à un groupe", Toast.LENGTH_LONG)
                    .show();
            finish();
        }));

        nonButton.setOnClickListener(v -> finish());


    }
}