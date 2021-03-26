package github.com.kazetavi.sonofy.ui.admingroup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.user.MainProfilActivity;

public class AdhesionAdapter extends RecyclerView.Adapter<AdhesionAdapter.AdhesionViewHolder>{

    private List<User> users;
    private String groupId;

    public AdhesionAdapter(List<User> users, String groupId){
        this.users = users;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public AdhesionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.membre_demande_adhesion, parent, false);

        return new AdhesionAdapter.AdhesionViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull AdhesionViewHolder holder, int position) {
        User user = users.get(position);

        holder.username.setText("@" + user.getPseudo());

        holder.username.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MainProfilActivity.class);
            intent.putExtra("userID", user.getUserId());
            v.getContext().startActivity(intent);
        });

        GroupeFirestore.getGroupWithId(groupId).addOnSuccessListener(documentSnapshot -> {
            Groupe groupe = documentSnapshot.toObject(Groupe.class);


            holder.accepterButton.setOnClickListener(v -> {
                groupe.acceptRequest(user.getUserId());
                GroupeFirestore.getCollection().document(groupId).update("waitingApprovalUserId", groupe.getWaitingApprovalUserId());
                GroupeFirestore.getCollection().document(groupId).update("membersId", groupe.getMembersId());
                users.remove(user);
                notifyDataSetChanged();
            });

            holder.refuserButton.setOnClickListener(v -> {
                groupe.refuseRequest(user.getUserId());
                GroupeFirestore.getCollection().document(groupId).update("waitingApprovalUserId", groupe.getWaitingApprovalUserId());
                users.remove(user);
                notifyDataSetChanged();
            });
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class AdhesionViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        Button accepterButton;
        Button refuserButton;

        public AdhesionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.username = itemView.findViewById(R.id.adhesionMemberTextView);
            this.accepterButton = itemView.findViewById(R.id.accepterButton);
            this.refuserButton = itemView.findViewById(R.id.refuserMembreButton);
        }
    }
}
