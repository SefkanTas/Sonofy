package github.com.kazetavi.sonofy.ui.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
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

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.ui.homepage.HomeActivity;
import github.com.kazetavi.sonofy.ui.listgroup.ListGroupActivity;
import github.com.kazetavi.sonofy.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();
    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login);
        TextView new_count = findViewById(R.id.register);
        //TextView mdpo = findViewById(R.id.mdpoublie);
        ProgressBar loadingProgressBar = findViewById(R.id.loading);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        /*


        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });


        loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

    */

        //reinitialisation mdp
        /*
        * FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "user@example.com";

        auth.sendPasswordResetEmail(emailAddress)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Email sent.");
                }
            }
        });
        *
        * */
        loginButton.setOnClickListener(this);

        new_count.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login){
            loginUser();
        }
    }

    private void loginUser(){
        final String mail = usernameEditText.getText().toString().trim();
        final String mdp = passwordEditText.getText().toString().trim();

        if(mail.isEmpty()){
            usernameEditText.setError("Veuillez saisir votre email");
            usernameEditText.requestFocus();
            return;
        }

        if(mdp.isEmpty()){
            passwordEditText.setError("Veuillez saisir votre mot de passe");
            passwordEditText.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(mail,mdp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(LoginActivity.this, "Authentication réussie",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), HomeActivity.class));
                }else{
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}