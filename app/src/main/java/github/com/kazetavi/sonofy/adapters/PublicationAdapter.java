package github.com.kazetavi.sonofy.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.models.Publication;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationAdapter.PublicationViewHolder> {

    private List<Publication> publications;
    FirebaseFirestore db;

    public PublicationAdapter(List<Publication> publications) {
        this.publications = publications;
        db = FirebaseFirestore.getInstance();
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
        holder.commentsCountTextView.setText(publication.getNumComments().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(publication.getVideoUrl()));
                view.getContext().startActivity(intent);
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("publications")
                        .document(publication.getUid());
                docRef.update("like_count", FieldValue.increment(1));

            }
        });

        holder.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("publications")
                        .document(publication.getUid());
                docRef.update("dislike_count", FieldValue.increment(1));

            }
        });
        holder.commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = db.collection("publications")
                        .document(publication.getUid());
                docRef.update("comments_count", FieldValue.increment(1));

            }
        });


        final DocumentReference pubRef = db.collection("publications")
                .document(publication.getUid());

        pubRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null && value.exists()){
                    holder.likeCountTextView.setText(value.get("like_count").toString());
                    holder.dislikeCountTextView.setText(value.get("dislike_count").toString());
                    holder.commentsCountTextView.setText(value.get("comments_count").toString());
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
        TextView commentsCountTextView;

        LinearLayout likeButton;
        LinearLayout dislikeButton;
        LinearLayout commentsButton;

        public PublicationViewHolder(View v) {
            super(v);
            this.titreTextView = v.findViewById(R.id.titrePublicationTextView);
            this.miniatureImageView = v.findViewById(R.id.miniaturePublicationImageView);
            this.likeCountTextView = v.findViewById(R.id.likeCountTextView);
            this.dislikeCountTextView = v.findViewById(R.id.dislikeCountTextView);
            this.commentsButton = v.findViewById(R.id.commentsCountTextView);
            this.likeButton = v.findViewById(R.id.likeButton);
            this.dislikeButton = v.findViewById(R.id.dislikeButton);
            this.commentsButton = v.findViewById(R.id.commentsButton);
        }
    }


}
