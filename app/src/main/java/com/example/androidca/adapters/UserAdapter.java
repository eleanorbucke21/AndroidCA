package com.example.androidca.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidca.R;
import com.example.androidca.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private OnItemClickListener onItemClickListener;

    public UserAdapter(List<User> users, OnItemClickListener onItemClickListener) {
        this.users = users;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
        holder.address.setText(user.getAddressLine1()); // Assuming address line 1 is used here
        holder.deleteButton.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public User getUser(int position) {
        return users.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView email;
        TextView address;
        Button deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textViewUsername);
            email = itemView.findViewById(R.id.textViewEmail);
            address = itemView.findViewById(R.id.textViewAddress);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
