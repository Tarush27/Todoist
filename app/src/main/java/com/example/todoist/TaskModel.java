package com.example.todoist;

public class TaskModel{
    String title,note;
    int color;

    public TaskModel(String title, String note, int color) {
        this.title = title;
        this.note = note;
        this.color = color;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
