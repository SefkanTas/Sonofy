package github.com.kazetavi.sonofy.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.CommentaireFirestore;
import github.com.kazetavi.sonofy.data.api.PublicationFirestore;
import github.com.kazetavi.sonofy.data.model.Commentaire;
import github.com.kazetavi.sonofy.data.model.Publication;
import github.com.kazetavi.sonofy.ui.publication.PublicationActivity;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder> {

    private List<Publication> publications;

    public PublicationAdapter(List<Publication> publications) {
        this.publications = publications;
    }

    @NonNull
    @Override
    public PublicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.publication, parent, false);

        return new PublicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PublicationViewHolder holder, int position) {
        final Publication publication = publications.get(position);
        holder.titreTextView.setText(publication.getTitre());
        Picasso.get().load(publication.getMiniatureUrl()).into(holder.miniatureImageView);
        holder.likeCountTextView.setText(publication.getLikeCount().toString());
        holder.dislikeCountTextView.setText(publication.getDislikeCount().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getVideoUrl()));
                Intent intent = new Intent(view.getContext(), PublicationActivity.class);
                intent.putExtra("PUBLICATION_ID", publication.getUid());
                view.getContext().startActivity(intent);
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicationFirestore.incrementLike(publication);
            }
        });

        holder.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicationFirestore.incrementDislike(publication);
            }
        });

        holder.archive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // PublicationFirestore.createPublication(publication);
                PublicationFirestore.deletePublication(publication.getUid());
            }
        });

        PublicationFirestore.getPublicationRef(publication)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null && value.exists()){
                    holder.likeCountTextView.setText(value.get(PublicationFirestore.LIKE_COUNT).toString());
                    holder.dislikeCountTextView.setText(value.get(PublicationFirestore.DISLIKE_COUNT).toString());
                }
            }
        });

        CommentaireFirestore.getCollectionQueryByPublication(publication.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult() != null){
                    holder.commentaireCountTextView.setText(String.valueOf(task.getResult().size()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return publications.size();
    }

    public static class PublicationViewHolder extends RecyclerView.ViewHolder {

        TextView titreTextView;
        ImageView miniatureImageView;
        TextView likeCountTextView;
        TextView dislikeCountTextView;
        TextView commentaireCountTextView;

        LinearLayout likeButton;
        LinearLayout dislikeButton;

        ImageView archive_btn;

        public PublicationViewHolder(View v) {
            super(v);
            this.titreTextView = v.findViewById(R.id.titrePublicationTextView);
            this.miniatureImageView = v.findViewById(R.id.miniaturePublicationImageView);
            this.likeCountTextView = v.findViewById(R.id.likeCountTextView2);
            this.dislikeCountTextView = v.findViewById(R.id.dislikeCountTextView2);
            this.likeButton = v.findViewById(R.id.likeButton2);
            this.dislikeButton = v.findViewById(R.id.dislikeButton2);
            this.commentaireCountTextView = v.findViewById(R.id.commentCountTextView);

            this.archive_btn = v.findViewById(R.id.delete);
        }
    }


}
