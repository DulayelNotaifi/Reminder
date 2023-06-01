package com.example.notification;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {
    TextView txt1, txt2, txt3, txt4, txt5, txt6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Bundle e = getIntent().getExtras();
        try{
            if (e != null) {
                String title = e.getString("title");
                String notes = e.getString("Notes");
                String date = e.getString("eventDate");
                String time = e.getString("eventTime");
                String pri = e.getString("Priority");
                String type = e.getString("Type");


                txt1 = (TextView) txt1.findViewById(R.id.textView99);
                txt2 = (TextView) txt2.findViewById(R.id.textView111);
                txt3 = (TextView) txt3.findViewById(R.id.textView400);
                txt4 = (TextView) txt4.findViewById(R.id.textView105);
                txt5 = (TextView) txt5.findViewById(R.id.textView166);
                txt6 = (TextView) txt6.findViewById(R.id.textView177);
                txt1.setText(title);
                txt2.setText(notes);
                txt3.setText(date);
                txt4.setText(time);
                txt5.setText(pri);
                txt6.setText(type);

            }}
        catch (Exception g){
            Toast.makeText(this,g.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }}

