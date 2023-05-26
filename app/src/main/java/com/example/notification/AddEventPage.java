package com.example.notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddEventPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    EditText eventName,eventDescription;
    Button addEvent,dateButt,timeButt;

    String EventDate,EventTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        eventName = findViewById(R.id.nameOfEvent);
        eventDescription = findViewById(R.id.tasksOfEvent);
        addEvent = findViewById(R.id.addEvent);
        dateButt = findViewById(R.id.dateOfEvent);
        timeButt = findViewById(R.id.timeOfEvent);


        //date listener
        dateButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment df = new DateFragment();
                //show the date picker
                df.show(getSupportFragmentManager(),"DATE PICKER");
            }
        }); //end of date listener

        //time listener
        timeButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment tf = new TimeFragment();
                tf.show(getSupportFragmentManager(),"TIME PICKER");
            }
        });//end of time listener

        //add event listener
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //   addEventToDB();
                addEventToCalender();
            }
        });//end of add event listener
    }//end on Create

    private void addEventToDB() {
    }//end addEventToDB

    private void addEventToCalender() {
        CalendarPickerView datePicker = findViewById(R.id.calendar);

        Calendar c = Calendar.getInstance();
        c.set(2023,5,29);

        Date d = new Date(2023,5,29);
        List<Date> g = new ArrayList<Date>();
        g.add(d);
        Toast.makeText(this,""+g.isEmpty(),Toast.LENGTH_SHORT).show();
        datePicker.highlightDates(g);

   }//end addEventToCalender


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //format the calender object using the chosen date
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,day);

        EventDate = DateFormat.getDateInstance().format(c.getTime());
    }//end onDateSet

    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        String am_pm = "";

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, h);
        datetime.set(Calendar.MINUTE, m);

        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";

        EventTime = h +":"+m +" " +am_pm;
    }
}