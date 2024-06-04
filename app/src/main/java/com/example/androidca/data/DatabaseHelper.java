package com.example.androidca.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handling database schema upgrades
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_NAME + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_1 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_2 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS_LINE_3 + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_POSTCODE + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_COUNTRY + " TEXT");
        }
    }

    // Simplified example of hashing a password before storing it in the database
    public String hashPassword(String password) {
        return String.valueOf(password.hashCode());
    }
}
