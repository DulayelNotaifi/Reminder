package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalenderPage extends AppCompatActivity {

    CalendarPickerView datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

         datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);
        ArrayList<Date> d = new ArrayList<Date>();
        Date d1 = parseDate("2023-05-28");
        Date d2 = parseDate("2023-05-30");
        Date d3 = parseDate("2023-05-31");
        d.add(d1);
        d.add(d2);
        d.add(d3);
        datePicker.highlightDates(d);


        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                dateSelected();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void dateSelected() {
    }



    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}