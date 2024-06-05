package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
                DatabaseHelper.COLUMN_ORDER_DATE,
                DatabaseHelper.COLUMN_ORDER_DETAILS  // Assuming this contains product name
        };
        String selection = DatabaseHelper.COLUMN_USER_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(DatabaseHelper.TABLE_ORDERS, columns, selection, selectionArgs, null, null, null);

        int dateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DATE);
        int detailsIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_DETAILS);

        if (dateIndex != -1 && detailsIndex != -1) {
            if (cursor.moveToFirst()) {
                do {
                    String orderDate = cursor.getString(dateIndex);
                    String productName = cursor.getString(detailsIndex);  // Assuming this is the product name
                    Order order = new Order();
                    order.setOrderDate(orderDate);
                    order.setOrderDetails(productName);  // Using orderDetails as product name
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } else {
            Log.e("OrderDAO", "Error: Column index not found.");
        }
        cursor.close();
        db.close();
        return orders;
    }
}
