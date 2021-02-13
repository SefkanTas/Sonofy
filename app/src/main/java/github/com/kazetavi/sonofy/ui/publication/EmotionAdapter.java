package github.com.kazetavi.sonofy.ui.publication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.User;

public class EmotionAdapter extends RecyclerView.Adapter<EmotionAdapter.EmotionViewHolder>{

    private final List<Emotion> emotions;

    public EmotionAdapter(List<Emotion> emotions) {
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public EmotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_emotion, parent, false);
        return new EmotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionViewHolder holder, int position) {
        Emotion emotion = emotions.get(position);
        switch (emotion.getEmotion()){
            case "veryHappy":
                holder.emotionIV.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24);
                break;
            case "happy":
                holder.emotionIV.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_48);
                break;
            case "sad":
                holder.emotionIV.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24);
                break;
            case "verySad":
                holder.emotionIV.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24);
                break;
            default:
                holder.emotionIV.setImageResource(R.drawable.ic_baseline_error_outline_24);
        }
        UserFirestore.getUser(emotion.getUserId()).addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if(user.getPseudo() != null){
                holder.usernameTV.setText(user.getPseudo());
            }
            else {
                holder.usernameTV.setText("anonymous");
            }
        });
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    public class EmotionViewHolder extends RecyclerView.ViewHolder {

        ImageView emotionIV;
        TextView usernameTV;

        public EmotionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.emotionIV = itemView.findViewById(R.id.userEmotionIV);
            this.usernameTV = itemView.findViewById(R.id.emotionUsernameTV);
        }
    }
}
