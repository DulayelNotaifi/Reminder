package com.example.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalenderPage extends AppCompatActivity {

    CalendarPickerView datePicker;
    BottomNavigationView navigation;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);
        context = this;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                View view = LayoutInflater.from(context).inflate(
                        R.layout.popup_ui,
                        (ConstraintLayout)findViewById(R.id.layoutDialogContainer));
                builder.setView(view);

           MyDBHelper helper = new MyDBHelper(context);
           String myevents = helper.eventsOfDate(date);


                    ((TextView) view.findViewById(R.id.txTitle)).setText("Events");
                ((TextView) view.findViewById(R.id.txMessage)).setText(myevents);
                final AlertDialog alertDialog = builder.create();
            //    MyDBHelper dbHelper = new MyDBHelper(context);

                view.findViewById(R.id.buttonDismiss).setOnClickListener(new View.OnClickListener() {
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