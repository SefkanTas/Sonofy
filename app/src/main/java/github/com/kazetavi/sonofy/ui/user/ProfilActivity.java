package github.com.kazetavi.sonofy.ui.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.homepage.HomeActivity;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseAuth user;
    private ProfilViewModel profilvm;
    private TextView nom;
    private TextView prenom;
    //private TextView email;
    private TextView pseudo;
    private ImageButton home ;
    private Button back;
    public static final String MODIFIER = "Modifier";
    public static final String ANNULER = "Annuler";

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        profilvm = new ViewModelProvider(this).get(ProfilViewModel.class);

        //Champs à afficher à partir de la base de données
        user = FirebaseAuth.getInstance();

        home = findViewById(R.id.imageButton3);
        back = findViewById(R.id.retour);
        nom = findViewById(R.id.nom_modif);
        prenom = findViewById(R.id.prenom_modif);
        //email = findViewById(R.id.mail_modif);
        pseudo = findViewById(R.id.pseudo_modif);

        //Bouton permettant la modification
        ImageView nom_mod = findViewById(R.id.nom_button);
        ImageView prenom_mod = findViewById(R.id.prenom_button);
        //ImageView email_mod = findViewById(R.id.email_button);
        ImageView pseudo_mod = findViewById(R.id.pseudo_button);


        profilvm.getUserMutableLiveData().observe(this, user -> {
            u = user;
            nom.setText(u.getNom());
            prenom.setText(u.getPrenom());
            //email.setText(u.getEmail());
            pseudo.setText(u.getPseudo());
        });

        profilvm.getUser(user.getCurrentUser().getUid());


        nom_mod.setOnClickListener(v -> boutonModifier(v, "Nom"));

        prenom_mod.setOnClickListener(v -> boutonModifier(v, "Prénom"));

        /*email_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonModEmail(v);
            }
        });*/

        pseudo_mod.setOnClickListener(v -> boutonModifier(v, "Pseudo"));

        home.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        back.setOnClickListener(v -> finish());
    }

    public void boutonModifier(View view, String champ){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_modification, null);
        final EditText etUsername = alertLayout.findViewById(R.id.edit_field);
        final TextView label = alertLayout.findViewById(R.id.attribut_popup);
        label.setText(champ);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modifier mon "+champ);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton(ANNULER, (dialog, which) -> { });
        alert.setPositiveButton(MODIFIER, (dialog, which) -> {
            String name = etUsername.getText().toString();
            if(champ.equals("Prénom")){
                profilvm.updatePrenom(user.getUid(), name);
            }else if (champ.equals("Nom")){
                profilvm.updateNom(user.getUid(), name);
            }else{
                profilvm.updatePseudo(user.getUid(), name);
            }
            recreate();
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
