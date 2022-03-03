package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

// SQL : Sequential Query Language
public class TodoDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ToDoList";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "Todo";
    public static final String ID_COL = "_id";
    public static final String TEXT_COL = "text";
    public static final String DATE_COL = "date";
    public static final String DONE_COL = "done";

    public TodoDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TEXT_COL + " TEXT," +
                DATE_COL + " TEXT," +
                DONE_COL + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Update
    public void setDone(int id, boolean done) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DONE_COL, done);
        getWritableDatabase().update(TABLE_NAME, contentValues, "_id=?", new String[]{Integer.toString(id)});
    }

    // Create
    public int insertItem(String text, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEXT_COL, text);
        contentValues.put(DATE_COL, date);
        contentValues.put(DONE_COL, false);
        return (int) getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    // Read
    public ArrayList<TodoItem> getAllItems() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, new String[]{ID_COL, TEXT_COL, DATE_COL, DONE_COL},
                null,
                null,
                null, null, null);
        ArrayList<TodoItem> items = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                TodoItem item = new TodoItem(
                        cursor.getInt(cursor.getColumnIndex(ID_COL)),
                        cursor.getString(cursor.getColumnIndex(TEXT_COL)),
                        cursor.getString(cursor.getColumnIndex(DATE_COL)),
                        cursor.getInt(cursor.getColumnIndex(DONE_COL)) == 1
                );
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    // Delete
    public boolean deleteItem(int id) {
        return getWritableDatabase().delete(TABLE_NAME,
                "_id=?",
                new String[]{Integer.toString(id)}) > 0;
    }
}
