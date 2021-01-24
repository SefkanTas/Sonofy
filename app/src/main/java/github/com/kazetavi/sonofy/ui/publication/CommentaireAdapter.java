package github.com.kazetavi.sonofy.ui.publication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.CommentaireFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

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
    public void onBindViewHolder(@NonNull CommentaireViewHolder holder, int position) {
        final Commentaire commentaire = commentaires.get(position);
        holder.commentaireTextView.setText(commentaire.getContent());

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
    }

    @Override
    public int getItemCount() {
        return commentaires.size();
    }

    public static class CommentaireViewHolder extends RecyclerView.ViewHolder{

        ImageView sadButton;
        ImageView angryButton;
        ImageView superrButton;
        ImageView happyButton;
        ImageView heoButton;
        TextView commentaireTextView;
        TextView usernameTextView;

        public CommentaireViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sadButton = itemView.findViewById(R.id.sad);
            this.happyButton = itemView.findViewById(R.id.happy);
            this.superrButton = itemView.findViewById(R.id.superr);
            this.heoButton = itemView.findViewById(R.id.heo);
            this.angryButton = itemView.findViewById(R.id.angry);
            this.commentaireTextView = itemView.findViewById(R.id.commentaireTextView);
            this.usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }


}
