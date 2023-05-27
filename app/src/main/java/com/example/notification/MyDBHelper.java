package com.example.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public List<Event> getClosestEvents() {
        List<Event> events = new ArrayList<>();

        // Define the SQL query to retrieve the closest 5 events based on the date
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " >= date('now') ORDER BY " + COLUMN_DATE + " ASC LIMIT 5";

        // Open a connection to the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Execute the query and retrieve the resulting cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Iterate over the cursor and retrieve the data for each row

        if (cursor.moveToFirst()) {
            do {
                // Retrieve the data for each column in the current row
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                String priority = cursor.getString(5);
                String notes = cursor.getString(6);

                // Create a new Event object with the retrieved data and add it to the list
                Event event = new Event(id, name, type, date,time, priority, notes);
                events.add(event);

            } while (cursor.moveToNext());
        }

        // Close the cursor and the database connection
        cursor.close();
        db.close();

        // Return the list of retrieved events
        return events;
    }

    public List<Event> readAllEvents(){
        List<Event> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                // Retrieve the data for each column in the current row
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String type = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                String priority = cursor.getString(5);
                String notes = cursor.getString(6);

                // Create a new Event object with the retrieved data and add it to the list
                Event event = new Event(id, name, type, date,time, priority, notes);
                events.add(event);

            } while (cursor.moveToNext());
        }//end if
     return events;
    }
}
