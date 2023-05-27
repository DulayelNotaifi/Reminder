package com.example.notification;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;


public class ClosestEvents extends AppCompatActivity{

    private Button button;
    private Button closestEvent1;
    private Button closestEvent2;
    private Button closestEvent3;
    private Button closestEvent4;
    private Button closestEvent5;

    BottomNavigationView navigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        button = (Button) findViewById(R.id.appCompatButton);
        navigation = findViewById(R.id.nav);

        closestEvent1 = (Button) findViewById(R.id.appCompatButton2);
        closestEvent2 = (Button) findViewById(R.id.appCompatButton3);
        closestEvent3 = (Button) findViewById(R.id.appCompatButton4);
        closestEvent4 = (Button) findViewById(R.id.appCompatButton5);
        closestEvent5 = (Button) findViewById(R.id.appCompatButton8);
        getClsestEvents();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.calen:
                        openCalendarPage();
                        break;
                }
                return true;
            }
        });
    }

private void getClsestEvents(){
    // Create an instance of the database helper
    MyDBHelper dbHelper = new MyDBHelper(this);

    // Call the getClosestEvents() method to retrieve the closest 5 events
    List<Event> closestEvents = dbHelper.getClosestEvents();

    if(closestEvents.size() == 0)
        Toast.makeText(this, "NO EVENTS", Toast.LENGTH_SHORT).show();
    // Assign the name of each retrieved event to a button view in the layout
    if (closestEvents.size() >= 1) {
        closestEvent1.setText(closestEvents.get(0).getName());
    }
    if (closestEvents.size() >= 2) {
        closestEvent2.setText(closestEvents.get(1).getName());
    }
    if (closestEvents.size() >= 3) {
        closestEvent3.setText(closestEvents.get(2).getName());
    }
    if (closestEvents.size() >= 4) {
        closestEvent4.setText(closestEvents.get(3).getName());
    }
    if (closestEvents.size() >= 5) {
        closestEvent5.setText(closestEvents.get(4).getName());
    }
}

    private void openCalendarPage() {
        Intent intent=new Intent(this, CalenderPage.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent=new Intent(this, AddEventPage.class);
        startActivity(intent);
    }
    private void showWarningDialog(String name, String type, String date, String time, String priority, String notes){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(
                R.layout.alert_ui,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(name);
        ((TextView) view.findViewById(R.id.textMessage)).setText("Type: "+type + "\nDate: "+ date+"\nTime: " + time +"\nPriority: "+ priority + "\nNotes: "+notes);


        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.buttonConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); //?????

            }
        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); //delete query

            }
        });
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
}
