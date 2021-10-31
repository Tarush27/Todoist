package com.example.todoist;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeFragment extends Fragment {
    public static final String key = "DATE";
    TextView datePicker, timePicker;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    SaveDateAndTime saveDateAndTime;
    String date, time;
    String defaultDate, defaultTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        date = getArguments().getString(key);
        return inflater.inflate(R.layout.time_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        if (date == null) {
            defaultDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
        } else {
//            date = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
            defaultDate = date;
        }
        datePicker.setText(defaultDate);
        saveDateAndTime.onSaveDate(defaultDate);
        datePicker.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(view.getContext(), datePicker);
            p.getMenuInflater().inflate(R.menu.popup, p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        R.style.Theme_AppCompat_Light_Dialog, onDateSetListener = (view1, year1, month1, dayOfMonth) -> {
                    date = dayOfMonth + "-" + (month1 + 1) + "-" + year1;
                    datePicker.setText(date);
                    saveDateAndTime.onSaveDate(date);
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                datePickerDialog.getButton(BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 100, 0));
                datePickerDialog.getButton(BUTTON_POSITIVE).setTextColor(Color.rgb(0, 100, 0));
                return true;
            });
            p.show();

        });
        defaultTime = new SimpleDateFormat("HH:mm aa", Locale.getDefault()).format(new Date());
        timePicker.setText(defaultTime);
        saveDateAndTime.onSaveTime(defaultTime);
        timePicker.setOnClickListener(v -> {
            PopupMenu p1 = new PopupMenu(view.getContext(), timePicker);
            p1.getMenuInflater().inflate(R.menu.popup_one, p1.getMenu());
            p1.setOnMenuItemClickListener(item -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                        (timePickerView, hourOfDay, minutes) -> {
                            calendar.set(0, 0, 0, hourOfDay, minutes);
                            time = (String) DateFormat.format("hh:mm aa", calendar);
                            timePicker.setText(time);
                            saveDateAndTime.onSaveTime(time);
                        }, 12, 0, false);
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePickerDialog.show();
                timePickerDialog.getButton(BUTTON_NEGATIVE).setTextColor(Color.rgb(224, 64, 251));
                timePickerDialog.getButton(BUTTON_POSITIVE).setTextColor(Color.rgb(224, 64, 251));
                return true;
            });
            p1.show();

        });
    }

    void setSaveDateAndTime(SaveDateAndTime saveDateAndTime) {
        this.saveDateAndTime = saveDateAndTime;
    }


}