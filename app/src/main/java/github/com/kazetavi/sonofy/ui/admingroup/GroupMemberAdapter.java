package github.com.kazetavi.sonofy.ui.admingroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.User;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;

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

        holder.removeButton.setOnClickListener(v -> {
            //remove le membre
        });
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
