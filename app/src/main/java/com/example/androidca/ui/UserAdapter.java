package com.example.androidca.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidca.models.User;
import com.example.androidca.R;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public UserAdapter(List<User> users, OnItemClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.textViewUsername.setText(user.getUsername());
        holder.textViewEmail.setText(user.getEmail());
        holder.textViewAddress.setText(user.getAddress());
        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername, textViewEmail, textViewAddress;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public User getUser(int position) {
        return users.get(position);
    }

    public void setUsers(List<User> newUsers) {
        this.users = newUsers;
        notifyDataSetChanged();
    }
}
