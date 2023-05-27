package com.example.notification;
import android.graphics.Color;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;
import java.util.Date;
import java.util.List;

public class CustomCalendarCellDecorator implements CalendarCellDecorator {

    private List<Date> mDatesToHighlight;

    public CustomCalendarCellDecorator(List<Date> datesToHighlight) {
        mDatesToHighlight = datesToHighlight;
    }

    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        if (mDatesToHighlight.contains(date)) {
            cellView.setBackgroundColor(Color.YELLOW);
        }
    }
}