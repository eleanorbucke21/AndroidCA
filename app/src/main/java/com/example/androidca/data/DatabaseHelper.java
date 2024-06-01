package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.androidca.models.User;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "ecommerce.db";
    private static final int DATABASE_VERSION = 1;  // Ensure this is set to 1 for the initial version

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ORDERS = "orders";

    // User table columns
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_ROLE = "role";  // Adding a column for the user role

    // Order table columns
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_USER_ID_FK = "user_id_fk";
    public static final String COLUMN_ORDER_DATE = "order_date";
    public static final String COLUMN_ORDER_DETAILS = "order_details";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static final String COLUMN_DELIVERY_ADDRESS = "delivery_address";

    // SQL to create user table with role
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_ADDRESS + " TEXT, " +
            COLUMN_ROLE + " TEXT DEFAULT 'user')";

    // SQL to create orders table
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + " (" +
            COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USER_ID_FK + " INTEGER, " +
            COLUMN_ORDER_DATE + " TEXT, " +
            COLUMN_ORDER_DETAILS + " TEXT, " +
            COLUMN_TOTAL_AMOUNT + " REAL, " +
            COLUMN_DELIVERY_ADDRESS + " TEXT, " +
            "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ORDERS);
        addAdminUser(db);  // Add an admin user after creating the tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, we simply drop the tables and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    // Helper method to add an admin user
    private void addAdminUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "Limerick");  // In a real app, this should be hashed
        values.put(COLUMN_EMAIL, "admin@example.com");
        values.put(COLUMN_ADDRESS, "Admin HQ");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_USERS, null, values);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_ADDRESS};  // Ensure columns match your table structure
        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int addressIndex = cursor.getColumnIndex(COLUMN_ADDRESS);

            if (idIndex != -1 && usernameIndex != -1 && emailIndex != -1 && addressIndex != -1) {
                do {
                    User user = new User(
                            cursor.getInt(idIndex),
                            cursor.getString(usernameIndex),
                            cursor.getString(emailIndex),
                            cursor.getString(addressIndex)
                    );
                    users.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return users;
    }


    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?", new String[] { String.valueOf(userId) });
        db.close();
    }

}
