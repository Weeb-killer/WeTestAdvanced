package com.example.cognitive_diagnosis_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class KnowledgemapActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgemap);
    }

    public void mapOnclick1(View view){
        Intent intent = new Intent(KnowledgemapActivity.this, MainActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }

    public void mapOnclick2(View view){
        Intent intent = new Intent(KnowledgemapActivity.this, KnowlegdeMapDetail.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }


    public void mapOnclick3(View view){
        Intent intent = new Intent(KnowledgemapActivity.this, UserActivity.class);
        startActivity(intent);//开始activity，跳转到下一个页面
        overridePendingTransition(R.anim.activity_jump1,R.anim.activity_jump2);
    }
}
