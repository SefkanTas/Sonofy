package github.com.kazetavi.sonofy.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import github.com.kazetavi.sonofy.R;

public class ProfilActivity extends AppCompatActivity {

    private FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        user = FirebaseAuth.getInstance();
    }
}