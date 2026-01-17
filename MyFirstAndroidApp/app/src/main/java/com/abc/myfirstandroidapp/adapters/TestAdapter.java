package com.abc.myfirstandroidapp.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.myfirstandroidapp.R;
import com.abc.myfirstandroidapp.entity.User;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.UserViewHolder> {

    public interface onUserClick {
        void onLongClick(User user);
    }

    private Context context;
    private List<User> userList;
    private onUserClick listener;

    public TestAdapter(Context context, List<User> userList, onUserClick listener) {
        this.context = context;
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone());
        holder.tvCountry.setText(user.getCountry());
        holder.tvSkills.setText(user.getSkills());

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onLongClick(user);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone, tvCountry, tvSkills;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvSkills = itemView.findViewById(R.id.tvSkills);
        }
    }
}
