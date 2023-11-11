package com.example.cognitive_diagnosis_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

public class KnowlegdeMapDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_knowlegde_map_detail);
    }
    public void mapOnclick1(View view){
        Intent intent = new Intent(KnowlegdeMapDetail.this, MainActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mapOnclick2(View view){
        Intent intent = new Intent(KnowlegdeMapDetail.this, KnowledgemapActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }


    public void mapOnclick3(View view){
        Intent intent = new Intent(KnowlegdeMapDetail.this, UserActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }
}