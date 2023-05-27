package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

          MyDBHelper dbHelper = new MyDBHelper(this);
          List<Event> Events = dbHelper.readAllEvents();
          CustomCalendarCellDecorator decorator = new CustomCalendarCellDecorator(Events, datePicker);
          datePicker.setDecorators(Collections.singletonList(decorator));
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

    public CalendarPickerView getCalendarPickerView() {
        return datePicker;
    }

}