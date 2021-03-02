package github.com.kazetavi.sonofy.ui.publication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.api.UserFirestore;
import github.com.kazetavi.sonofy.data.model.Emotion;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.data.model.User;

public class EmotionMainProfileAdapter extends RecyclerView.Adapter<EmotionMainProfileAdapter.EmotionMainProfileViewHolder>{

    private final List<Emotion> emotions;

    public EmotionMainProfileAdapter(List<Emotion> emotions) {
        this.emotions = emotions;
    }

    @NonNull
    @Override
    public EmotionMainProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_emotion_main_profil, parent, false);
        return new EmotionMainProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmotionMainProfileViewHolder holder, int position) {
        Emotion emotion = emotions.get(position);
        AtomicReference<String> groupId = new AtomicReference<>("erreur");
        switch (emotion.getEmotion()){
            case "veryHappy":
                holder.emotion_user.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24);
                break;
            case "happy":
                holder.emotion_user.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_48);
                break;
            case "sad":
                holder.emotion_user.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24);
                break;
            case "verySad":
                holder.emotion_user.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24);
                break;
            default:
                holder.emotion_user.setImageResource(R.drawable.ic_baseline_error_outline_24);
        }
        UserFirestore.getUser(emotion.getUserId()).addOnSuccessListener(documentSnapshot -> {
            User user = documentSnapshot.toObject(User.class);
            if(user.getPseudo() != null){
                holder.username_emotion.setText(user.getPseudo());
            }
            else {
                holder.username_emotion.setText("anonymous");
            }
        });
        PublicationFirestore.getPublicationById(emotion.getPublicationId()).addOnSuccessListener(documentSnapshot -> {
            Publication publication = documentSnapshot.toObject(Publication.class);
            if(publication.getTitre() != null){
                holder.publication_name.setText(publication.getTitre());
                groupId.set(publication.getGroupId());
                Log.d("Emotion group id "," suivant "+groupId);
            }else{
                String erreur = "Erreur";
                holder.publication_name.setText(erreur);
            }
        });
        if(groupId != null) {
            Log.d("Je suis "," la à groupid !=null");
            GroupeFirestore.getGroupWithId(groupId.get()).addOnSuccessListener(documentSnapshot -> {
                Groupe groupe = documentSnapshot.toObject(Groupe.class);
                Log.d("nom du groupe "," suivant "+groupe.getName());
                if (groupe.getName() != null) {
                    holder.group_name.setText(groupe.getName());
                } else {
                    holder.group_name.setText(groupId.get());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return emotions.size();
    }

    public class EmotionMainProfileViewHolder extends RecyclerView.ViewHolder {

        ImageView emotion_user;
        TextView username_emotion;
        TextView publication_name;
        TextView group_name;

        public EmotionMainProfileViewHolder(@NonNull View itemView) {
            super(itemView);
            this.emotion_user = itemView.findViewById(R.id.user_emotion);
            this.username_emotion = itemView.findViewById(R.id.username_emotion);
            this.publication_name = itemView.findViewById(R.id.publication_main_profile);
            this.group_name = itemView.findViewById(R.id.group_name_profile);
        }
    }
}
