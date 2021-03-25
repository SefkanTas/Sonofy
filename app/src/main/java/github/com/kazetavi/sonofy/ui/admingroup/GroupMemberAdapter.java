package github.com.kazetavi.sonofy.ui.admingroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.api.GroupeFirestore;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.data.model.User;

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.GroupMemberViewHolder>{

    private List<User> members;
    private String groupId;

    public GroupMemberAdapter(List<User> members, String groupId){
        this.members = members;
        this.groupId = groupId;
    }

    @NonNull
    @Override
    public GroupMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group_member, parent, false);

        return new GroupMemberAdapter.GroupMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMemberViewHolder holder, int position) {
        User member = members.get(position);
        holder.memberUsername.setText("@" + member.getPseudo());

        holder.removeButton.setOnClickListener(v -> GroupeFirestore.getGroupWithId(groupId).addOnSuccessListener(documentSnapshot -> {
            Groupe groupe = documentSnapshot.toObject(Groupe.class);

            if(!groupe.isAdmin(member.getUserId())){
                groupe.removeMember(member.getUserId());
                GroupeFirestore.getCollection().document(groupId).update("membersId", groupe.getMembersId());
                members.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(holder.itemView.getContext(),
                        "Vous ne pouvez pas retirer un admin du groupe",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public static class GroupMemberViewHolder extends RecyclerView.ViewHolder{
        TextView memberUsername;
        Button removeButton;

        public GroupMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            this.memberUsername = itemView.findViewById(R.id.groupMemberTextView);
            this.removeButton = itemView.findViewById(R.id.retirerMembreButton);
        }
    }

}
