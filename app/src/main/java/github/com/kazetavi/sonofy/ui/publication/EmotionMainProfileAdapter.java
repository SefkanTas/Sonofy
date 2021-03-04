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
        PublicationFirestore.getPublicationById(emotion.getPublicationId()).addOnSuccessListener(documentSnapshot -> {
            Publication publication = documentSnapshot.toObject(Publication.class);
            assert publication != null;
            if(publication.getTitre() != null){
                String s = "Publication : " + publication.getTitre();
                holder.publication_name.setText(s);
                groupName(holder, publication.getGroupId());
            }else{
                String erreur = "Erreur";
                holder.publication_name.setText(erreur);
            }
        });
    }

    //Permet de recupérer le nom du groupe pour l'affichage
    public void groupName(@NonNull EmotionMainProfileViewHolder holder, String groupId){
        if(groupId != null) {
            GroupeFirestore.getGroupWithId(groupId).addOnSuccessListener(documentSnapshot -> {
                Groupe groupe = documentSnapshot.toObject(Groupe.class);
                assert groupe != null;
                if (groupe.getName() != null) {
                    String group = "Groupe : " + groupe.getName();
                    holder.group_name.setText(group);
                } else {
                    holder.group_name.setText(groupId);
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
