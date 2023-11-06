package com.training.projectb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ArticleDBHelper extends SQLiteOpenHelper {
    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";

    // Table and column names
    private static final String TABLE_ARTICLES = "articles";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";

    public ArticleDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CONTENT + " TEXT" + ")";
        db.execSQL(CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }

    public void addArticle( String content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_CONTENT, content);

        db.insert(TABLE_ARTICLES, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public List<String> getArticles(){
        List<String> records  = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ARTICLES;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                records.add(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        return records;
    }
}
