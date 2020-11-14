package github.com.kazetavi.sonofy.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import github.com.kazetavi.sonofy.AddPublicationActivity;
import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.adapters.PublicationAdapter;
import github.com.kazetavi.sonofy.data.model.Publication;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton newPublicationButton;
    private RecyclerView publicationRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    //List<Publication> publications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newPublicationButton = findViewById(R.id.newPublicationButton);
        publicationRecyclerView = findViewById(R.id.publicationRecyclerView);

        layoutManager = new LinearLayoutManager(this);
        publicationRecyclerView.setLayoutManager(layoutManager);


        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getPublications().observe(this, new Observer<List<Publication>>() {
            @Override
            public void onChanged(List<Publication> publications) {
                adapter = new PublicationAdapter(publications);
                publicationRecyclerView.setAdapter(adapter);
            }
        });

        mainViewModel.loadPublications();

        newPublicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddPublicationActivity.class);
                startActivity(intent);
            }
        });
    }


}