package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class WaterDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wellness.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "water";
    private static final String COL_ID ="id" ;
    private static final String COL_CONSUMPTION = "Consumption";
    private static final String COL_UNIT = "Unit";
    private static final String COL_TIME = "RecordDate";

    public WaterDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " Integer PRIMARY KEY AUTOINCREMENT," +
                COL_CONSUMPTION + " Integer NOT NULL,"+
                COL_UNIT+ " TEXT NOT NULL,"+
                COL_TIME + " DATE DEFAULT CURRENT_DATE)" +";" ;
        System.out.println(createTable);
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newV) {
        db.execSQL("Drop table " + TABLE_NAME+ ";");
        this.onCreate(db);
    }

    public void addRecord(int volume, String unit){
        ContentValues values = new ContentValues();
        values.put(COL_CONSUMPTION, volume);
        values.put(COL_UNIT, unit);

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Insert the new entry into the DB.
            db.insert(TABLE_NAME, null, values);
        } finally {
            assert db != null;
            db.close();
        }

    }
    public void updateRecord(int volume, String unit){
        ContentValues values = new ContentValues();
        values.put(COL_CONSUMPTION, volume);
        values.put(COL_UNIT, unit);

        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            // Insert the new entry into the DB.
            db.update(TABLE_NAME, values, COL_TIME + "= CURRENT_DATE", null);

        } finally {
            assert db != null;
            db.close();
        }

    }

    public int retrieveVolume(){
        SQLiteDatabase db = this.getReadableDatabase();
        int volume = 0;
        Cursor c;
        c = db.rawQuery("Select "+ COL_CONSUMPTION + " FROM " + TABLE_NAME + " where " + COL_TIME + "== CURRENT_DATE;", null);
        if(c.getCount()> 0){
            c.moveToFirst();
            int column = c.getColumnIndex(COL_CONSUMPTION);
            if(column >= 0) {
                volume += c.getInt(column);
            }
        }else{
            System.out.println("No columns");
        }




        c.close();
        db.close();
        System.out.println(volume);
        return volume;
    }
    public void  deleteRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Delete from " + TABLE_NAME + " where " + COL_TIME + "!= CURRENT_DATE;", null);
        cursor.close();
        db.close();
    }
}

