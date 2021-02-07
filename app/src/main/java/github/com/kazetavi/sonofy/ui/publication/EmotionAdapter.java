package github.com.kazetavi.sonofy.ui.publication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.EmotionFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder> {

    private List<Emotion> emotions;

    public EmotionAdapter(List<Emotion> emotions) {
        this.emotions = emotions;
    }


    @NonNull
    @Override
    public EmotionAdapter.EmotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.commentaire, parent, false);

        return new EmotionAdapter.EmotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EmotionViewHolder holder, int position) {
        final Emotion emotion = emotions.get(position);
        //holder.commentaireTextView.setImageResource(R.drawable.emoji_sad);
        holder.sadCountTextView.setText(emotion.getSadCount().toString());
        if(emotion.getUsername() != null && !emotion.getUsername().isEmpty()){
            holder.usernameTextView.setText("@" + emotion.getUsername());
            holder.sadCountTextView.setText(emotion.getSadCount().toString());
        }

        holder.sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmotionFirestore.incrementSad(emotion);
            }
        });
        /*
        holder.angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmotionFirestore.incrementAngry(emotion);
            }
        });

        holder.happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmotionFirestore.incrementHappy(emotion);
            }
        });

        holder.heoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmotionFirestore.incrementHeo(emotion);
            }
        });

        holder.superrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmotionFirestore.incrementSuperr(emotion);
            }
        });

         */
        EmotionFirestore.getPublicationRef(emotion).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null && value.exists()){
                    holder.sadCountTextView.setText(value.get(EmotionFirestore.SAD_COUNT).toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    public static class EmotionViewHolder extends RecyclerView.ViewHolder{

        LinearLayout sadButton;
        LinearLayout angryButton;
        LinearLayout superrButton;
        LinearLayout happyButton;
        LinearLayout heoButton;
        ImageView commentaireTextView;
        TextView usernameTextView;
        TextView likeCountTextView;
        TextView dislikeCountTextView;
        TextView sadCountTextView;
        TextView angryCountTextView;
        TextView superrCountTextView;
        TextView happyCountTextView;
        TextView heoCountTextView;


        public EmotionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sadButton = itemView.findViewById(R.id.sadButton);
            this.happyButton = itemView.findViewById(R.id.happyButton);
            this.superrButton = itemView.findViewById(R.id.superrButton);
            this.heoButton = itemView.findViewById(R.id.heoButton);
            this.angryButton = itemView.findViewById(R.id.angryButton);
            this.commentaireTextView = itemView.findViewById(R.id.commentaireTextView);
            this.usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }


}

