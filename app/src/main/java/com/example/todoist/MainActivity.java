package com.example.todoist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ToolbarOverlapCallBack, SaveTimeAndDateCallBack {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    LinearLayout linearLayoutToolbar;
    Toolbar actionbarOverlap;
    TextView actionbarText;
    FloatingActionButton fab;
    String title, note, newTitle,newNote;
    ImageView cross_icon;
    int longPressed;
    int singlePressed;
    DrawerLayout drawerLayout;
    ImageView list;
    NavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        actionbarOverlap = findViewById(R.id.actionbarOverlap);
        actionbarOverlap.setTitle("");
        actionbarText = findViewById(R.id.actionbarText);
        list = findViewById(R.id.list);
        cross_icon = findViewById(R.id.cross_icon);
        cross_icon.setVisibility(View.INVISIBLE);
        fab = findViewById(R.id.fab);
        list.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        nav_view.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.notes:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }
            return true;
        });

        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TaskActivity.class);
            startActivityForResult(i, 1);
        });

        loadData();

        recyclerView = findViewById(R.id.recyclerView);
        taskAdapter = new TaskAdapter(taskModelList, this, this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
//                newTitle = data.getStringExtra("message");
//                newNote = data.getStringExtra("message1");
//                title = newTitle;
//                note = newNote;
                prepareTaskList(title, note);
                taskAdapter.updateAdapter();
            }
        }

    }

    private void prepareTaskList(String title, String note) {
        int color = Color.WHITE;
        TaskModel taskModel = new TaskModel(title, note, color);
        taskModelList.add(0, taskModel);
    }


    @Override
    public void onNoteLongClick(int position) {
        longPressed = position;
        linearLayoutToolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(actionbarOverlap);
        actionbarOverlap.setVisibility(View.VISIBLE);
        cross_icon.setVisibility(View.VISIBLE);
        taskModelList.get(longPressed).setBorderColor(Color.BLACK);
        cross_icon.setOnClickListener(v -> {
            linearLayoutToolbar.setVisibility(View.VISIBLE);
            actionbarOverlap.setVisibility(View.INVISIBLE);
            taskModelList.get(longPressed).setBorderColor(Color.WHITE);
            taskAdapter.updateAdapter();
        });
        taskAdapter.updateAdapter();
    }

    @Override
    public void onNoteSingleClick(int position) {
        singlePressed = position;
        actionbarOverlap.setVisibility(View.INVISIBLE);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        taskModelList.get(singlePressed).setBorderColor(Color.WHITE);
        taskAdapter.updateAdapter();
    }

    @Override
    public void onSaveTimeAndDate(String date, String time) {
        taskModelList.get(longPressed).setDate(date);
        taskModelList.get(longPressed).setTime(time);
        taskAdapter.updateAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.color:
                ColorSelectorFragment fragment = new ColorSelectorFragment();
                fragment.setColorSelectedCallback(color -> {
                    fragment.dismiss();
                    actionbarOverlap.setVisibility(View.INVISIBLE);
                    linearLayoutToolbar.setVisibility(View.VISIBLE);
                    taskModelList.get(longPressed).setColor(color);
                    taskModelList.get(longPressed).setBorderColor(Color.WHITE);
                    taskAdapter.updateAdapter();
                });
                fragment.show(getSupportFragmentManager(), "Dialog Fragment");
                return true;
            case R.id.reminder:
                ReminderFragment reminderFragment = new ReminderFragment();
                reminderFragment.setSaveTimeAndDateCallBack(this);
                reminderFragment.show(getSupportFragmentManager(), "Reminder Fragment");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}