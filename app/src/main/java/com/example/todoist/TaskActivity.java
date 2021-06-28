package com.example.todoist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class TaskActivity extends AppCompatActivity {

    private EditText title;
    private EditText note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_layout);
        title = findViewById(R.id.title);

        note = findViewById(R.id.note);
        Toolbar toolbar = findViewById(R.id.taskLayoutToolbar);
        setSupportActionBar(toolbar);
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
//        String str = title.getText().toString().trim();
//        String str1 = note.getText().toString().trim();
//        Bundle b = new Bundle();
//        b.putString("message", str);
//        b.putString("message1",str1);
//        Intent i = new Intent();
//        i.putExtras(b);
//        if (str.isEmpty() && str1.isEmpty()) {
//            setResult(RESULT_CANCELED,i);
//        }
//        else {
//            setResult(1,i);
//        }

        String str = title.getText().toString().trim();
        String str1 = note.getText().toString().trim();
        Bundle b = new Bundle();
        b.putString("message",str);
        b.putString("message1",str1);
        Intent i = new Intent();
        i.putExtras(b);
        if (str.isEmpty() && str1.isEmpty()){
            setResult(RESULT_CANCELED);
        }
        else {
            setResult(1, i);
        }
    }
}