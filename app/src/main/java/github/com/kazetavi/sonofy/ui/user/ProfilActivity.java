package github.com.kazetavi.sonofy.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.User;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseAuth user;
    private ProfilViewModel profilvm;
    private TextView nom,prenom,email,pseudo;
    private ImageView nom_mod, prenom_mod,email_mod,pseudo_mod;
    private EditText popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Champs à afficher à partir de la base de données
        user = FirebaseAuth.getInstance();
        User u = profilvm.getUser(user.getUid());
        nom = findViewById(R.id.nom_modif);
        prenom = findViewById(R.id.prenom_modif);
        email = findViewById(R.id.mail_modif);
        pseudo = findViewById(R.id.pseudo_modif);

        //Bouton permettant la modification
        nom_mod = findViewById(R.id.nom_button);
        prenom_mod = findViewById(R.id.prenom_button);
        email_mod = findViewById(R.id.email_button);
        pseudo_mod = findViewById(R.id.pseudo_button);

        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());
        email.setText(user.getCurrentUser().getEmail());
        pseudo.setText(u.getPseudo());

        user.getCurrentUser().getUid();

        nom_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String  modif = ;
                //profilvm.updateNom(user.getUid(),v);
            }
        });

        prenom_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        email_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pseudo_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}