package github.com.kazetavi.sonofy.ui.listgroup;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.ui.adhesiongroup.AdhesionGroupActivity;
import github.com.kazetavi.sonofy.ui.main.MainActivity;

public class GroupeAdapter extends RecyclerView.Adapter<GroupeAdapter.GroupeViewHolder> {

    private final List<Groupe> groupeList;
    private final String currentUserId;

    public GroupeAdapter(List<Groupe> groupeList) {
        this.groupeList = groupeList;
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    static class GroupeViewHolder  extends RecyclerView.ViewHolder{

        TextView groupNameTextView;

        public GroupeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.groupNameTextView = itemView.findViewById(R.id.groupNameTextView);
        }
    }



    @NonNull
    @Override
    public GroupeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.group, parent, false);
        return new GroupeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupeViewHolder holder, int position) {
        final Groupe groupe = groupeList.get(position);
        holder.groupNameTextView.setText(groupe.getName());

        if(groupe.canAccess(currentUserId)){
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("GROUPE_ID", groupe.getUid());
                view.getContext().startActivity(intent);
            });
        }
        else {
            holder.groupNameTextView.setTextColor(Color.GRAY);
            if(groupe.getWaitingApprovalUserId().contains(currentUserId)){
                holder.itemView.setOnClickListener(view -> Toast.makeText(view.getContext(), "Ce groupe est privé. Vous avez déjà une demande d'adhésion en cours pour ce groupe", Toast.LENGTH_LONG).show());
            }
            else {
                holder.itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(view.getContext(), AdhesionGroupActivity.class);
                    intent.putExtra("GROUPE_ID", groupe.getUid());
                    view.getContext().startActivity(intent);
                });
            }
        }
    }


    @Override
    public int getItemCount() {
        return groupeList.size();
    }


}











