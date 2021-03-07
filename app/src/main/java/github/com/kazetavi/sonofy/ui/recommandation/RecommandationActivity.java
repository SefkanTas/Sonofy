package github.com.kazetavi.sonofy.ui.recommandation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import github.com.kazetavi.sonofy.R;

public class RecommandationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommandation_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecommandationFragment.newInstance())
                    .commitNow();
        }
    }
}