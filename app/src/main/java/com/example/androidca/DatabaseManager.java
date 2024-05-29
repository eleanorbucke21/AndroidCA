package com.example.androidca;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // User operations
    public long addUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_NAME, username);
        values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);
        return database.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    public Cursor getUser(String username) {
        String[] columns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_PASSWORD };
        String selection = DatabaseHelper.COLUMN_USER_NAME + " = ?";
        String[] selectionArgs = { username };
        return database.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);
    }

    // Order operations
    public long addOrder(String orderDetails, String orderDate) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ORDER_DETAILS, orderDetails);
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, orderDate);
        return database.insert(DatabaseHelper.TABLE_ORDERS, null, values);
    }

    public Cursor getAllOrders() {
        String[] columns = { DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_ORDER_DETAILS, DatabaseHelper.COLUMN_ORDER_DATE };
        return database.query(DatabaseHelper.TABLE_ORDERS, columns, null, null, null, null, DatabaseHelper.COLUMN_ORDER_DATE + " DESC");
    }
}
