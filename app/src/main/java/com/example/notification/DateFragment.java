package com.example.notification;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dp=new DatePickerDialog(getActivity(),R.style.PurpleDialogTheme,(DatePickerDialog.OnDateSetListener)getActivity(),y,m,d);
        dp.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dp;
    }
}