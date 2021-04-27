package com.example.todoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity extends AppCompatActivity {

    private EditText title;

    //    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);
//        save = findViewById(R.id.save);
        title = findViewById(R.id.title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        setTaskInResult();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setTaskInResult();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTaskInResult() {
        String str = title.getText().toString().trim();
        Bundle b = new Bundle();
        b.putString("message", str);
        Intent i = new Intent();
        i.putExtras(b);
        setResult(1, i);
    }
}