package github.com.kazetavi.sonofy.auth.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import github.com.kazetavi.sonofy.MainActivity;
import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.auth.data.model.User;
import github.com.kazetavi.sonofy.auth.ui.login.LoginActivity;
import github.com.kazetavi.sonofy.auth.ui.login.LoginViewModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private EditText uPrenom, uNom, uPseudo, uEmail,uMdp;
    private TextView LoginBtn;
    private Button inscription;
    private FirebaseAuth mAuth;
    private ProgressBar prgB;
    private RadioGroup btn_groupe;
    private RadioButton artiste, normal;

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
        btn_groupe = findViewById(R.id.groupe);
        artiste = findViewById(R.id.u_artiste);
        normal = findViewById(R.id.u_normal);

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        inscription.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.inscription) {
            final String role = checkRole(v);
            registerUser(role);
        }
    }

    public String checkRole(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.u_artiste:
                if (checked)
                    return "artiste";
                    break;
            case R.id.u_normal:
                if (checked)
                    return "normal";
                    break;
        }
        btn_groupe.requestFocus();
        return "";
    }

    private void registerUser(final String role) {
        final String email = uEmail.getText().toString().trim();
        final String mdp = uMdp.getText().toString().trim();
        final String name = uNom.getText().toString().trim();
        final String firstname = uPrenom.getText().toString().trim();
        final String pseudo = uPseudo.getText().toString().trim();

        //Vérification des champs à remplir
        if(firstname.isEmpty()){
            uPrenom.setError("Veuillez saisir votre prénom");
            uPrenom.requestFocus();
        }

        if(name.isEmpty()){
            uNom.setError("Veuillez saisir votre nom");
            uNom.requestFocus();
        }

        if(pseudo.isEmpty()){
            uPseudo.setError("Veuillez saisir votre pseudonyme");
            uPseudo.requestFocus();
        }

        if(email.isEmpty()){
            uEmail.setError("Veuillez saisir votre email");
            uEmail.requestFocus();
        }

        if( !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            uEmail.setError("Veuillez saisir une adresse mail valide");
            uEmail.requestFocus();
        }

        if(mdp.isEmpty()){
            uMdp.setError("Veuillez saisir un mot de passe");
            uMdp.requestFocus();
        }

        if(mdp.length() < 8){
            uMdp.setError("Veuillez saisir un mot de passe à 8 caractères");
            uMdp.requestFocus();
        }

        prgB.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,mdp)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User u = new User(name, firstname,pseudo, email,role);

                            FirebaseFirestore.getInstance().collection("Users")
                                    .add(u)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "Nouvel utilisateur créé avec succès avec ID: " + documentReference.getId());
                                            prgB.setVisibility(View.VISIBLE);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Création d'un nouvel utilisateur échouéet", e);
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