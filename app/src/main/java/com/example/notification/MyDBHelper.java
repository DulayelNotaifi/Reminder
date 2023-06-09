package com.example.notification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String COLUMN_RemindTime = "Event_remindTime";

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
                COLUMN_NOTES + " TEXT," +
                COLUMN_RemindTime + " TEXT);" ;
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addEvent(String name,String type,String date, String time, String priority, String notes,String remind){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_Type,type);
        cv.put(COLUMN_DATE,date);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_PRIORITY,priority);
        if(notes != null && !notes.isEmpty())
          cv.put(COLUMN_NOTES,notes);
        else
            cv.put(COLUMN_NOTES,"no notes");

        cv.put(COLUMN_RemindTime,remind);

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
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE (" + COLUMN_DATE + " > date('now') OR (" + COLUMN_DATE + " = date('now') AND " + COLUMN_TIME + " > time('now'))) ORDER BY " + COLUMN_DATE + " ASC, " + COLUMN_TIME + " ASC LIMIT 5";
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
                String remindTime = cursor.getString(7);

                // Create a new Event object with the retrieved data and add it to the list
                Event event = new Event(id, name, type, date,time, priority, notes,remindTime);
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
                String remindTime = cursor.getString(7);

                // Create a new Event object with the retrieved data and add it to the list
                Event event = new Event(id, name, type, date,time, priority, notes,remindTime);
                events.add(event);

            } while (cursor.moveToNext());
        }//end if
     return events;
    }
    int idio(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + "='" + name + "'";

// Execute the query and retrieve the resulting cursor
        Cursor cursor = db.rawQuery(selectQuery, null);
        int id = 0;

// Iterate over the cursor and retrieve the event data for each row
        while (cursor.moveToNext()) {
            // Retrieve the event data from the cursor
            id = cursor.getInt(0);

        }

// Close the cursor and the database connection
        cursor.close();
        db.close();
        return id;
    }
        public void deleteEvent(int idNum) {
        //Toast.makeText(context.getApplicationContext(),idNum+" id",Toast.LENGTH_LONG).show();

        Intent notificationIntent = new Intent(context, Notif.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, idNum,
                notificationIntent, PendingIntent.FLAG_MUTABLE
        );//getActivity
        AlarmManager am=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        am.cancel(pi);


        Intent remIntent = new Intent(context, rem.class);
        PendingIntent ri = PendingIntent.getBroadcast(context, idNum+1000000,
                remIntent, PendingIntent.FLAG_MUTABLE//FLAG_UPDATE_CURRENT//FLAG_MUTABLE
        );
        AlarmManager rm=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        rm.cancel(ri) ;

        SQLiteDatabase db = this.getReadableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=" + idNum, null);

        if(result == -1){
            Toast.makeText(context, "Failed to delete the event", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Event Deleted Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }
    public void updateEvent(int id,String name,String type,String date, String time, String priority, String notes,String remind){
         String Query = "UPDATE " +TABLE_NAME+" SET "+ COLUMN_NAME +" = '"+ name+"',"+ COLUMN_Type +" = '" +type+"',"+COLUMN_DATE +" = '"+ date+
                        "',"+ COLUMN_TIME +" = '" +time+"',"+COLUMN_PRIORITY +" = '" +priority+"',"+COLUMN_NOTES
                         +" = '"+ notes+"',"+ COLUMN_RemindTime +" = '" +remind+ "' WHERE "+COLUMN_ID +" = "+id+";";
         
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Query);
    }

    public List<Event> eventsOfDate(Date d) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Format the date to match the format used in the database
        // Format the date to match the format used in the database
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(d);

// Define the SQL query to retrieve the event data for the specified date
        String selectQuery = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_Type + ", " + COLUMN_DATE + ", " + COLUMN_TIME + ", " + COLUMN_PRIORITY + ", " + COLUMN_NOTES + ", " + COLUMN_RemindTime + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + "='" + strDate + "' ORDER BY " + COLUMN_TIME + " ASC";

// Execute the query and retrieve the resulting cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

// Initialize a list to hold the events
        List<Event> events = new ArrayList<>();

// Iterate over the cursor and retrieve the event data for each row
        while (cursor.moveToNext()) {
            // Retrieve the event data from the cursor
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String date = cursor.getString(3);
            String time = cursor.getString(4);
            String priority = cursor.getString(5);
            String notes = cursor.getString(6);
            String remindTime = cursor.getString(7);

            // Create a new Event object with the retrieved data and add it to the list
            Event event = new Event(id, name, type, date, time, priority, notes, remindTime);
            events.add(event);
        }

// Close the cursor and the database connection
        cursor.close();
        db.close();

// Return the list of events
        return events;
    }
}
