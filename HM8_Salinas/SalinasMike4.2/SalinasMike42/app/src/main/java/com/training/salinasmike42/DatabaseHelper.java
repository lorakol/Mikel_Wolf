package com.training.salinasmike42;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myapp.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_AGE = "age";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public long insertItem(String firstName, String lastName, int age) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_AGE, age);
        return db.insert(TABLE_NAME, null, values);
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + "=? ";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, whereClause, whereArgs);
    }



    @SuppressLint("Range")
    public ArrayList<User> getAllUser(){
        ArrayList<User> records = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String first_name = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                String last_name = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                int age = cursor.getInt(cursor.getColumnIndex(COLUMN_AGE));
                records.add(new User(id, first_name, last_name, age));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return records;

    }
}

