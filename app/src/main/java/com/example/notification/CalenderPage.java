package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class CalenderPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = findViewById(R.id.calendar);
        datePicker.init(today, nextYear.getTime()).withSelectedDate(today);

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
}