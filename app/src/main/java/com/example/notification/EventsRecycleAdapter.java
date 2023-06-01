package com.example.notification;

import static com.example.notification.CalenderPage.REQUEST_CODE_UPDATE_EVENT;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventsRecycleAdapter extends RecyclerView.Adapter<EventsRecycleAdapter.EventViewHolder>  {
    static List<Event> data;
static Context context;

public EventsRecycleAdapter(List<Event> data,Context c){
    this.data = data;
    context = c;
}
    public void updateData(List<Event> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row,parent,false);
        EventViewHolder hold = new EventViewHolder(v, this);
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
        EventsRecycleAdapter adapter;
         public EventViewHolder(@NonNull View itemView, EventsRecycleAdapter adapter) {
            super(itemView);
             this.adapter = adapter;
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
                             // Remove the event from the list and update the adapter's data
                             List<Event> updatedData = new ArrayList<>(data);
                             updatedData.remove(getAdapterPosition());
                             adapter.updateData(updatedData);

                             alertDialog.dismiss();

                             // Update the calendar page after deleting the event and updating the adapter's data
                             ((CalenderPage) context).calindarEvents();
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
                     intent.putExtra("notes",myEvent.getNotes());
                     intent.putExtra("remindTime", myEvent.getRemindTime());
                     // Start AddEventPage activity for a result
                     ((AppCompatActivity) context).startActivity(intent);
                     //((CalenderPage) context).startActivityForResult(intent, REQUEST_CODE_UPDATE_EVENT);
                     ((CalenderPage) context).calindarEvents(); // update the calendar page
                     context.startActivity(intent);
                 }
             });



        }
    }
}
