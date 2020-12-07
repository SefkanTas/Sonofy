package github.com.kazetavi.sonofy.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;
import github.com.kazetavi.sonofy.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private EditText uPrenom, uNom, uPseudo, uEmail,uMdp;
    private TextView LoginBtn;
    private Button inscription,login;
    private FirebaseAuth mAuth;
    private ProgressBar prgB;
    //private RadioGroup btn_groupe;
    //private RadioButton role;

    private RegisterViewModel registerViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        uPrenom = findViewById(R.id.prenom_user);
        uNom = findViewById(R.id.nom_user);
        uPseudo = findViewById(R.id.pseudo_user);
        uEmail = findViewById(R.id.mail_user);
        uMdp = findViewById(R.id.password_user);
        prgB = findViewById(R.id.progressBar2);
        inscription = findViewById(R.id.inscription);
        mAuth = FirebaseAuth.getInstance();
        //btn_groupe = findViewById(R.id.groupe);
        login = findViewById(R.id.log_button);

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getBaseContext(), ListGroupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //finish();
        }

        inscription.setOnClickListener(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
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
        //final int r = btn_groupe.getCheckedRadioButtonId();
        //role = (RadioButton) findViewById(r);
        //final String type = role.getText().toString().trim();

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

        /*if(type.isEmpty()){
            role.setError("Veuillez selectionner un type de compte");
            role.requestFocus();
        }*/

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
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User u = new User(name, firstname,pseudo, email,"normal"/*type*/);

                            FirebaseFirestore.getInstance().collection("Users")
                                    .add(u)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Nouvel utilisateur créé avec succès avec ID: " + documentReference.getId());
                                            prgB.setVisibility(View.VISIBLE);

                                            //Ajout du pseudo dans le displayName
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(pseudo).build();
                                            user.updateProfile(profileUpdates);

                                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Création d'un nouvel utilisateur échouée", e);
                                            prgB.setVisibility(View.GONE);
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Création nouvel utilisateur échoué . Veuillez réessayer", Toast.LENGTH_SHORT).show();
                            prgB.setVisibility(View.GONE);
                        }
                    }
                });
    }
}