package com.example.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ClosestEvents extends AppCompatActivity {

    private Button button,viewCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        button=(Button) findViewById(R.id.appCompatButton);


        ////Added new
        viewCalendar = (Button) findViewById(R.id.viewCalendar);
        viewCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarPage();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }

    private void openCalendarPage() {
        Intent intent=new Intent(this, CalenderPage.class);
        startActivity(intent);
    }

    public void openActivity2(){
        Intent intent=new Intent(this, AddEventPage.class);
        startActivity(intent);
    }
}
