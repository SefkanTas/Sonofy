package github.com.kazetavi.sonofy.ui.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.User;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseAuth user;
    private ProfilViewModel profilvm;
    private TextView nom,prenom,email,pseudo;
    private ImageView nom_mod, prenom_mod,email_mod,pseudo_mod;

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
               boutonModNom(v);
            }
        });

        prenom_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonModPrenom(v);
            }
        });

        email_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonModEmail(v);
            }
        });

        pseudo_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutonModPseudo(v);
            }
        });
    }

    public void boutonModPrenom(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_modification, null);
        final EditText etUsername = alertLayout.findViewById(R.id.edit_field);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modifier mon prénom");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                profilvm.updatePrenom(user.getUid(), name);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void boutonModNom(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_modification, null);
        final EditText etUsername = alertLayout.findViewById(R.id.edit_field);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modifier mon nom");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                profilvm.updateNom(user.getUid(), name);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void boutonModPseudo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_modification, null);
        final EditText etUsername = alertLayout.findViewById(R.id.edit_field);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modifier mon pseudo");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                profilvm.updatePseudo(user.getUid(), name);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void boutonModEmail(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.popup_modification, null);
        final EditText etUsername = alertLayout.findViewById(R.id.edit_field);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Modifier mon email");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                profilvm.updateEmail(user.getUid(), name);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

}