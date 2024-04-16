package com.example.finalprojectecommerceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ECommerceDB";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "users";
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String EMAIL_COL = "email";
    public static final String PASSWORD_COL = "password";
    public static final String PHONE_COL = "phone";

    private Context context; // Add a Context field

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COL + " TEXT, " +
                EMAIL_COL + " TEXT UNIQUE, " + // Ensure email is unique
                PASSWORD_COL + " TEXT, " +
                PHONE_COL + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(String name, String email, String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(PASSWORD_COL, password);
        values.put(PHONE_COL, phone);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ?", new String[]{email});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + EMAIL_COL + " = ? AND " + PASSWORD_COL + " = ?", new String[]{email, password});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
