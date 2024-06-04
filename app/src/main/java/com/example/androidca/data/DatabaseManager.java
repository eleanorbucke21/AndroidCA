package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.androidca.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS_LINE_1)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS_LINE_2)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS_LINE_3)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POSTCODE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COUNTRY))
                );
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_USERS, DatabaseHelper.COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }
    // Order operations
    public long addOrder(int userId, String orderDetails, String orderDate, double totalAmount, String deliveryAddress) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID_FK, userId);
        values.put(DatabaseHelper.COLUMN_ORDER_DETAILS, orderDetails);
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, orderDate);
        values.put(DatabaseHelper.COLUMN_TOTAL_AMOUNT, totalAmount);
        values.put(DatabaseHelper.COLUMN_DELIVERY_ADDRESS, deliveryAddress);
        return database.insert(DatabaseHelper.TABLE_ORDERS, null, values);
    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_ORDERS, new String[] { DatabaseHelper.COLUMN_ORDER_DETAILS }, null, null, null, null, DatabaseHelper.COLUMN_ORDER_DATE + " DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                orders.add(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ORDER_DETAILS)));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return orders;
    }

    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
