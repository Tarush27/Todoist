package com.example.todoist;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


//    private TextView receive_text;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        receive_text = findViewById(R.id.receive_text);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, TaskActivity.class);
            startActivityForResult(i, 1);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String str = null;
        Log.d("MainActivity", data.toString());
        if (data != null) {
            str = data.getStringExtra("message");
        }
//        receive_text.setText(str);
    }
}