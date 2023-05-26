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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

    EditText eventNameEditText,eventNotesEditText;
    Button addEvent,dateButt,timeButt;

    String NameOfEvent,NotesOfEvent,EventDate,EventTime,PeriorityItem,TypeItem;

    String[] periorityItems = {"High","Medium","Low"};
    String[] typeItems = {"Meeting","Picnic","Presentation","Shopping","Family Visit","Study",
            "Appointment","Eid","Travel day","Other"};
    AutoCompleteTextView Periorityauto,Typesauto;
    ArrayAdapter<String> Periorityadaptor,Typesadaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

         //Setup Priority dropDown Menu
        Periorityauto = findViewById(R.id.auto1);
        Periorityadaptor = new ArrayAdapter<String>(this,R.layout.per_items,periorityItems);
        Periorityauto.setAdapter(Periorityadaptor);
        Periorityauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PeriorityItem = adapterView.getItemAtPosition(i).toString();
            }
        });

        //Setup Types dropDown Menu
        Typesauto = findViewById(R.id.auto2);
        Typesadaptor = new ArrayAdapter<String>(this,R.layout.eventtype_items,typeItems);
        Typesauto.setAdapter(Typesadaptor);
        Typesauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TypeItem = adapterView.getItemAtPosition(i).toString();
            }
        });

        eventNameEditText = findViewById(R.id.nameOfEvent);
        eventNotesEditText = findViewById(R.id.tasksOfEvent);
        addEvent = findViewById(R.id.addEvent);
        dateButt = findViewById(R.id.dateOfEvent);
        timeButt = findViewById(R.id.timeOfEvent);
        NameOfEvent = eventNameEditText.getText().toString();
        NotesOfEvent = eventNotesEditText.getText().toString();


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
               addEventToDB();
                addEventToCalender();
            }
        });//end of add event listener
    }//end on Create

    private void addEventToDB() {
        MyDBHelper myDB = new MyDBHelper(AddEventPage.this);
        myDB.addEvent(NameOfEvent,TypeItem,EventDate,EventTime,PeriorityItem,NotesOfEvent);
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