package github.com.kazetavi.sonofy.ui.publication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Commentaire;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.CommentaireViewHolder> {

    private final List<Commentaire> commentaires;

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
        Commentaire commentaire = commentaires.get(position);
        holder.commentaireTextView.setText(commentaire.getContent());

        if(commentaire.getUsername() != null && !commentaire.getUsername().isEmpty()){
            holder.usernameTextView.setText("@" + commentaire.getUsername());
        }
    }

    @Override
    public int getItemCount() {
        return commentaires.size();
    }

    public static class CommentaireViewHolder extends RecyclerView.ViewHolder{

        TextView commentaireTextView;
        TextView usernameTextView;

        public CommentaireViewHolder(@NonNull View itemView) {
            super(itemView);
            this.commentaireTextView = itemView.findViewById(R.id.commentaireTextView);
            this.usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }


}
