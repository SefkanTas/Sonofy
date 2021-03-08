package github.com.kazetavi.sonofy.ui.recommandation;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Map;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

public class RecommandationFragment extends Fragment {


    private RecommandationViewModel recommandationViewModel;

    private RecyclerView recommandedRecyclerView;
    private RecyclerView.Adapter adapter;

    public static RecommandationFragment newInstance() {
        return new RecommandationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recommandation_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Recommandations");

        recommandationViewModel = new ViewModelProvider(this).get(RecommandationViewModel.class);

        recommandedRecyclerView = getView().findViewById(R.id.recommandationRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recommandedRecyclerView.setLayoutManager(layoutManager);

        recommandationViewModel.getRecommendedPublicationsLiveData().observe(getViewLifecycleOwner(), recommandedPublications -> {
            adapter = new PublicationAdapter(recommandedPublications);
            recommandedRecyclerView.setAdapter(adapter);
        });
        recommandationViewModel.getRecommandation(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}