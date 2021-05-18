package com.example.todoist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ToolbarOverlapCallBack {


    String title, note;
    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    Toolbar toolbar;
    Toolbar actionbarOverlap;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        actionbarOverlap = findViewById(R.id.actionbarOverlap);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TaskActivity.class);
            startActivityForResult(i, 1);
        });

        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(taskModelList, this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Todoist", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("name", "");
        Type type = (Type) new TypeToken<ArrayList<TaskModel>>() {
        }.getType();
        taskModelList = gson.fromJson(json, type);
        if (taskModelList == null) {
            Log.d("MainActivity", "Empty List");
            taskModelList = new ArrayList<>();
        } else {
            Log.d("MainActivity", taskModelList.size() + "");
        }
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }

    private void saveData() {
        SharedPreferences sp = getSharedPreferences("Todoist", MODE_PRIVATE);
        SharedPreferences.Editor sEdit = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskModelList);
        Log.d("MainActivity", json);
        sEdit.putString("name", json);
        sEdit.apply();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Empty Note Discarded", Toast.LENGTH_LONG).show();
            }
        } else {
            if (data != null) {
                title = data.getStringExtra("message");
                note = data.getStringExtra("message1");
                prepareTaskList(title, note);
                taskAdapter.updateAdapter();
            }
        }

    }

    private void prepareTaskList(String title, String note) {
        TaskModel taskModel = new TaskModel(title, note);
        taskModelList.add(0,taskModel);
    }


    @Override
    public void onToolbarOverlap() {
        toolbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActionbarOverlap() {
        actionbarOverlap.setVisibility(View.INVISIBLE);
//        setSupportActionBar(actionbarOverlap);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }
}