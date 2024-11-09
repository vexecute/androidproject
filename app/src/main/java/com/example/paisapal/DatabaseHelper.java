package com.example.paisapal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name
    private static String USERNAME = "";
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 3; // Increment version to trigger onUpgrade

    // Table Names
    private static final String TABLE_USERS = "users";
    private static final String TABLE_TRANSACTIONS = "transactions";

    // Columns for users table
    private static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "EMAIL";
    private static final String COL_4 = "PASSWORD";

    // Columns for transactions table
    private static final String COL_T_ID = "ID";
    private static final String COL_T_DESCRIPTION = "DESCRIPTION";
    private static final String COL_T_2 = "USERNAME";
    private static final String COL_T_CATEGORY = "CATEGORY";
    private static final String COL_T_TYPE = "TYPE";
    private static final String COL_T_AMOUNT = "AMOUNT";
    private static final String COL_T_DATE = "DATE"; // Column for date

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT)");

        // Create transactions table
        db.execSQL("CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                COL_T_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_T_2 + " TEXT, " +
                COL_T_DESCRIPTION + " TEXT, " +
                COL_T_CATEGORY + " TEXT, " +
                COL_T_TYPE + " TEXT, " +
                COL_T_AMOUNT + " REAL, " +
                COL_T_DATE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables and create new ones
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // Check if user exists by username and password
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COL_2 + " = ? AND " + COL_4 + " = ?", new String[]{username, password});

        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        if (userExists) {
            USERNAME = username;
        }
        return userExists;
    }

    // Insert a new user
    public boolean insertUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_2, username);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1; // Return true if insert was successful
    }

    // Method to get email for a given username
    public String getEmailForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COL_3 + " FROM " + TABLE_USERS + " WHERE " + COL_2 + " = ?", new String[]{username});

        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_3));
            cursor.close();
            return email;
        } else {
            cursor.close();
            return null; // Return null if the email is not found
        }
    }

    // Method to get all transactions
    public List<Transaction> getAllTransactions(String userName) {
        List<Transaction> transactionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + COL_T_2 + " = ?", new String[]{userName});

        if (cursor.moveToFirst()) {
            do {
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COL_T_DESCRIPTION));
                String category = cursor.getString(cursor.getColumnIndexOrThrow(COL_T_CATEGORY));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COL_T_TYPE));
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_T_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_T_DATE));

                // Create a new Transaction object and add it to the list
                Transaction transaction = new Transaction(description, category, type, amount, date);
                transactionList.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return transactionList; // Return the list of transactions
    }

    // Method to add a transaction
    public boolean addTransaction(String description, String category, String type, double amount, String date, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_T_2, userName);
        contentValues.put(COL_T_DESCRIPTION, description);
        contentValues.put(COL_T_CATEGORY, category);
        contentValues.put(COL_T_TYPE, type);
        contentValues.put(COL_T_AMOUNT, amount);
        contentValues.put(COL_T_DATE, date); // Store the date

        long result = db.insert(TABLE_TRANSACTIONS, null, contentValues);
        return result != -1; // Return true if insert was successful
    }

    public String getUserName() {
        return USERNAME;
    }

    // Get total amount spent by user
    public double getTotalAmountSpent(String userName) {
        double totalSpent = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_T_AMOUNT + ") FROM " + TABLE_TRANSACTIONS +
                " WHERE " + COL_T_TYPE + " = ? AND " + COL_T_2 + " = ?", new String[]{"Expense", userName});

        if (cursor.moveToFirst()) {
            totalSpent = cursor.getDouble(0); // Get the sum from the first column
        }
        cursor.close();
        return totalSpent;
    }

    // Get total income by user
    public double getTotalIncome(String userName) {
        double totalIncome = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_T_AMOUNT + ") FROM " + TABLE_TRANSACTIONS +
                " WHERE " + COL_T_TYPE + " = ? AND " + COL_T_2 + " = ?", new String[]{"Income", userName});

        if (cursor.moveToFirst()) {
            totalIncome = cursor.getDouble(0); // Get the sum from the first column
        }
        cursor.close();
        return totalIncome;
    }
}
