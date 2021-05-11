package com.example.todoist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskModel{
    String title,note;

    public TaskModel(String title, String note) {
        this.title = title;
        this.note = note;
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
