package com.example.todoist;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TaskModel {
    String title, note, date, time;
    int color;
    int borderColor;

    public TaskModel(String title, String note, int color) {
        this.title = title;
        this.note = note;
        this.color = color;
    }


    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
