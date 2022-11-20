package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {
    // DB configs
    private static final String DATABASE_NAME = "wellness.db";
    private static final String TABLE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    // columns
    private static final String COL_ID = "id";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASS = "password";
    private static final String COL_REMEMBER_USER = "remember_user";
    private static final String COL_ACTIVE_USER = "active_user";

    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " TEXT NOT NULL," +
                COL_PASS + " TEXT NOT NULL," +
                COL_REMEMBER_USER + " INTEGER DEFAULT 0," +
                COL_ACTIVE_USER + " INTEGER DEFAULT 0);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_NAME + ";");
        this.onCreate(db);
    }

    public boolean addUser(String username, String password, boolean rememberMe) {
        int remInt = rememberMe ? 1 : 0;
        int activeUser = 1;

        // check if username already exists and return false if it does
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor = db_read.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username='" + username + "';", null);

        if (cursor.moveToFirst()) {
            System.out.println("here");
            String fetched_username = cursor.getString(1);
            if (fetched_username.equals(username)) {
                db_read.close();
                cursor.close();
                return false;
            }
        }

        // add new user
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASS, password);
        values.put(COL_REMEMBER_USER, remInt);
        values.put(COL_ACTIVE_USER, activeUser);

        try (SQLiteDatabase db_write= this.getWritableDatabase()) {
            db_write.insert(TABLE_NAME, null, values);
        }

        return true;
    }

    // return false if password doesn't match username
    public boolean signInUser(String username, String password, boolean rememberMe) {
        int remInt = rememberMe ? 1 : 0;

        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor = db_read.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username='" + username + "';", null);

        if (cursor.moveToFirst()) {
            int user_id = cursor.getInt(0);
            String fetched_pass = cursor.getString(2);

            // check if user pass matches input pass
            if (fetched_pass.equals(password)) {
                SQLiteDatabase db_write = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COL_REMEMBER_USER, remInt);
                values.put(COL_ACTIVE_USER, 1);
                db_write.update(TABLE_NAME, values, "id = ?", new String[]{Integer.toString(user_id)});
                db_read.close();
                db_write.close();
                cursor.close();
                return true;
            }
        }

        db_read.close();
        return false;
    }

    public boolean rememberSignIn() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor = db_read.rawQuery("select * from " + TABLE_NAME + " where " + COL_REMEMBER_USER + "=1;", null);

        if (cursor.moveToFirst()) {
            db_read.close();
            return true;
        }

        cursor.close();
        return false;
    }

    public void signOut() {
        SQLiteDatabase db_read = this.getReadableDatabase();
        Cursor cursor = db_read.rawQuery("select * from " + TABLE_NAME + " where " + COL_ACTIVE_USER + "=1;", null);

        if (cursor.moveToFirst()) {
            int user_id = cursor.getInt(0);
            SQLiteDatabase db_write = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COL_REMEMBER_USER, 0);
            values.put(COL_ACTIVE_USER, 0);
            db_write.update(TABLE_NAME, values, "id = ?", new String[]{Integer.toString(user_id)});
        }
        cursor.close();
    }

}
