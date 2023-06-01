package com.example.notification;

import android.content.Intent;
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
        getClosestEvents();
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
protected void onResume() {
    super.onResume();
    getClosestEvents();
}
    private void getClosestEvents() {
        // Create an instance of the database helper
        MyDBHelper dbHelper = new MyDBHelper(this);

        // Call the getClosestEvents() method to retrieve the closest 5 events
        List<Event> closestEvents = dbHelper.getClosestEvents();

        Button[] eventButtons = {
                findViewById(R.id.appCompatButton2),
                findViewById(R.id.appCompatButton3),
                findViewById(R.id.appCompatButton4),
                findViewById(R.id.appCompatButton5),
                findViewById(R.id.appCompatButton8)
        };

        // Find the TextView for displaying "NO EVENTS" in your layout
        TextView noEventsTextView = findViewById(R.id.no_events_text_view);

        if (closestEvents.size() == 0) {
            noEventsTextView.setVisibility(View.VISIBLE);
        } else {
            noEventsTextView.setVisibility(View.GONE);
        }

        // Assign the name of each retrieved event to a button view in the layout and set visibility
        for (int i = 0; i < eventButtons.length; i++) {
            final Button eventButton = eventButtons[i];
            if (i < closestEvents.size()) {
                final Event event = closestEvents.get(i);
                eventButton.setText(event.getName());
                eventButton.setVisibility(View.VISIBLE);
                eventButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showWarningDialog(event.getId(), event.getName(), event.getType(), event.getDate(), event.getTime(), event.getPriority(), event.getNotes(), event.getRemindTime());
                    }
                });
            } else {
                eventButton.setVisibility(View.GONE);
            }
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
    private void showWarningDialog(int id, String name, String type, String date, String time, String priority, String notes, String remindTime){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(this).inflate(
                R.layout.alert_ui,
                (ConstraintLayout)findViewById(R.id.layoutDialogContainer)
        );
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(name);
        ((TextView) view.findViewById(R.id.textMessage)).setText("Type: "+type + "\nDate: "+ date+"\nTime: " + time +"\nPriority: "+ priority + "\nNotes: "+notes+ "\nRemind Time: "+remindTime);


        final AlertDialog alertDialog = builder.create();
        MyDBHelper dbHelper = new MyDBHelper(this);
        view.findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss(); //?????
                        Intent intent = new Intent(getApplicationContext(), AddEventPage.class);
                        intent.putExtra("id", id);
                        intent.putExtra("name", name);
                        intent.putExtra("type",type);
                        intent.putExtra("date",date);
                        intent.putExtra("time",time);
                        intent.putExtra("priority",priority);
                        intent.putExtra("notes",notes);
                        intent.putExtra("remindTime", remindTime);
                        startActivity(intent);
                    }

        });

        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteEvent(id);
                getClosestEvents();
                alertDialog.dismiss();

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
