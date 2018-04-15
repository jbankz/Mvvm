package bankzworld.com.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bankzworld.com.R;
import bankzworld.com.data.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    private static final String TAG = "UsersAdapter";
    private List<User> usersList;

    public UsersAdapter(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public UsersAdapter.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout, parent, false);
        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UsersViewHolder holder, int position) {
        User user = usersList.get(position);

        holder.mName.setText(user.getName());
        holder.mPosition.setText(user.getPosition());

        // retrieve the id from the room database
        int id = usersList.get(position).getId();

        // set the tag of itemView in the holder to the id
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        if (usersList == null)
            return 0;
        return usersList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mPosition;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mPosition = (TextView) itemView.findViewById(R.id.position);
        }
    }
}
