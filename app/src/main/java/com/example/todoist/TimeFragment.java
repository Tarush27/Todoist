package com.example.todoist;

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

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeFragment extends Fragment {


    TextView datePicker, timePicker;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.time_fragment, container, false);
        datePicker = view.findViewById(R.id.datePicker);
        timePicker = view.findViewById(R.id.timePicker);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        String defaultDate = new SimpleDateFormat("dd MMM", Locale.getDefault()).format(new Date());
        datePicker.setText(defaultDate);
        datePicker.setOnClickListener(v -> {
            PopupMenu p = new PopupMenu(view.getContext(), datePicker);
            p.getMenuInflater().inflate(R.menu.popup, p.getMenu());
            p.setOnMenuItemClickListener(item -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                        R.style.Theme_MaterialComponents_DayNight_Dialog_MinWidth, onDateSetListener = (view1, year1, month1, dayOfMonth) -> {
                    String date = dayOfMonth + "-" + (month1 + 1) + "-" + year1;
                    datePicker.setText(date);
                }, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                return true;
            });
            p.show();
        });
        timePicker.setOnClickListener(v -> {
            PopupMenu p1 = new PopupMenu(view.getContext(), timePicker);
            p1.getMenuInflater().inflate(R.menu.popup_one, p1.getMenu());
            p1.setOnMenuItemClickListener(item -> {
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), (view12, hourOfDay, minute1) -> {
                    calendar.set(0, 0, 0, hourOfDay, minute1);
                    timePicker.setText(DateFormat.format("hh:mm aa", calendar));
                }, 12, 0, false);
                timePickerDialog.updateTime(hour, minute);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
                return true;
            });
            p1.show();
        });
        return view;
    }
}