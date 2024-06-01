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
    private static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ORDERS = "orders";

    // User table columns
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS_LINE_1 = "address_line_1";
    public static final String COLUMN_ADDRESS_LINE_2 = "address_line_2";
    public static final String COLUMN_ADDRESS_LINE_3 = "address_line_3";
    public static final String COLUMN_POSTCODE = "postcode";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_ROLE = "role";

    // Order table columns
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_USER_ID_FK = "user_id_fk";
    public static final String COLUMN_ORDER_DATE = "order_date";
    public static final String COLUMN_ORDER_DETAILS = "order_details";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static final String COLUMN_DELIVERY_ADDRESS = "delivery_address";

    // SQL to create user table
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_ADDRESS_LINE_1 + " TEXT, " +
            COLUMN_ADDRESS_LINE_2 + " TEXT, " +
            COLUMN_ADDRESS_LINE_3 + " TEXT, " +
            COLUMN_POSTCODE + " TEXT, " +
            COLUMN_COUNTRY + " TEXT, " +
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
        addAdminUser(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_NAME + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_1 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_2 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_3 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_POSTCODE + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_COUNTRY + " TEXT");
        }
    }

    private void addAdminUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "Limerick");
        values.put(COLUMN_EMAIL, "admin@example.com");
        values.put(COLUMN_NAME, "Admin");
        values.put(COLUMN_ADDRESS_LINE_1, "Admin Address Line 1");
        values.put(COLUMN_ADDRESS_LINE_2, "Admin Address Line 2");
        values.put(COLUMN_ADDRESS_LINE_3, "Admin Address Line 3");
        values.put(COLUMN_POSTCODE, "000000");
        values.put(COLUMN_COUNTRY, "Admin Country");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_USERS, null, values);
    }

    // Add the getAllUsers method
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_NAME,
                COLUMN_ADDRESS_LINE_1,
                COLUMN_ADDRESS_LINE_2,
                COLUMN_ADDRESS_LINE_3,
                COLUMN_POSTCODE,
                COLUMN_COUNTRY
        };
        Cursor cursor = db.query(TABLE_USERS, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
            int addressLine1Index = cursor.getColumnIndex(COLUMN_ADDRESS_LINE_1);
            int addressLine2Index = cursor.getColumnIndex(COLUMN_ADDRESS_LINE_2);
            int addressLine3Index = cursor.getColumnIndex(COLUMN_ADDRESS_LINE_3);
            int postcodeIndex = cursor.getColumnIndex(COLUMN_POSTCODE);
            int countryIndex = cursor.getColumnIndex(COLUMN_COUNTRY);

            if (idIndex != -1 && usernameIndex != -1 && emailIndex != -1 && nameIndex != -1 && addressLine1Index != -1 && addressLine2Index != -1 && addressLine3Index != -1 && postcodeIndex != -1 && countryIndex != -1) {
                do {
                    User user = new User(
                            cursor.getInt(idIndex),
                            cursor.getString(usernameIndex),
                            cursor.getString(emailIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(addressLine1Index),
                            cursor.getString(addressLine2Index),
                            cursor.getString(addressLine3Index),
                            cursor.getString(postcodeIndex),
                            cursor.getString(countryIndex)
                    );
                    users.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return users;
    }

    // Add the deleteUser method
    public void deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
