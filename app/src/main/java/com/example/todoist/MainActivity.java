package com.example.todoist;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ToolbarOverlapCallBack {

    private ArrayList<TaskModel> taskModelList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    LinearLayout linearLayoutToolbar;
    Toolbar actionbarOverlap;
    TextView actionbarText;
    FloatingActionButton fab;
    String title, note;
    ImageView cross_icon;
    int longPressed;
    int singlePressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutToolbar = findViewById(R.id.linearLayoutToolbar);
        actionbarOverlap = findViewById(R.id.actionbarOverlap);
        actionbarOverlap.setTitle("");
        actionbarText = findViewById(R.id.actionbarText);
        cross_icon = findViewById(R.id.cross_icon);
        cross_icon.setVisibility(View.INVISIBLE);
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
        int color = Color.WHITE;
        TaskModel taskModel = new TaskModel(title, note, color);
        taskModelList.add(0, taskModel);
    }


    @Override
    public void onNoteLongClick(int position) {
        int borderColor = Color.BLACK;
        longPressed = position;
        linearLayoutToolbar.setVisibility(View.INVISIBLE);
        setSupportActionBar(actionbarOverlap);
        actionbarOverlap.setVisibility(View.VISIBLE);
        cross_icon.setVisibility(View.VISIBLE);
        cross_icon.setOnClickListener(v -> {
            linearLayoutToolbar.setVisibility(View.VISIBLE);
            actionbarOverlap.setVisibility(View.INVISIBLE);
            taskModelList.get(longPressed).setBorderColor(Color.WHITE);
            taskAdapter.updateAdapter();
        });
        taskModelList.get(longPressed).setBorderColor(borderColor);
        taskAdapter.updateAdapter();
    }

    @Override
    public void onNoteSingleClick(int position) {
        singlePressed = position;
        actionbarOverlap.setVisibility(View.INVISIBLE);
        linearLayoutToolbar.setVisibility(View.VISIBLE);
        int removeBorderColor = Color.WHITE;
        taskModelList.get(singlePressed).setBorderColor(removeBorderColor);
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
                    taskModelList.get(singlePressed).setBorderColor(Color.WHITE);
                    taskAdapter.updateAdapter();
                });
                fragment.show(getSupportFragmentManager(), "Dialog Fragment");
                return true;
            case R.id.reminder:
                ReminderFragment reminderFragment = new ReminderFragment();
                reminderFragment.show(getSupportFragmentManager(),"Reminder Fragment");
                return true;
            case R.id.pin:
                Toast.makeText(this, "Hy", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}