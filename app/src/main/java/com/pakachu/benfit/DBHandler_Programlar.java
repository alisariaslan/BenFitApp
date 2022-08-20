package com.pakachu.benfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHandler_Programlar extends SQLiteOpenHelper {


    private static final String TABLE_NAME = "programlarim";
    private static final String COL1 = "id";
    private static final String COL2 = "ad";

    public DBHandler_Programlar(Context context) {
        super(context, TABLE_NAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT)";
        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);

        long result = db.insert(TABLE_NAME, null, contentValues);
        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void addData(String table,String gun,String hareket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(gun, hareket);
       db.insert(table, null, contentValues);

    }

    public Cursor getData(String tabloadi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tabloadi;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getColumnData(String tabloadi,String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT "+column+" FROM " + tabloadi;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public ArrayList<String> getTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> tables=new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        while(cursor.moveToNext()){
            String tableName = cursor.getString(0);
            if(tableName.equals("android_metadata") || tableName.equals("sqlite_sequence") || tableName.equals("programlarim")){
                continue;
            }else{
                tables.add(tableName);
            }
        }
        return tables;
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }



}