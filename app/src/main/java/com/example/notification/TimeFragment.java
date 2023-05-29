package com.example.notification;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimeFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), R.style.PurpleDialogTheme,(TimePickerDialog.OnTimeSetListener) getActivity()
                ,h,m, DateFormat.is24HourFormat(getActivity()));
        //the last argument will display the Time picker depending on the user setting, 24 or 12 format
    }
}