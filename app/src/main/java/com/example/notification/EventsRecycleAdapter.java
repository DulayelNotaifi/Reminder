package com.example.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventsRecycleAdapter extends RecyclerView.Adapter<EventsRecycleAdapter.EventViewHolder>  {
List<Event> data;
static Context context;

public EventsRecycleAdapter(List<Event> data,Context c){
    this.data = data;
    context = c;
}
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        EventViewHolder hold = new EventViewHolder(v);
        return hold;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
         Event ev = data.get(position);
        holder.EventName.setText(ev.getName());
        holder.EventTime.setText(ev.getTime() );
        holder.myEvent = ev;
    }

    @Override
    public int getItemCount() {
        return this.data.size() ;
    }

    public static class EventViewHolder extends  RecyclerView.ViewHolder {
    TextView EventName;
        TextView EventTime;
        Event myEvent;
         public EventViewHolder(@NonNull View itemView) {
            super(itemView);
             EventName = itemView.findViewById(R.id.Evname);
             EventTime =  itemView.findViewById(R.id.EvTime);

             itemView.findViewById(R.id.DelButt).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                     View v = LayoutInflater.from(context).inflate(
                             R.layout.confirm_delete_popup, (ConstraintLayout) view.findViewById(R.id.layoutDialogContainer));
                     builder.setView(v);
                     final AlertDialog alertDialog = builder.create();


                     v.findViewById(R.id.buttonDismiss).setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             alertDialog.dismiss();

                         }
                     });
                     if (alertDialog.getWindow() != null){
                         alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                     }

                     v.findViewById(R.id.buttondelete).setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             MyDBHelper dbHelper = new MyDBHelper(context);
                             dbHelper.deleteEvent(myEvent.getId());
                             alertDialog.dismiss();
                         }
                     });

                     alertDialog.show();

                 }
             });

             itemView.findViewById(R.id.EditiButt).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(context, AddEventPage.class);
                     intent.putExtra("id", myEvent.getId());
                     intent.putExtra("name", myEvent.getName());
                     intent.putExtra("type",myEvent.getType());
                     intent.putExtra("date",myEvent.getDate());
                     intent.putExtra("time",myEvent.getTime());
                     intent.putExtra("priority",myEvent.getPriority());
                     intent.putExtra("notes",myEvent.getPriority());
                     intent.putExtra("remindTime", myEvent.getRemindTime());
                     context.startActivity(intent);
                 }
             });

        }
    }
}
