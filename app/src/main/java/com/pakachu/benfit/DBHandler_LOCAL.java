package com.pakachu.benfit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler_LOCAL extends SQLiteOpenHelper {

    private static final String FIRST_TABLE = "hareketler";
    private static final String FIRST_COL1 = "id";
    private static final String FIRST_COL2 = "hareket";

    private static final String SECOND_TABLE = "detaylar";
    private static final String SECOND_COL1 = "id";
    private static final String SECOND_COL2 = "detay";

    public DBHandler_LOCAL(Context context) {
        super(context, FIRST_TABLE, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + FIRST_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIRST_COL2 + " TEXT)";
        String createTable2 = "CREATE TABLE " + SECOND_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SECOND_COL2 + " TEXT)";
        db.execSQL(createTable1);
        db.execSQL(createTable2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FIRST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SECOND_TABLE);
        onCreate(db);
    }

    public void addDefaultData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_COL2,"ısınma");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"kardiyo");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"off");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"bench press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"incline dumbell press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"chest press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"cable crossover");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"dumbell fly");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"machine fly");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"bicep curl");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"hammer curl");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"barfiks");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"machine row");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"dumbell row");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"lat pull");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"seated cable row");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"hyperextension");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"dumbell skullcrusher");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"close grip bench press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"squat");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"deadlift");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"lunge");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"leg press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"leg extension");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"leg curl");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"calf raise");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"dips");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"dumbell shoulder press");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"lateral raise");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"front raise");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"rear delt fly");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"facepull");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"preacher curl");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"cable curl");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"rope pushdown");
        db.insert(FIRST_TABLE, null, contentValues);
        contentValues.put(FIRST_COL2,"cable pushdown");
        db.insert(FIRST_TABLE, null, contentValues);
    }
    public void addDataTo_FirstTable(String hareket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIRST_COL2, hareket);
        db.insert(FIRST_TABLE, null, contentValues);
    }

    public void addDataTo_SecondTable(String detay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECOND_COL2, detay);
        db.insert(SECOND_TABLE, null, contentValues);
    }

    public Cursor getDataFrom(String tabloadi) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tabloadi;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void executeSQL(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }

}