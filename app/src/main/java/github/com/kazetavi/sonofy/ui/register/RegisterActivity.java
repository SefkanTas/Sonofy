package github.com.kazetavi.sonofy.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private EditText uPrenom;
    private EditText uNom;
    private EditText uPseudo;
    private EditText uEmail;
    private EditText uMdp;
    private FirebaseAuth mAuth;
    private RadioGroup btn_groupe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uPrenom = findViewById(R.id.prenom_user);
        uNom = findViewById(R.id.nom_user);
        uPseudo = findViewById(R.id.pseudo_user);
        uEmail = findViewById(R.id.mail_user);
        uMdp = findViewById(R.id.password_user);
        Button inscription = findViewById(R.id.inscription);
        mAuth = FirebaseAuth.getInstance();
        btn_groupe = findViewById(R.id.groupe);
        Button login = findViewById(R.id.log_button);

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        inscription.setOnClickListener(this);

        login.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.inscription) {
            registerUser();
        }
    }

    private void registerUser() {
        final String email = uEmail.getText().toString().trim();
        final String mdp = uMdp.getText().toString().trim();
        final String name = uNom.getText().toString().trim();
        final String firstname = uPrenom.getText().toString().trim();
        final String pseudo = uPseudo.getText().toString().trim();
        final int r = btn_groupe.getCheckedRadioButtonId();
        RadioButton role = (RadioButton) findViewById(r);
        final String type = role.getText().toString().trim();

        //Vérification des champs à remplir
        if(name.isEmpty()){
            uNom.setError("Veuillez saisir votre nom");
            uNom.requestFocus();
            return;
        }

        if(firstname.isEmpty()){
            uPrenom.setError("Veuillez saisir votre prénom");
            uPrenom.requestFocus();
            return;
        }

        if(pseudo.isEmpty()){
            uPseudo.setError("Veuillez saisir votre pseudonyme");
            uPseudo.requestFocus();
            return;
        }

        if(type.isEmpty()){
            role.setError("Veuillez selectionner un type de compte");
            role.requestFocus();
            return;
        }

        if(email.isEmpty()){
            uEmail.setError("Veuillez saisir votre email");
            uEmail.requestFocus();
            return;
        }

        if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            uEmail.setError("Veuillez saisir une adresse mail valide");
            uEmail.requestFocus();
            return;
        }

        if(mdp.isEmpty()){
            uMdp.setError("Veuillez saisir un mot de passe");
            uMdp.requestFocus();
            return;
        }

        if(mdp.length() < 8){
            uMdp.setError("Veuillez saisir un mot de passe à 8 caractères");
            uMdp.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,mdp)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        User u = new User(name, firstname,pseudo, email,type);

                        FirebaseFirestore.getInstance().collection("Users")
                                .document(mAuth.getCurrentUser().getUid())
                                .set(u)
                                .addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Log.d(TAG, "Nouvel utilisateur créé avec succès avec ID: " + mAuth.getCurrentUser().getUid());

                                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(e -> Log.w(TAG, "Création d'un nouvel utilisateur échouée", e));
                    }else{
                        Toast.makeText(RegisterActivity.this, "Création nouvel utilisateur échoué . Veuillez réessayer", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}