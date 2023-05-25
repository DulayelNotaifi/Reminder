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

//             if(eventDescription.getText().toString().isEmpty()){
//                 eventDescription.
//             }
                else 
                    addEventToCalender();
            }
        });


    }

    private void addEventToCalender() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI); //the data we are working with ia a calendar even

        intent.putExtra(CalendarContract.Events.TITLE,eventName.getText().toString());
        if(!eventDescription.getText().toString().isEmpty()){
            intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription.getText().toString());
        }
        intent.putExtra(CalendarContract.Events.ALL_DAY,false);

//        String stime = "7:20";
//        intent.putExtra(CalendarContract.Events.DTSTART,stime);
//
//        String etime = "7:20";
//        intent.putExtra(CalendarContract.Events.DTEND,etime);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
        else
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT);


    }
}