package com.example.androidca.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidca.R;

import android.content.SharedPreferences;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.androidca.models.User;
import java.util.List;
import com.example.androidca.data.DatabaseHelper;

public class ManageUsersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        databaseHelper = new DatabaseHelper(this);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(databaseHelper.getAllUsers(), position -> {
            if (isAdmin()) {
                databaseHelper.deleteUser(adapter.getUser(position).getId());
                adapter.removeItem(position);
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
        List<User> users = databaseHelper.getAllUsers();
        adapter.setUsers(users);
        adapter.notifyDataSetChanged();
    }
}