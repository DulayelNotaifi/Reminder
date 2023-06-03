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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_EVENT = 1;
    private static final int VIEW_TYPE_NO_EVENTS = 2;
    static List<Event> data;
    static Context context;
    private static String selectedDate;

    public EventsRecycleAdapter(List<Event> data, Context c, String selectedDate) {
        this.data = data;
        context = c;
        this.selectedDate = selectedDate;
    }
    public void updateData(List<Event> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else if (viewType == VIEW_TYPE_EVENT)  {
            View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_row, parent, false);
            return new EventViewHolder(eventView, this);
        }else {
            View noEventsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.no_events, parent, false);
            return new NoEventsViewHolder(noEventsView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_EVENT) {
            EventViewHolder holder = (EventViewHolder) viewHolder;
            Event ev = data.get(position - 1); // Subtract 1 to account for the header
            holder.EventName.setText(ev.getName());
            holder.EventTime.setText(ev.getTime());
            holder.myEvent = ev;
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (data.isEmpty()) {
            return VIEW_TYPE_NO_EVENTS;
        } else {
            return VIEW_TYPE_EVENT;
        }
    }

    @Override
    public int getItemCount() {
        return this.data.isEmpty() ? 2 : this.data.size() + 1;
    }
    // Add a new ViewHolder class for the "No events" message
    public static class NoEventsViewHolder extends RecyclerView.ViewHolder {
        public NoEventsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    // Add a new ViewHolder class for the header
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            TextView evDate = itemView.findViewById(R.id.evDate);
            evDate.setText(selectedDate); // Set the selectedDate text to evDate

            Button addEventButton = itemView.findViewById(R.id.addButt);
            addEventButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddEventPage.class);
                    intent.putExtra("selectedDate", selectedDate); // Pass the selected date
                    context.startActivity(intent);
                }
            });
        }
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
                             updatedData.remove(getAdapterPosition()-1);
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
