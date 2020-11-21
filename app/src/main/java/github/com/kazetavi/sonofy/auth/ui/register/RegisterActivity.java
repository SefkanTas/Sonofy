package github.com.kazetavi.sonofy.auth.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = this.getClass().getSimpleName();
    private EditText uPrenom, uNom, uPseudo, uEmail,uMdp;
    private TextView LoginBtn;
    private Button inscription;
    private FirebaseAuth mAuth;
    private ProgressBar prgB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uPrenom = findViewById(R.id.prenom_user);
        uNom = findViewById(R.id.nom_user);
        uPseudo = findViewById(R.id.pseudo_user);
        uEmail = findViewById(R.id.mail_user);
        uMdp = findViewById(R.id.password_user);
        prgB = findViewById(R.id.progressBar2);
        inscription = findViewById(R.id.inscription);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        inscription.setOnClickListener(this);
               /* new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(TextUtils.isEmpty(email)){
                    uEmail.setError("Email requis");
                    return;
                }

                if(TextUtils.isEmpty(mdp)){
                    uMdp.setError("Mot de passe requis");
                    return;
                }

                if(mdp.length() < 8){
                    uMdp.setError("Votre mot de passe doit faire au minimum 8 caractères");
                    return;
                }

                prgB.setVisibility(View.VISIBLE);

                //On enregistre l'utilisateur et ses données
                mAuth.createUserWithEmailAndPassword(email, mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Compte créé", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this,"Erreur ! " + task.getException(), Toast.LENGTH_SHORT).show();
                            prgB.setVisibility(View.GONE);
                        }
                    }
                });
            }

        });*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.inscription) {
            registerUser();
        }
    }

    private void registerUser() {
        final String email = uEmail.getText().toString().trim();
        String mdp = uMdp.getText().toString().trim();
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
                            User u = new User(name, firstname,pseudo, email);

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

    /*
     * Dans main_activity
     * Ajouter une variable Button logout
     * Dans MainActivity aussi
     * puis mettre le code suivant dans onCreate():
     *
     * logout = findViewById(R.id.deconnexion_user);
     * logout.setOnClickListener(new View.OnClickListener(){
     * @Override
     *      public void onClick(View v) {
     *          FireBaseAuth.getInstance().signOut();
     *          startActivity(new Intent(getApplicationContext(), MainActivity.class));
     *          finish();
     *      }
     *
     * );
    *
    * */
}