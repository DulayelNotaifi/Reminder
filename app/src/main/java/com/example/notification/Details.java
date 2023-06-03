package com.example.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {
    TextView txt1, txt2, txt3, txt4, txt5, txt6;
    Button Button22;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Intent i=getIntent();
        String title=i.getStringExtra("name");
        String notes=i.getStringExtra("Notes");
        String date=i.getStringExtra("eventDate");
        String time=i.getStringExtra("eventTime");
        String pri=i.getStringExtra("Priority");
        String type=i.getStringExtra("Type");

        txt1 = (TextView) findViewById(R.id.textView99);
        txt2 = (TextView) findViewById(R.id.textView111);
        txt3 = (TextView) findViewById(R.id.textView400);
        txt4 = (TextView) findViewById(R.id.textView105);
        txt5 = (TextView) findViewById(R.id.textView166);
        txt6 = (TextView) findViewById(R.id.textView177);
        txt1.setText("Event Name: " +title);
        txt2.setText("Notes: "+notes);
        txt3.setText("Event Date: "  + date);
        txt4.setText("Event Time: " +  time);
        txt5.setText( "Event Priority: " + pri);
        txt6.setText( "Event Type: " + type);

        Button22=(Button)findViewById(R.id.Hhome);

        Button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClosestsEvents();
            }
        });








        //}
        // }
        //catch (Exception g){
        // Toast.makeText(this,g.getMessage(),Toast.LENGTH_SHORT).show();
        // }

    }
    public void  openClosestsEvents(){
        Intent openClosest=new Intent(this, ClosestEvents.class);
        startActivity(openClosest);
 }
}

