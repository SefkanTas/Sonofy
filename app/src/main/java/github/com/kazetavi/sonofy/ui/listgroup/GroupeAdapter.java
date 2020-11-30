package github.com.kazetavi.sonofy.ui.listgroup;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import github.com.kazetavi.sonofy.R;
import github.com.kazetavi.sonofy.data.model.Groupe;
import github.com.kazetavi.sonofy.ui.main.MainActivity;
import github.com.kazetavi.sonofy.ui.main.PublicationAdapter;
import github.com.kazetavi.sonofy.ui.publication.PublicationActivity;

public class GroupeAdapter extends RecyclerView.Adapter<GroupeAdapter.GroupeViewHolder> {

    private List<Groupe> groupeList;

    public GroupeAdapter(List<Groupe> groupeList) {
        this.groupeList = groupeList;
    }

    class GroupeViewHolder  extends RecyclerView.ViewHolder{

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("GROUPE_ID", groupe.getUid());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groupeList.size();
    }


}











