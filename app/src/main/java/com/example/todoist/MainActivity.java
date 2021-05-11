package com.example.todoist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.PersistableBundle;
import android.renderscript.Type;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String title,note;
    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TaskActivity.class);
            startActivityForResult(i, 1);
        });

//        saveData();
//        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(taskModelList);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("Todoist",MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("name","");
//        Type type = (Type) new TypeToken<ArrayList<TaskModel>>(){}.getType();
//        taskModelList = gson.fromJson(json, (java.lang.reflect.Type) type);
//        if (taskModelList == null){
//            taskModelList = new ArrayList<>();
//        }
//    }
//
//    private void saveData() {
//        SharedPreferences sp = getSharedPreferences("Todoist",MODE_PRIVATE);
//        SharedPreferences.Editor sEdit = sp.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(taskModelList);
//        sEdit.putString("name",json);
//        sEdit.apply();
//    }




//    SharedPreferences sp = getSharedPreferences("Hello",MODE_PRIVATE); // for storing data
//    SharedPreferences.Editor sEdit = sp.edit();

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putSerializable("taskModelList",taskModelList);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        taskModelList = (ArrayList<TaskModel>) savedInstanceState.getSerializable("taskModelList");
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            title = data.getStringExtra("message");
            note = data.getStringExtra("message1");
        }

        prepareTaskList(title,note);
        Collections.reverse(taskModelList);
        taskAdapter.updateAdapter();
    }
    private void prepareTaskList(String title, String note) {
        TaskModel taskModel = new TaskModel(title,note);
        taskModelList.add(taskModel);
    }




}