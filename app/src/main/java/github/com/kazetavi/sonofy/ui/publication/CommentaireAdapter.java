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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.CommentaireFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.CommentaireViewHolder> {

    private List<Commentaire> commentaires;

    public CommentaireAdapter(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }


    @NonNull
    @Override
    public CommentaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.commentaire, parent, false);

        return new CommentaireAdapter.CommentaireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentaireViewHolder holder, int position) {
        final Commentaire commentaire = commentaires.get(position);
        //holder.commentaireTextView.setImageResource(R.drawable.emoji_sad);
        holder.sadCountTextView.setText(commentaire.getSadCount().toString());
        if(commentaire.getUsername() != null && !commentaire.getUsername().isEmpty()){
            holder.usernameTextView.setText("@" + commentaire.getUsername());
        }

        holder.sadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentaireFirestore.incrementSad(commentaire);
            }
        });

        holder.angryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentaireFirestore.incrementAngry(commentaire);
            }
        });

        holder.happyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentaireFirestore.incrementHappy(commentaire);
            }
        });

        holder.heoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentaireFirestore.incrementHeo(commentaire);
            }
        });

        holder.superrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentaireFirestore.incrementSuperr(commentaire);
            }
        });

        CommentaireFirestore.getPublicationRef(commentaire)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value != null && value.exists()){
                            holder.sadCountTextView.setText(value.get(CommentaireFirestore.SAD_COUNT).toString());
                           //holder.dislikeCountTextView.setText(value.get(PublicationFirestore.DISLIKE_COUNT).toString());
                        }
                    }
                });




    }

    @Override
    public int getItemCount() {
        return commentaires.size();
    }

    public static class CommentaireViewHolder extends RecyclerView.ViewHolder{

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


        public CommentaireViewHolder(@NonNull View itemView) {
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
