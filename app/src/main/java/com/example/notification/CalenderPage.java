package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

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
import java.util.Date;
import java.util.List;

public class CalenderPage extends AppCompatActivity {

    CalendarPickerView datePicker;
    BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);

        navigation = findViewById(R.id.nav);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);

        Event e = new Event(11, "test11", "Meeting", "2023-05-30", "07:30", "High", "");
        Event e2 = new Event(12, "test12", "Presentation", "2023-05-31", "07:30", "High", "");

        List<Event> events = new ArrayList<>();
        events.add(e);
        events.add(e2);
        CustomCalendarCellDecorator decorator = new CustomCalendarCellDecorator(events, datePicker);
        datePicker.setDecorators(Collections.singletonList(decorator));

        // add a public method named getCalendarPickerView() that returns a reference to the calendarPickerView object

        /*
        ArrayList<Date> d = new ArrayList<Date>();
        Date d1 = parseDate("2023-05-28");
        Date d2 = parseDate("2023-05-30");
        Date d3 = parseDate("2023-05-31");
        d.add(d1);
        d.add(d2);
        d.add(d3);
        datePicker.highlightDates(d);*/

        /*// Define a list of dates to highlight
        List<Date> datesToHighlight = new ArrayList<>();
        datesToHighlight.add(parseDate("2023-05-28"));
        datesToHighlight.add(parseDate("2023-05-30"));
        datesToHighlight.add(parseDate("2023-05-31"));

        // Create a custom decorator to highlight thedates
        CustomCalendarCellDecorator decorator = new CustomCalendarCellDecorator(datesToHighlight);

        // Set the custom decorator to the CalendarPickerView
        datePicker.setDecorators(Collections.singletonList(decorator));*/

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




        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void openhome() {
        Intent intent=new Intent(this, ClosestEvents.class);
        startActivity(intent);
    }


    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public CalendarPickerView getCalendarPickerView() {
        return datePicker;
    }

}