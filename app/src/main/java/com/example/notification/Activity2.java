package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Activity2 extends AppCompatActivity {

    EditText eventName,eventDescription;
    Button addEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        eventName = findViewById(R.id.nameOfEvent);
        eventDescription = findViewById(R.id.tasksOfEvent);
        addEvent = findViewById(R.id.addEvent);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eventName.getText().toString().isEmpty()) {
                    eventName.setHint("Please fill the event name");
                    eventName.setHintTextColor(getResources().getColor(R.color.warning));
                }
                else 
                    addEventToCalender();
            }
        });
    }//end on Create

    private void addEventToCalender() {
        
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, eventName.getText().toString());
        if(!eventDescription.getText().toString().isEmpty()){
            intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription.getText().toString());}

        startActivity(intent);
   }
}