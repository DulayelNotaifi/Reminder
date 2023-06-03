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
import android.content.Context;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AddEventPage extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

   // Define variables
    Calendar cad,rem;
    EditText eventNameEditText,eventNotesEditText , eventTypeEditText,eventDateEditText, eventTimeEditText, eventPriorityEditText;
    Button addEvent,dateButt,timeButt;
    String NameOfEvent,NotesOfEvent,EventDate,EventTime,PeriorityItem,TypeItem,RemindItem;
    String[] periorityItems = {"High","Medium","Low"};
    String[] typeItems = {"Meeting","Picnic","Presentation","Shopping","Family Visit","Study","Appointment","Eid","Travel day","Other"};
    String[] remindItems = {"10 min","15 min","20 min","25 min","30 min","35 min","40 min","45 min","50 min","60 min","120 min"};
    AutoCompleteTextView Periorityauto,Typesauto,reminauto;
    ArrayAdapter<String> Periorityadaptor,Typesadaptor,remindadaptor;
Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        context = this;


        //for notification
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

        //add event button listener
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NameOfEvent = eventNameEditText.getText().toString();
                NotesOfEvent = eventNotesEditText.getText().toString();

                //Missing field validation
                String missingFields = "";
                if (NameOfEvent.trim().isEmpty() || NameOfEvent.equals("") || EventDate == null || EventTime == null || PeriorityItem == null || TypeItem == null || RemindItem == null) {
                    if (NameOfEvent.trim().isEmpty() || NameOfEvent.equals(""))
                        missingFields = "Name\n";
                    if (TypeItem == null) {
                        missingFields += "Type\n";
                    }
                    if (PeriorityItem == null) {
                        missingFields += "Priority\n";
                    }
                    if (EventDate == null) {
                        missingFields += "Date\n";
                    }
                    if (EventTime == null) {
                        missingFields += "Time\n";
                    }
                    if (RemindItem == null) {
                        missingFields += "Reminder\n";
                    }
                    missingFields = "Please fill all the missing fields:\n" + missingFields;
                } else {
                    try {
                        // Additional validation for today's date and past time
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                        Calendar currentDateTime = Calendar.getInstance();
                        Calendar selectedDateTime = Calendar.getInstance();
                        selectedDateTime.setTime(dateFormat.parse(EventDate));

                        Date parsedEventTime = timeFormat.parse(EventTime);
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, parsedEventTime.getHours());
                        selectedDateTime.set(Calendar.MINUTE, parsedEventTime.getMinutes());

                        if (selectedDateTime.get(Calendar.YEAR) == currentDateTime.get(Calendar.YEAR) &&
                                selectedDateTime.get(Calendar.MONTH) == currentDateTime.get(Calendar.MONTH) &&
                                selectedDateTime.get(Calendar.DAY_OF_MONTH) == currentDateTime.get(Calendar.DAY_OF_MONTH) &&
                                selectedDateTime.getTimeInMillis() <= currentDateTime.getTimeInMillis()) {

                            missingFields += "\nPlease select a future time for today's events.";
                        }
                    } catch (ParseException e) {
                        // Handle parsing exceptions
                        e.printStackTrace();
                    }
                }
                if (!missingFields.isEmpty()) {
                    showWarningDialog(missingFields);
                } else {
                            addEventToDB();
                            scheduleNotification();
                        }

        }});//end of add event button listener


        // In case the page is being displayed for editing
       Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            addEvent.setText("Edit Event");
        }
        if(addEvent.getText().equals("Edit Event")){
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (addEvent.getText().equals("Edit Event")) {
                            NameOfEvent = eventNameEditText.getText().toString();
                            MyDBHelper db = new MyDBHelper(AddEventPage.this);
                            NameOfEvent = eventNameEditText.getText().toString();
                            NotesOfEvent = eventNotesEditText.getText().toString();
                            int EventTodelete = intent.getIntExtra("id",0);
                            if(TypeItem== null) TypeItem =intent.getStringExtra("type");
                            if(PeriorityItem ==null) PeriorityItem = intent.getStringExtra("priority");
                            if(RemindItem == null) RemindItem = intent.getStringExtra("remindTime");
                            if(EventDate == null) EventDate = intent.getStringExtra("date");
                            if(EventTime == null) EventTime = intent.getStringExtra("time");
                        Intent notificationIntent = new Intent(context, Notif.class);
                        PendingIntent pi = PendingIntent.getBroadcast(context, EventTodelete,
                                notificationIntent, PendingIntent.FLAG_MUTABLE
                        );//getActivity
                        AlarmManager am=(AlarmManager)context.getSystemService(ALARM_SERVICE);
                        am.cancel(pi);


                        Intent remIntent = new Intent(context, rem.class);
                        PendingIntent ri = PendingIntent.getBroadcast(context, EventTodelete+1000000,
                                remIntent, PendingIntent.FLAG_MUTABLE//FLAG_UPDATE_CURRENT//FLAG_MUTABLE
                        );
                        AlarmManager rm=(AlarmManager)context.getSystemService(ALARM_SERVICE);
                        rm.cancel(ri) ;
                            db.updateEvent(EventTodelete,NameOfEvent,TypeItem,EventDate,EventTime,PeriorityItem,NotesOfEvent,RemindItem);
                        scheduleNotification();
                    }
                    Toast.makeText(AddEventPage.this, "Event edited sucessfully", Toast.LENGTH_SHORT).show();
                   // setResult(RESULT_OK);
                    Intent i = new Intent(context,CalenderPage.class);
                    i.putExtra("addedDate", EventDate );
                    startActivity(i);
                }
            });

            // Get event info from intent extras and Populate views with event info
            eventNameEditText.setText(intent.getStringExtra("name"));
            Typesauto.setText(intent.getStringExtra("type"));
            Typesadaptor = new ArrayAdapter<String>(this,R.layout.eventtype_items,typeItems);
            Typesauto.setAdapter(Typesadaptor);
            Typesauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TypeItem = adapterView.getItemAtPosition(i).toString();
                }
            });

            Periorityauto.setText(intent.getStringExtra("priority"));
            Periorityadaptor = new ArrayAdapter<String>(this,R.layout.per_items,periorityItems);
            Periorityauto.setAdapter(Periorityadaptor);
            Periorityauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    PeriorityItem = adapterView.getItemAtPosition(i).toString();
                }
            });

            reminauto.setText(intent.getStringExtra("remindTime"));
            remindadaptor = new ArrayAdapter<String>(this,R.layout.alert_items,remindItems);
            reminauto.setAdapter(remindadaptor);
            reminauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    RemindItem = adapterView.getItemAtPosition(i).toString();
                }
            });
            eventNotesEditText.setText(intent.getStringExtra("notes"));

            String eventDate = intent.getStringExtra("date");
            String eventTime = intent.getStringExtra("time");
            dateButt.setText(eventDate);
            timeButt.setText(eventTime);
        }//end big if
        ////End of editing Page

        // Get the selected date from the intent extras
        if (intent.hasExtra("selectedDate")) {
            String eventDate = intent.getStringExtra("selectedDate");
            dateButt.setText(eventDate);
            EventDate = intent.getStringExtra("selectedDate");
        }
    }//end on Create


    int idio(String name){
        MyDBHelper myDB = new MyDBHelper(AddEventPage.this);
        return myDB.idio(name);
    }

    void scheduleNotification() {
        int iddd=idio(NameOfEvent);
        cad.set(Calendar.SECOND,0);
        rem.set(Calendar.SECOND,0);
        Intent notificationIntent = new Intent(this.getApplicationContext(), Notif.class);
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        notificationIntent.putExtra("title","your "+NameOfEvent+" event starts now");
        notificationIntent.putExtra("message","for more info");/////////////////////////////////////////////////////


        /////*****
        notificationIntent.putExtra("name",NameOfEvent);
        notificationIntent.putExtra("Notes",NotesOfEvent);
        notificationIntent.putExtra("eventDate",EventDate);
        notificationIntent.putExtra("eventTime",EventTime);
        notificationIntent.putExtra("Priority",PeriorityItem);
        notificationIntent.putExtra("Type", TypeItem);

        /////****

        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        PendingIntent pi = PendingIntent.getBroadcast(this, iddd,
                notificationIntent, PendingIntent.FLAG_MUTABLE//FLAG_UPDATE_CURRENT
        );//getActivity
        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);
        long time=getTime();
        //Toast.makeText(getApplicationContext(),cad.getTime()+"cad",Toast.LENGTH_LONG).show();

        am.set(AlarmManager. RTC_WAKEUP , time , pi) ;
        //////
        //////
        //Intent deIntent=new Intent(this.getApplicationContext(),Details.class);
        //deIntent.putExtra("title",NameOfEvent);
        //deIntent.putExtra("Notes",NotesOfEvent);
        //deIntent.putExtra("eventDate",EventDate);
        //deIntent.putExtra("eventTime",EventTime);
       // deIntent.putExtra("Priority",PeriorityItem);
        //deIntent.putExtra("Type",TypeItem);


        Intent remIntent = new Intent(this.getApplicationContext(), rem.class);
        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        remIntent.putExtra("title","this is a reminder your "+NameOfEvent+" event starts in "+RemindItem);
        remIntent.putExtra("message","for more info");/////////////////////////////////////////////////////

        ///////****
        remIntent.putExtra("name",NameOfEvent);
        remIntent.putExtra("Notes",NotesOfEvent);
        remIntent.putExtra("eventDate",EventDate);
        remIntent.putExtra("eventTime",EventTime);
        remIntent.putExtra("Priority",PeriorityItem);
        remIntent.putExtra("Type", TypeItem);
        /////***


        //Toast.makeText(getApplicationContext(),this.NameOfEvent,Toast.LENGTH_LONG).show();
        PendingIntent ri = PendingIntent.getBroadcast(this, iddd+1000000 ,
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
        myDB.addEvent(NameOfEvent,TypeItem,EventDate,EventTime,PeriorityItem,NotesOfEvent,RemindItem);
        Intent i = new Intent(this, CalenderPage.class);
        i.putExtra("addedDate", EventDate );
        startActivity(i);
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
        dateButt.setText(date);
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
        timeButt.setText(EventTime);
    }//end onTimeSet

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