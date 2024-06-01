package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.androidca.models.User;

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
        db.insert(DatabaseHelper.TABLE_USERS, null, values);
        db.close();
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_PASSWORD};
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);
            if (passwordIndex != -1) {
                String dbPassword = cursor.getString(passwordIndex);
                cursor.close();
                db.close();
                return dbPassword.equals(password);
            } else {
                Log.e("UserDAO", "Invalid password column index detected in loginUser method");
            }
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
                return "admin".equalsIgnoreCase(role);
            } else {
                Log.e("UserDAO", "Invalid role column index detected in isAdminUser method");
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
        String[] columns = {
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_USERNAME,
                DatabaseHelper.COLUMN_EMAIL,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_ADDRESS_LINE_1,
                DatabaseHelper.COLUMN_ADDRESS_LINE_2,
                DatabaseHelper.COLUMN_ADDRESS_LINE_3,
                DatabaseHelper.COLUMN_POSTCODE,
                DatabaseHelper.COLUMN_COUNTRY
        };
        String selection = DatabaseHelper.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int addressLine1Index = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS_LINE_1);
            int addressLine2Index = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS_LINE_2);
            int addressLine3Index = cursor.getColumnIndex(DatabaseHelper.COLUMN_ADDRESS_LINE_3);
            int postcodeIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_POSTCODE);
            int countryIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_COUNTRY);

            if (idIndex >= 0 && usernameIndex >= 0 && emailIndex >= 0 && nameIndex >= 0 && addressLine1Index >= 0 && addressLine2Index >= 0 && addressLine3Index >= 0 && postcodeIndex >= 0 && countryIndex >= 0) {
                int id = cursor.getInt(idIndex);
                String usernameValue = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                String name = cursor.getString(nameIndex);
                String addressLine1 = cursor.getString(addressLine1Index);
                String addressLine2 = cursor.getString(addressLine2Index);
                String addressLine3 = cursor.getString(addressLine3Index);
                String postcode = cursor.getString(postcodeIndex);
                String country = cursor.getString(countryIndex);
                user = new User(id, usernameValue, email, name, addressLine1, addressLine2, addressLine3, postcode, country);
            } else {
                Log.e("UserDAO", "Invalid column index detected in getUserInfo method");
            }

            cursor.close();
        }
        db.close();
        return user;
    }

    public boolean updateUserInfo(String username, String name, String addressLine1, String addressLine2, String addressLine3, String postcode, String country) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_ADDRESS_LINE_1, addressLine1);
        values.put(DatabaseHelper.COLUMN_ADDRESS_LINE_2, addressLine2);
        values.put(DatabaseHelper.COLUMN_ADDRESS_LINE_3, addressLine3);
        values.put(DatabaseHelper.COLUMN_POSTCODE, postcode);
        values.put(DatabaseHelper.COLUMN_COUNTRY, country);
        int rows = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
        return rows > 0;
    }
}
