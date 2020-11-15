package github.com.kazetavi.sonofy.auth.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.MainActivity;
import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.auth.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {
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
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = uEmail.toString().trim();
                String mdp = uMdp.toString().trim();

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