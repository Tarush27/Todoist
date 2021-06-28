package com.example.todoist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
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
    Button switch_btn;
    SwitchCompat mySwitch;
    String title, note;
    ImageView cross_icon;
    int longPressed;
    int singlePressed;
    DrawerLayout drawerLayout;
    ImageView list;
    NavigationView nav_view;

    //    String date,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.DarkTheme);
//        }
//        else{
//            setTheme(R.style.AppTheme);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        switch_btn = findViewById(R.id.switch_btn);
//        mySwitch = findViewById(R.id.mySwitch);
//        SharedPreferences sp = getSharedPreferences("Mode", MODE_PRIVATE);
//        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
//        final boolean isDarkMode = sp.getBoolean("isDarkMode", true);
//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            mySwitch.setChecked(true);
//        }
//        SharedPreferences sharedPrefs = getSharedPreferences("mode",MODE_PRIVATE);
//        final boolean isDark = sharedPrefs.getBoolean("isDarkMode",true);
//        if (isDark){
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            mySwitch.setChecked(true);
//        }
//        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    SharedPreferences.Editor editor = getSharedPreferences("mode",MODE_PRIVATE).edit();
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    mySwitch.setChecked(true);
//                    editor.putBoolean("isDarkMode",true);
//                    editor.apply();
//                }
//                else{
//                    SharedPreferences.Editor editor = getSharedPreferences("mode",MODE_PRIVATE).edit();
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                    mySwitch.setChecked(false);
//                    editor.putBoolean("isDarkMode",false);
//                    editor.apply();
//                }
//                finish();
//                startActivity(new Intent(MainActivity.this, MainActivity.this.getClass()));
//
//            }
//        });
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

//        SharedPreferences sp = getSharedPreferences("Mode", MODE_PRIVATE);
//        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sp.edit();
//        final boolean isDarkMode = sp.getBoolean("DM", false);
//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            switch_btn.setClickable(true);
//            switch_btn.setEnabled(true);
//        }
//        switch_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                  switch_btn.setClickable(true);
//                    switch_btn.setEnabled(true);
//                    editor.putBoolean("DM", true);
//                    editor.apply();
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                  switch_btn.setClickable(false);
//                    switch_btn.setEnabled(false);
//                    editor.putBoolean("DM", false);
//                    editor.apply();
//                }
//
//
//            }
//        });
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
        taskModelList.get(longPressed).setNote(date);
        taskModelList.get(longPressed).setNote(time);
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
//                reminderFragment.setSaveTimeAndDateCallBack((date, time) -> {
//                    reminderFragment.dismiss();
//                    this.date = date;
//                    this.time = time;
//                    taskModelList.get(longPressed).setNote(date);
//                    Log.d("MainActivity",date + "");
//                    taskModelList.get(longPressed).setNote(time);
//                    Log.d("MainActivity",time + "");
//                    taskAdapter.updateAdapter();
//                });
                reminderFragment.saveTimeAndDateCallBack.onSaveTimeAndDate(reminderFragment.date, reminderFragment.time);
                reminderFragment.show(getSupportFragmentManager(), "Reminder Fragment");
                return true;
            case R.id.delete:
//                taskModelList.remove(delNotePos);
                return true;
            case R.id.send:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}