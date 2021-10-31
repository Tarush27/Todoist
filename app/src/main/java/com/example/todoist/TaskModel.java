package com.example.todoist;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskModel implements Parcelable {
    String title, note, date, time;
    int color;
    int borderColor;

    public TaskModel(String title, String note, int color) {
        this.title = title;
        this.note = note;
        this.color = color;
    }


    protected TaskModel(Parcel in) {
        title = in.readString();
        note = in.readString();
        date = in.readString();
        time = in.readString();
        color = in.readInt();
        borderColor = in.readInt();
    }

    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {
        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };

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

    @Override
    public String toString() {
        return "TaskModel{" +
                "title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", color=" + color +
                ", borderColor=" + borderColor +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(title);
        dest.writeString(note);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(color);
        dest.writeInt(borderColor);
    }
}
