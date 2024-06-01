package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androidca.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void registerUser(String username, String password, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, username);
        values.put(DatabaseHelper.COLUMN_PASSWORD, password);
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        long result = db.insert(DatabaseHelper.TABLE_USERS, null, values);
        if (result == -1) {
            Log.e("UserDAO", "Failed to insert user");
        } else {
            Log.d("UserDAO", "User inserted with row id: " + result);
        }
        db.close();
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_PASSWORD}; // Only need the password column
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            if (passwordIndex != -1) {
                String dbPassword = cursor.getString(passwordIndex);
                Log.d("UserDAO", "DB Password: " + dbPassword + " | Entered Password: " + password);
                cursor.close();
                db.close();
                return dbPassword.equals(password);
            } else {
                // Log an error if the password column index is invalid
                Log.e("UserDAO", "Invalid password column index detected in loginUser method");
            }
        } else {
            Log.e("UserDAO", "User not found or cursor is null");
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return false;
    }

    public boolean isAdminUser(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_ROLE};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ROLE);
            if (roleIndex != -1) {
                String role = cursor.getString(roleIndex);
                cursor.close();
                db.close();
                return "admin".equals(role);
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return false;
    }

    public User getUserInfo(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_USERNAME, DatabaseHelper.COLUMN_EMAIL, DatabaseHelper.COLUMN_ADDRESS};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
            int addressIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS);

            if (idIndex >= 0 && usernameIndex >= 0 && emailIndex >= 0 && addressIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String usernameValue = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                String address = cursor.getString(addressIndex);
                user = new User(id, usernameValue, email, address);
            } else {
                Log.e("UserDAO", "Invalid column index detected in getUserInfo method");
            }

            cursor.close();
        }
        db.close();
        return user;
    }

    public boolean updateUserInfo(String username, String name, String address) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, name);  // Update the user's name
        values.put(DatabaseHelper.COLUMN_ADDRESS, address);
        int rows = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
        return rows > 0;
    }
}
