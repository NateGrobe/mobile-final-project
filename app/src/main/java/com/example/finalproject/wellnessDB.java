package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Range;

import androidx.annotation.Nullable;

public class wellnessDB extends SQLiteOpenHelper {

    //declare the class's instance variables
    private static final String DATABASE_NAME = "wellness.db";
    private static final int DATABASE_VERSION = 6;
    private static final String TABLE_BREAKFAST = "breakfast";
    private static final String TABLE_LUNCH = "lunch";
    private static final String TABLE_DINNER = "dinner";
    private static final String TABLE_SNACK = "snack";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "foodName";
    private static final String COL_CALORIES = "foodCalories";


    MealSelectionActivity ms = new MealSelectionActivity();


    public wellnessDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableBreakfast = "CREATE TABLE " + TABLE_BREAKFAST + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_CALORIES + " number DEFAULT 0)" +";" ;
        db.execSQL(createTableBreakfast);

        String createTableLunch = "CREATE TABLE " + TABLE_LUNCH + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_CALORIES + " number DEFAULT 0)" +";" ;
        db.execSQL(createTableLunch);

        String createTableDinner = "CREATE TABLE " + TABLE_DINNER + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_CALORIES + " number DEFAULT 0)" +";" ;
        db.execSQL(createTableDinner);

        String createTableSnacks = "CREATE TABLE " + TABLE_SNACK + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_NAME + " Text NOT NULL,"+
                COL_CALORIES + " number DEFAULT 0)" +";" ;
        db.execSQL(createTableSnacks);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table " + TABLE_BREAKFAST + ";");
        db.execSQL("Drop table " + TABLE_LUNCH + ";");
        db.execSQL("Drop table " + TABLE_DINNER + ";");
        db.execSQL("Drop table " + TABLE_SNACK + ";");

        this.onCreate(db);
    }

    //add record method
    public void addRecord(Foods food, int mealType) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, food.getFoodName());
        values.put(COL_CALORIES, food.getFoodCalories());


        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db = this.getWritableDatabase();

            switch (mealType) {
                case 1:
                    db.insert(TABLE_BREAKFAST, null, values);
                    break;
                case 2:
                    db.insert(TABLE_LUNCH, null, values);
                    break;
                case 3:
                    db.insert(TABLE_DINNER, null, values);
                    break;
                case 4:
                    db.insert(TABLE_SNACK, null, values);
                    break;
            }

        } finally {
            db.close();
        }

    }

    //delete record method
    public void deleteRecord(String name, int mealType) {
        SQLiteDatabase db = this.getWritableDatabase();

        switch (mealType) {
            case 1:
                db.execSQL("Delete from " + TABLE_BREAKFAST + " where " + COL_NAME + "='" + name + "';");
                break;
            case 2:
                db.execSQL("Delete from " + TABLE_LUNCH + " where " + COL_NAME + "='" + name + "';");
                break;
            case 3:
                db.execSQL("Delete from " + TABLE_DINNER + " where " + COL_NAME + "='" + name + "';");;
                break;
            case 4:
                db.execSQL("Delete from " + TABLE_SNACK + " where " + COL_NAME + "='" + name + "';");
                break;
        }

        db.close();
    }

    @SuppressLint("Range")
    public int sumCalories(int mealType){
        int sum = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cCalories;

        switch (mealType) {
            case 1:
                cCalories = db.query(TABLE_BREAKFAST,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                break;

            case 2:
                cCalories = db.query(TABLE_LUNCH,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                break;

            case 3:
                cCalories = db.query(TABLE_DINNER,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                break;

            default:
                cCalories = db.query(TABLE_SNACK,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                break;
        }

        while(!cCalories.isAfterLast()){
            Log.d("calorie", "value:" + cCalories.getInt(cCalories.getColumnIndex(COL_CALORIES)));
            sum = sum + cCalories.getInt(cCalories.getColumnIndex(COL_CALORIES));
            cCalories.moveToNext();

        }

        db.close();
        return sum;
    }

    //select all records method
    @SuppressLint("Range")
    public String databaseToString(int mealType) {

        String result= "";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        Cursor cCalories;


        switch (mealType) {
            case 1:
                c = db.query(TABLE_BREAKFAST,new String[]{COL_NAME},null,null,null,null,null);
                cCalories = db.query(TABLE_BREAKFAST,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                c.moveToFirst();
                break;
            case 2:
                c = db.query(TABLE_LUNCH,new String[]{COL_NAME},null,null,null,null,null);
                cCalories = db.query(TABLE_LUNCH,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                c.moveToFirst();
                break;
            case 3:
                c = db.query(TABLE_DINNER,new String[]{COL_NAME},null,null,null,null,null);
                cCalories = db.query(TABLE_DINNER,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                c.moveToFirst();
                break;
            default:
                c = db.query(TABLE_SNACK,new String[]{COL_NAME},null,null,null,null,null);
                cCalories = db.query(TABLE_SNACK,new String[]{COL_CALORIES},null,null,null,null,null);
                cCalories.moveToFirst();
                c.moveToFirst();
                break;
        }

        while(!c.isAfterLast()){
            result += c.getString(c.getColumnIndex(COL_NAME));
            result += "       Calories: " + cCalories.getInt(cCalories.getColumnIndex(COL_CALORIES));
            result +="\n";
            Log.d("select","value : " + result);
            ms.countCalories(cCalories.getInt(cCalories.getColumnIndex(COL_CALORIES)));
            c.moveToNext();
            cCalories.moveToNext();
        }
        db.close();
        return result;
    }
}
