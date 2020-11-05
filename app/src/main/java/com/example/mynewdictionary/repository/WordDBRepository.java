package com.example.mynewdictionary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mynewdictionary.database.DBHelper;
import com.example.mynewdictionary.database.DBSchema;
import com.example.mynewdictionary.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordDBRepository implements IRepository {

    private static WordDBRepository sInstance;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private WordDBRepository(Context context) {

        mContext = context.getApplicationContext();
        DBHelper dbHelper = new DBHelper(context);
        mDatabase = dbHelper.getWritableDatabase();
        if (getWords().size() == 0) {

            Word word1 = new Word("dog", "سگ");
            Word word2 = new Word("cat", "گربه");
            Word word3 = new Word("pig", "خوک");
            Word word4 = new Word("hen", "مرغ");
            Word word5 = new Word("ship", "گوسفند");
            insertWord(word1);
            insertWord(word2);
            insertWord(word3);
            insertWord(word4);
            insertWord(word5);

        }
    }


    public static WordDBRepository getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new WordDBRepository(context);
        }
        return sInstance;

    }

    @Override
    public List<Word> getWords() {

        List<Word> words = new ArrayList<>();

        WordCursorWrapper wordCursorWrapper = queryWordCursor(null, null);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return words;
        try {
            wordCursorWrapper.moveToFirst();

            while (!wordCursorWrapper.isAfterLast()) {
                words.add(wordCursorWrapper.getWord());
                wordCursorWrapper.moveToNext();
            }
        } finally {
            wordCursorWrapper.close();
        }
        return words;
    }

    @Override
    public Word getWord(UUID wordId) {
        String where = DBSchema.WordTable.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{wordId.toString()};

        WordCursorWrapper wordCursorWrapper = queryWordCursor(where, whereArgs);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return null;
        try {
            wordCursorWrapper.moveToFirst();
            return wordCursorWrapper.getWord();
        } finally {
            wordCursorWrapper.close();
        }
    }

    private WordCursorWrapper queryWordCursor(String where, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                DBSchema.WordTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);

        WordCursorWrapper wordCursorWrapper = new WordCursorWrapper(cursor);
        return wordCursorWrapper;
    }

    @Override
    public void insertWord(Word word) {
        ContentValues values = getContentValues(word);
        mDatabase.insert(DBSchema.WordTable.NAME, null, values);
    }

    @Override
    public void updateWord(Word word) {
        ContentValues values = getContentValues(word);
        String whereClause = DBSchema.WordTable.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getId().toString()};
        mDatabase.update(DBSchema.WordTable.NAME, values, whereClause, whereArgs);
    }

    @Override
    public void deleteWord(Word word) {
        String whereClause = DBSchema.WordTable.Cols.UUID + " = ?";
        String[] whereArgs = new String[]{word.getId().toString()};
        mDatabase.delete(DBSchema.WordTable.NAME, whereClause, whereArgs);
    }

    private ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(DBSchema.WordTable.Cols.UUID, word.getId().toString());
        values.put(DBSchema.WordTable.Cols.NAME, word.getName());
        values.put(DBSchema.WordTable.Cols.MEAN, word.getMean());
        return values;
    }

    @Override
    public List<Word> searchResultEngToPer(String query) {
        List<Word> words = new ArrayList<>();
        String where = DBSchema.WordTable.Cols.NAME + " LIKE '%" + query + "%'";

        WordCursorWrapper wordCursorWrapper = queryWordCursor(where, null);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return words;
        try {
            wordCursorWrapper.moveToFirst();

            while (!wordCursorWrapper.isAfterLast()) {
                words.add(wordCursorWrapper.getWord());
                wordCursorWrapper.moveToNext();
            }
        } finally {
            wordCursorWrapper.close();
        }
        return words;
    }

    @Override
    public List<Word> searchResultPerToEng(String query) {
        List<Word> words = new ArrayList<>();
        String where = DBSchema.WordTable.Cols.MEAN + " LIKE '%" + query + "%'";

        WordCursorWrapper wordCursorWrapper = queryWordCursor(where, null);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return words;
        try {
            wordCursorWrapper.moveToFirst();

            while (!wordCursorWrapper.isAfterLast()) {
                words.add(wordCursorWrapper.getWord());
                wordCursorWrapper.moveToNext();
            }
        } finally {
            wordCursorWrapper.close();
        }
        return words;
    }
}
