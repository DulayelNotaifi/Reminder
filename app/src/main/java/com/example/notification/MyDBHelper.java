package com.example.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "CalendarApp.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_Calendar";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Event_name";
    private static final String COLUMN_Type = "Event_type";
    private static final String COLUMN_DATE = "Event_Date";
    private static final String COLUMN_TIME = "Event_TIME";
    private static final String COLUMN_PRIORITY = "Event_priority";
    private static final String COLUMN_NOTES = "Event_notes";


    public MyDBHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_Type + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_PRIORITY + " TEXT, " +
                COLUMN_NOTES + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEvent(String name,String type,String date, String time, String priority, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_Type,type);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_PRIORITY,priority);
        cv.put(COLUMN_NOTES,notes);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed to add the event", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Event Added Successfully!", Toast.LENGTH_SHORT).show();
        }

    }
}
