package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalenderPage extends AppCompatActivity {
    public static final int REQUEST_CODE_UPDATE_EVENT = 1;
    CalendarPickerView datePicker;

    BottomNavigationView navigation;
    Context context;
    List<Event> Events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);
        context = this;
        navigation = findViewById(R.id.nav);
        datePicker = findViewById(R.id.calendar);

        // Get events for today's date
        MyDBHelper helper = new MyDBHelper(context);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
        // Format the date as a string
        String SELECTED_DATE ;
        String qSELECTED_DATE ;

        // Initialize RecyclerView
        RecyclerView recycle = findViewById(R.id.rec);
        recycle.setLayoutManager(new LinearLayoutManager(context));



        Intent intent = getIntent();
        if (intent.hasExtra("addedDate")) {
            try {
                Date addedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(intent.getStringExtra("addedDate"));
                List<Event> myevents = helper.eventsOfDate(addedDate);
                SELECTED_DATE = sdf.format(addedDate);
                qSELECTED_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(addedDate);
                EventsRecycleAdapter adapt = new EventsRecycleAdapter(myevents, context, qSELECTED_DATE);
                recycle.setAdapter(adapt);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else {
            // Get events for today's date
            Date today = new Date();
            String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today);
            List<Event> myevents = helper.eventsOfDate(today); // Pass Date object instead of String
            // Create a SimpleDateFormat object with the desired date format
            // Format the date as a string
            SELECTED_DATE = sdf.format(today);
            qSELECTED_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(today);

            EventsRecycleAdapter adapt = new EventsRecycleAdapter(myevents, context, qSELECTED_DATE);
            recycle.setAdapter(adapt);
        }
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        openhome();
                    case R.id.calen:
                        break;
                }
                return true;
            }
        });

        calindarEvents();
    }//end OnCreate


    public void calindarEvents(){

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);

        MyDBHelper dbHelper = new MyDBHelper(this);
        Events = dbHelper.readAllEvents();
        CustomCalendarCellDecorator decorator = new CustomCalendarCellDecorator(Events, datePicker);
        datePicker.setDecorators(Collections.singletonList(decorator));

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                MyDBHelper helper = new MyDBHelper(context);
                List<Event> myevents = helper.eventsOfDate(date);
                RecyclerView recycle = findViewById(R.id.rec);
                // Create a SimpleDateFormat object with the desired date format
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
                // Format the date as a string
                String SELECTED_DATE =  sdf.format(date);
                String qSELECTED_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
                EventsRecycleAdapter adapt = new EventsRecycleAdapter(myevents,context,qSELECTED_DATE );
                recycle.setAdapter(adapt);

            }

            @Override
            public void onDateUnselected(Date date) {}
        });

        //Recycle view
        RecyclerView recycle = findViewById(R.id.rec);
        recycle.setHasFixedSize(true);
        LinearLayoutManager LayoutManegar = new LinearLayoutManager(context);
        recycle.setLayoutManager(LayoutManegar);
    }
    private void openhome() {
        Intent intent=new Intent(this, ClosestEvents.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPDATE_EVENT && resultCode == RESULT_OK) {
            MyDBHelper dbHelper = new MyDBHelper(this);
            Events = dbHelper.readAllEvents();

            // Sort the events by their time
            Collections.sort(Events, new Comparator<Event>() {
                @Override
                public int compare(Event e1, Event e2) {
                    return e1.getTime().compareTo(e2.getTime());
                }
            });

            // Get the selected date from the datePicker and format it as a string
            // Create a SimpleDateFormat object with the desired date format
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd", Locale.getDefault());
            String qSELECTED_DATE = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(datePicker.getSelectedDate().getDate());
            String selectedDate = sdf.format(datePicker.getSelectedDate().getDate());

            // Update the adapter's data with the refreshed and sorted list of events
            RecyclerView recyclerView = findViewById(R.id.rec);
            EventsRecycleAdapter eventsRecycleAdapter = (EventsRecycleAdapter) recyclerView.getAdapter();
            if (eventsRecycleAdapter != null) {
                eventsRecycleAdapter.updateData(Events);
            } else {
                // Create a new adapter with the updated events list if there was no existing adapter
                EventsRecycleAdapter newAdapter = new EventsRecycleAdapter(Events, this, qSELECTED_DATE);
                recyclerView.setAdapter(newAdapter);
            }

            // Refresh the calendar
            CustomCalendarCellDecorator decorator = new CustomCalendarCellDecorator(Events, datePicker);
            datePicker.setDecorators(Collections.singletonList(decorator));
            calindarEvents();
        }
    }
}