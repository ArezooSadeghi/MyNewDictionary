package com.example.mynewdictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {

        super(context, DBSchema.NAME, null, DBSchema.VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE " + DBSchema.WordTable.NAME + " (");
        query.append(DBSchema.WordTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(DBSchema.WordTable.Cols.UUID + " TEXT NOT NULL,");
        query.append(DBSchema.WordTable.Cols.NAME + " TEXT,");
        query.append(DBSchema.WordTable.Cols.MEAN + " TEXT");
        query.append(");");
        sqLiteDatabase.execSQL(query.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
