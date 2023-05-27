package com.example.notification;
import android.graphics.Color;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CustomCalendarCellDecorator implements CalendarCellDecorator {

    private List<Event> mEvents;
    private Map<String, Integer> mColorMap;
    private CalendarPickerView mCalendarPickerView;

    public CustomCalendarCellDecorator(List<Event> events, CalendarPickerView calendarPickerView) {
        mEvents = events;
        mColorMap = new HashMap<>();
        mColorMap.put("Meeting", Color.alpha(7188));
        mColorMap.put("Picnic", Color.GREEN);
        mColorMap.put("Presentation", Color.rgb(2,9,8));
        mColorMap.put("Shopping",Color.rgb(20,10,44));
        mColorMap.put("Family Visit",Color.alpha(810));
        mColorMap.put("Study", R.color.StudyColor);
        mColorMap.put("Appointment", R.color.AppointmentColor);
        mColorMap.put("Eid", R.color.EidColor);
        mColorMap.put("Travel day", R.color.TraveldayColor);
        mColorMap.put("Other",R.color.OtherColor);
        mCalendarPickerView = calendarPickerView;
    }

    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        int color = Color.WHITE;
        for (Event event : mEvents) {
            if (isSameDay(date, event.getDate())) {
                color = mColorMap.get(event.getType()); // replace mColorap with mColorMap
                break;
            }
        }
        cellView.setBackgroundColor(color);
        mCalendarPickerView.invalidate();
    }

    private boolean isSameDay(Date date, String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = dateFormat.parse(dateString);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date);
            cal2.setTime(d);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                    cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}