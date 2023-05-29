package com.example.notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

//import com.squareup.timessquare.CalendarPickerView;

//import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddEventPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Calendar cad,rem;
    EditText eventNameEditText,eventNotesEditText , eventTypeEditText,eventDateEditText, eventTimeEditText, eventPriorityEditText;
    Button addEvent,dateButt,timeButt;

    String NameOfEvent,NotesOfEvent,EventDate,EventTime,PeriorityItem,TypeItem,RemindItem;

    String[] periorityItems = {"High","Medium","Low"};
    String[] typeItems = {"Meeting","Picnic","Presentation","Shopping","Family Visit","Study",
            "Appointment","Eid","Travel day","Other"};

    String[] remindItems = {"10 min","15 min","20 min","25 min","30 min","35 min","40 min","45 min","50 min","60 min","120 min"};
    AutoCompleteTextView Periorityauto,Typesauto,reminauto;
    ArrayAdapter<String> Periorityadaptor,Typesadaptor,remindadaptor;
    private int year;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //for noti
        cad=Calendar.getInstance();
        rem=Calendar.getInstance();
        createNotificationChannel();


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

        //setup Remind dropDown Menu
        reminauto = findViewById(R.id.auto3);
        remindadaptor = new ArrayAdapter<String>(this,R.layout.alert_items,remindItems);
        reminauto.setAdapter(remindadaptor);

        reminauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RemindItem = adapterView.getItemAtPosition(i).toString();
            }
        });


        eventNameEditText = findViewById(R.id.nameOfEvent);
        /*eventTypeEditText = findViewById(R.id.nameOfEvent);
        eventDateEditText = findViewById(R.id.dateOfEvent);
        eventTimeEditText = findViewById(R.id.timeOfEvent);
        eventPriorityEditText = findViewById(R.id.auto1);*/
        eventNotesEditText = findViewById(R.id.tasksOfEvent);
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
                NameOfEvent = eventNameEditText.getText().toString();
                NotesOfEvent = eventNotesEditText.getText().toString();
                if(NameOfEvent==null||NotesOfEvent==null||EventDate==null||EventTime==null||PeriorityItem==null||TypeItem==null)
                    showWarningDialog("Please fill all the required fields");
                else {
                    addEventToDB();
                    //  addEventToCalender();
                    scheduleNotification();
                }
            }
        });//end of add event listener


        // for edit page
        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            // Get event info from intent extras and populate views
            addEvent.setText("Edit Event");
        }
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper db = new MyDBHelper(AddEventPage.this);
                if (addEvent.getText().equals("Edit Event")) {
                    // Delete existing event
                    db.deleteEvent(Integer.parseInt(intent.getStringExtra("id")));
                }
                // Add edited event to database
                addEventToDB();
                finish();
            }
        });

       // Get event info from intent extras
        String eventName = intent.getStringExtra("name");
        String eventType = intent.getStringExtra("type");
        String eventDate = intent.getStringExtra("date");
        String eventTime = intent.getStringExtra("time");
        String eventPriority = intent.getStringExtra("priority");
        String eventNotes = intent.getStringExtra("notes");

// Populate views with event info
        eventNameEditText.setText(eventName);
        /* eventTypeEditText.setText(eventType);
        eventDateEditText.setText(eventDate);
        eventTimeEditText.setText(eventTime);
        eventPriorityEditText.setText(eventPriority);*/
        eventNotesEditText.setText(eventNotes);
    }//end on Create


    void scheduleNotification() {
        cad.set(Calendar.SECOND,0);
        rem.set(Calendar.SECOND,0);
        Intent notificationIntent = new Intent(this.getApplicationContext(), Notif.class);
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        notificationIntent.putExtra("title","your "+NameOfEvent+" event starts now");
        notificationIntent.putExtra("message","for more info");/////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                notificationIntent, PendingIntent.FLAG_MUTABLE//FLAG_UPDATE_CURRENT
        );//getActivity
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        long time=getTime();
        //Toast.makeText(getApplicationContext(),cad.getTime()+"cad",Toast.LENGTH_LONG).show();

        am.set(AlarmManager. RTC_WAKEUP , time , pi) ;
        //////
        Intent deIntent=new Intent(this.getApplicationContext(),Details.class);
        deIntent.putExtra("title",NameOfEvent);
        deIntent.putExtra("Notes",NotesOfEvent);
        deIntent.putExtra("eventDate",EventDate);
        deIntent.putExtra("eventTime",EventTime);
        deIntent.putExtra("Priority",PeriorityItem);
        deIntent.putExtra("Type",TypeItem);

        /////



        Intent remIntent = new Intent(this.getApplicationContext(), rem.class);
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        remIntent.putExtra("title","this is a reminder your "+NameOfEvent+" event starts in "+RemindItem);
        remIntent.putExtra("message","for more info");/////////////////////////////////////////////////////
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        PendingIntent ri = PendingIntent.getBroadcast(this, 0,
                remIntent, PendingIntent.FLAG_MUTABLE//FLAG_UPDATE_CURRENT
        );//getActivity
        getTime(Integer.parseInt(RemindItem.substring(0,RemindItem.indexOf(" "))));/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        long timeRem=rem.getTimeInMillis();
        //Toast.makeText(getApplicationContext(),rem.getTime()+"rem",Toast.LENGTH_LONG).show();
        AlarmManager rm=(AlarmManager)getSystemService(ALARM_SERVICE);
        //Toast.makeText(getApplicationContext(),RemindItem.substring(0,RemindItem.indexOf(" "))+"this is",Toast.LENGTH_LONG).show();
        rm.set(AlarmManager. RTC_WAKEUP , timeRem , ri) ;
        //Toast.makeText(getApplicationContext(),"4",Toast.LENGTH_LONG).show();
        //
        }
    long getTime(){
        return cad.getTimeInMillis();

    }
    long getTime(int mins){
        rem.add(Calendar.MINUTE, -1*mins);
        return rem.getTimeInMillis();
    }
    private void addEventToDB() {
        MyDBHelper myDB = new MyDBHelper(AddEventPage.this);
        myDB.addEvent(NameOfEvent,TypeItem,EventDate,EventTime,PeriorityItem,NotesOfEvent);
        Intent intent = new Intent(this, ClosestEvents.class);
        startActivity(intent);
    }//end addEventToDB


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        cad.set(Calendar.YEAR,year);
        cad.set(Calendar.MONTH,month);
        cad.set(Calendar.DAY_OF_MONTH,day);
        rem.set(Calendar.YEAR,year);
        rem.set(Calendar.MONTH,month);
        rem.set(Calendar.DAY_OF_MONTH,day);
        String monthd ="";
        month = month+1;
        if(month < 10){
            monthd = "0"+month;
        }
        else{
            monthd = ""+month;
        }
        String singleDay = "";
        if(day < 10){
            singleDay = "0"+day;
        }
        else{
            singleDay = ""+day;
        }
        String date = ""+year +"-"+monthd+"-"+singleDay;
        EventDate = date;

    }//end onDateSet

    @Override
    public void onTimeSet(TimePicker timePicker, int h, int m) {
        String am_pm = "";

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, h);
        datetime.set(Calendar.MINUTE, m);
        cad.set(Calendar.HOUR_OF_DAY, h);
        cad.set(Calendar.MINUTE, m);
        rem.set(Calendar.HOUR_OF_DAY, h);
        rem.set(Calendar.MINUTE, m);

        String h2 = "";
        if(h < 10){
            h2 = "0"+h;
        }
        else{
            h2 = ""+h;
        }
        String m2 = "";
        if(m < 10){
            m2 = "0"+m;
        }
        else{
            m2 = ""+m;
        }
        if (datetime.get(Calendar.AM_PM) == Calendar.AM)
            am_pm = "AM";
        else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
            am_pm = "PM";
        EventTime = h2 +":"+m2 +" " +am_pm;
    }
    private void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        NotificationChannel nc= new NotificationChannel("chan1", "chan1", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager nm=( NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.createNotificationChannel(nc);}
    }
    private void showWarningDialog(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(
                R.layout.layout_error_dailog,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.errorMessage)).setText(msg);


        final AlertDialog alertDialog = builder.create();
        view.findViewById(R.id.buttonAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });


        if (alertDialog.getWindow() != null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}