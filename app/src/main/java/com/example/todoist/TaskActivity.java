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

//            String str = title.getText().toString().trim();
//            Intent i = new Intent();
//            i.putExtra("message",str);
//            setResult(1,i);
//            super.onBackPressed();
//            finish();
//
//
    }

//    @Override
//    public void onBackPressed() {
//        String str = title.getText().toString().trim();
//        Bundle b = new Bundle();
//        b.putString("message",str);
//        Intent i = new Intent();
//        i.putExtras(b);
//        setResult(1, i);
//        super.onBackPressed();
////
//    }

//    @Override
//    public void finish() {
//        String str = title.getText().toString().trim();
//        Bundle b = new Bundle();
//        b.putString("message","hello");
//        Intent i = new Intent();
//        i.putExtras(b);
//        setResult(1, i);
//        Log.d("MainActivity",b.toString());
//        super.finish();
//        Log.d("TaskActivity","hello");
//    }

    @Override
    protected void onPause() {
        String str = title.getText().toString().trim();
        Bundle b = new Bundle();
        b.putString("message","hello");
        Intent i = new Intent();
        i.putExtras(b);
        setResult(RESULT_OK, i);
        Log.d("TaskActivity",b.toString());
        super.onPause();
    }
//    @Override
//    public boolean onOptionsItemSelected(final MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                String str = title.getText().toString().trim();
//                Bundle b = new Bundle();
//                b.putString("message","hello");
//                Intent i = new Intent();
//                i.putExtras(b);
//                setResult(1, i);
//                Log.d("TaskActivity",b.toString());
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}