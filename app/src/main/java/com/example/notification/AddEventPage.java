package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

public class AddEventPage extends AppCompatActivity {

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
                    addEventToCalender();
            }
        });
    }//end on Create

    private void addEventToCalender() {
        
   }
}