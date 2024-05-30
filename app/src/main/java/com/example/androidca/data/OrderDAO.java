package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidca.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private DatabaseHelper dbHelper;

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void addOrder(int userId, String orderDate, String orderDetails, double totalAmount, String deliveryAddress) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID_FK, userId);
        values.put(DatabaseHelper.COLUMN_ORDER_DATE, orderDate);
        values.put(DatabaseHelper.COLUMN_ORDER_DETAILS, orderDetails);
        values.put(DatabaseHelper.COLUMN_TOTAL_AMOUNT, totalAmount);
        values.put(DatabaseHelper.COLUMN_DELIVERY_ADDRESS, deliveryAddress);
        db.insert(DatabaseHelper.TABLE_ORDERS, null, values);
        db.close();
    }

    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.COLUMN_ORDER_ID,
                DatabaseHelper.COLUMN_USER_ID_FK,
                DatabaseHelper.COLUMN_ORDER_DATE,
                DatabaseHelper.COLUMN_ORDER_DETAILS,
                DatabaseHelper.COLUMN_TOTAL_AMOUNT,
                DatabaseHelper.COLUMN_DELIVERY_ADDRESS
        };
        String selection = DatabaseHelper.COLUMN_USER_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_ORDERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID)));
                order.setUserId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_ID_FK)));
                order.setOrderDate(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DATE)));
                order.setOrderDetails(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAILS)));
                order.setTotalAmount(cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.COLUMN_TOTAL_AMOUNT)));
                order.setDeliveryAddress(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DELIVERY_ADDRESS)));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }
}
