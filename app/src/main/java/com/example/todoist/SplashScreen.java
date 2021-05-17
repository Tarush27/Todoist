package com.example.todoist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 5000;
    Animation topAnim;
    ImageView todoLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        topAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.top_anim);
//        bottomAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bottom_anim);
        todoLogo = findViewById(R.id.todoLogo);
        todoLogo.setAnimation(topAnim);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        },SPLASH_SCREEN);
    }
}