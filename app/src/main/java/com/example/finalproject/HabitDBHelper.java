package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HabitDBHelper extends SQLiteOpenHelper {
    //declare the class's instance variables
    private static final String DATABASE_NAME = "habit.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "habits";
    private static final String COL_ID ="id" ;
    private static final String COL_NAME = "habitName";
    private static final String COL_TYPE = "habitType";

    public HabitDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_TYPE + " number DEFAULT 0)" +";" ;
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,  int oldVersion, int newVersion) {
        db.execSQL("Drop table " + TABLE_NAME+ ";");
        this.onCreate(db);
    }

    //add record method
    public void addRecord(HabitModel habitModel){
        ContentValues values = new ContentValues();
        values.put(COL_NAME,habitModel.getName());
        values.put(COL_TYPE,habitModel.getHabitType());


        //SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Insert the new entry into the DB.
            db.insert(TABLE_NAME,null,values);
        } finally {
            db.close();
        }

    }

    //delete record method
    public void  deleteRecord(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from " + TABLE_NAME + " where " + COL_NAME + "='"  + name +"';");

        db.close();
    }

    //select all records method
    @SuppressLint("Range")
    public  String databaseToString() {

        String result = "";

        SQLiteDatabase db = getReadableDatabase();
        String query = "Select " + COL_NAME + "  from " + TABLE_NAME + ";";
        Cursor c = db.query(TABLE_NAME, new String[]{COL_NAME}, null, null, null, null, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            result += c.getString(c.getColumnIndex(COL_NAME));
            result += "\n";
            Log.d("select", "value : " + result);
            c.moveToNext();
        }
        db.close();
        return result;
    }

    public List<HabitModel> getHabits() {
        List<HabitModel> returnList = new ArrayList<>();

        //get data from db

        String queryString = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            //loop
            do {
                int habitID = cursor.getInt(0);
                String habitName = cursor.getString(1);
                String habitType = cursor.getString(2);

                HabitModel newHabit = new HabitModel(habitID, habitName, habitType);
                returnList.add(newHabit);

            }while(cursor.moveToNext());

        }else {
            //Failure. do not add to list
        }

        cursor.close();
        db.close();
        return returnList;
    }

}
