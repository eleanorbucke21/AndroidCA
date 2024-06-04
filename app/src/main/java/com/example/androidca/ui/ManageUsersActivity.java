package com.example.androidca.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidca.R;
import com.example.androidca.data.DatabaseManager;
import com.example.androidca.models.User;
import com.example.androidca.adapters.UserAdapter;
import android.content.SharedPreferences;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter adapter;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        databaseManager = new DatabaseManager(this);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(databaseManager.getAllUsers(), position -> {
            if (isAdmin()) {
                databaseManager.deleteUser(adapter.getUser(position).getId());
                adapter.removeItem(position);
                recyclerViewUsers.getAdapter().notifyItemRemoved(position); // More efficient update
                Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewUsers.setAdapter(adapter);
    }

    private boolean isAdmin() {
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        return "admin".equals(prefs.getString("UserRole", "user"));
    }

    private void refreshUserList() {
        List<User> users = databaseManager.getAllUsers();
        adapter.setUsers(users);
        adapter.notifyDataSetChanged();
    }
}
