package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class logo extends AppCompatActivity {
    private static int SPLASH_TIME_OUT=2000;

    Animation fade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        fade= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        findViewById(R.id.logo).setAnimation(fade);
        findViewById(R.id.logoword).setAnimation(fade);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(logo.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        },SPLASH_TIME_OUT);
    }
}